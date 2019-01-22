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
                tools { maven 'apache-maven-3.6.0' }
                agent { label 'master'}
                steps {
                    sh 'mvn -B -DskipTests clean package'
                }
            }

            stage ('test') {
                tools { maven 'apache-maven-3.6.0' }
                agent { label 'master'}
                steps {
                    parallel (
                        "unit tests": { sh 'mvn test' },
                        "fake integration tests": { sh 'mvn test' }
                    )
                }
            }

            stage('deploy somewhere'){
                steps {
                    fakeDeploy address: args.targetServer, count: 10
                }
            }
        }
        post {
            failure {
                echo "mail to: ${args.email}, subject: 'Pipeline failed', body: ${env.BUILD_URL}"
            }
        }
    }
}
