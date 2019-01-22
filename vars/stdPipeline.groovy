#!/usr/bin/env groovy
def call(body) {
    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent any

        stages {
            stage('checkout git') {
                steps {
                    git branch: branch, url: scmUrl
                }
            }

            stage('build') {
                steps {
                    sh 'mvn clean package -DskipTests=true'
                }
            }

            stage ('test') {
                steps {
                    parallel (
                        "unit tests": { sh 'mvn test' },
                        "fake integration tests": { sh 'mvn test' }
                    )
                }
            }

            stage('deploy somewhere'){
                steps {
                    fakeDeploy address: targetServer, count: 10
                }
            }
        }
    }
}
