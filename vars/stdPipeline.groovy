#!/usr/bin/env groovy

def call(Map args) {

    pipeline {
        agent any

        stages {
            stage('checkout git') {
                steps {
                    git branch: args.branch, url: args.scmUrl
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
                    fakeDeploy args.address: args.targetServer, count: 10
                }
            }
        }
        post {
            failure {
                mail to: args.email, subject: 'Pipeline failed', body: "${env.BUILD_URL}"
            }
        }
    }
}
