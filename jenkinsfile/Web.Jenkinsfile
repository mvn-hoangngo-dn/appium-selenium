def APP_MODULE = "App"
pipeline {
    agent none
    tools {
          maven 'maven 3.8.4'
        }
    options { preserveStashes(buildCount: 5) }
    stages {
        stage('Stash source code') {
            agent {
                label 'master'
            }
            steps {
                stash includes: '**', name: 'source-code', useDefaultExcludes: false
            }
        }
        stage('Run Tests') {
            parallel {
                stage("Build") {
                    stages {
                        stage('Run cucumber') {
                            agent {
                                label 'master'
                            }

                            options { skipDefaultCheckout() }

                            steps {
                                unstash('source-code')
                                sh "mvn clean verify -DsuiteXmlFile='suite/ChromeSuite'"
                            }
                            post {
                                always {
                                    archiveArtifacts artifacts: "${APP_MODULE}/target/cucumber-reports/,${APP_MODULE}/target/screenshots/,${APP_MODULE}/target/GitHubReport.json"
                                    junit "${APP_MODULE}/target/cucumber-reports/*.xml"
                                    script {
                                        def props = readProperties interpolate: true, file: "${APP_MODULE}/target/cucumber-reports/browser.properties"
                                        cucumber fileIncludePattern: "${APP_MODULE}/target/cucumber-reports/*.json",
                                                sortingMethod: 'ALPHABETICAL',
                                                classifications: [
                                                        ['key'  : 'Platform',
                                                         'value': props.Platform
                                                        ],
                                                        ['key'  : 'BrowserName',
                                                         'value': props.BrowserName
                                                        ],
                                                        ['key'  : 'BrowserVersion',
                                                         'value': props.BrowserVersion
                                                        ],
                                                        ['key'  : 'Server',
                                                         'value': props.Server
                                                        ]
                                                ]
                                    }
//                                     stash includes: "${APP_MODULE}/target/GitHubReport.json", name: 'cucumber-report'
                                    deleteDir()
                                }

                                success {
                                    echo "Test succeeded"
                                }
                                failure {
                                    echo "Test failed"
                                }
                            }
                        }
                        stage('Export reports') {
                            when {
                                not {
                                    environment name: 'CHANGE_ID', value: ''
                                }
                            }
                            agent {
                                docker {
                                    label 'master'
                                    image 'anhquach/reporting:1.1.0'
                                    args '-v $HOME/vendor/bundle:/vendor/bundle'
                                }
                            }
                            options { skipDefaultCheckout() }
                            steps("Install gems") {
                                unstash('source-code')
                                unstash('cucumber-report')
                                sh "bundle install --path /vendor/bundle"
                            }
                            post {
                                success {
                                    sh "bundle exec danger --danger_id=cucumber_report --dangerfile=CucumberReport.Dangerfile"
                                    deleteDir()
                                }

                                failure {
                                    deleteDir()
                                }
                            }
                        }
                    }
                }

                stage('Validate code') {
                    when {
                        not {
                            environment name: 'CHANGE_ID', value: ''
                        }
                    }
                    stages {
                        stage('Validate') {
                            agent {
                                label 'master'
                            }

                            options { skipDefaultCheckout() }
                            steps {
                                unstash('source-code')
                                sh "mvn clean verify -DsuiteXmlFile='DefaultSuite'"
                                sh "mvn validate"
                            }
                            post {
                                always {
                                    stash includes: "${APP_MODULE}/target/checkstyle.xml", name: 'checkstyle'
                                    deleteDir()
                                }
                            }
                        }
                        stage('Reporting') {
                            agent {
                                docker {
                                    label 'master'
                                    image 'anhquach/reporting:1.1.0'
                                    args '-v $HOME/vendor/bundle:/vendor/bundle'
                                }
                            }
                            options { skipDefaultCheckout() }
                            steps("Preparing source code & Installing gems") {
                                unstash('source-code')
                                unstash('checkstyle')
                                sh "gem -v"
                                sh "bundle install --path /vendor/bundle"
                            }
                            post {
                                success {
                                    sh "sed -i -E 's/@[0-9]*//' ${pwd()}/${APP_MODULE}/target/checkstyle.xml"
                                    sh "bundle exec danger --danger_id=check_style --dangerfile=Dangerfile"
                                    deleteDir()
                                }

                                failure {
                                    deleteDir()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
