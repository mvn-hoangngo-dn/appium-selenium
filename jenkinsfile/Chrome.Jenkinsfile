def APP_MODULE = "Apps"
def SUITE_NAME = "suite/ChromeSuite"
def notify = evaluate readTrusted('notify.groovy')
def classification = evaluate readTrusted('classification.groovy')
pipeline {
    agent none
        tools {
                  maven 'maven 3.8.4'
                }
    stages {
        stage('Run cucumber') {
            agent {
                label 'master'
            }
            steps {
                sh "mvn clean test -DsuiteXmlFile='${SUITE_NAME}'"
            }
            post {
                always {
                    archiveArtifacts artifacts: "${APP_MODULE}/target/cucumber-reports/,${APP_MODULE}/target/screenshots/,${APP_MODULE}/target/GitHubReport.json"
                    junit "${APP_MODULE}/target/cucumber-reports/*.xml"
                    script {
                        def androidPropertyFile = "${APP_MODULE}/target/classifications/Chrome_Test.properties"
                        classification.addClassification(APP_MODULE, androidPropertyFile)
                        notify.sendMessage(APP_MODULE, androidPropertyFile)
                    }
                    stash includes: "${APP_MODULE}/target/GitHubReport.json", name: 'cucumber-report'
                    deleteDir()
                }
                success {
                    echo "Test succeeded"
                }
                failure {
                    echo "Test failed"
                    script {
                        notify.cucumberFailed("${APP_MODULE}/target/classifications/Chrome_Test.properties")
                    }
                }
            }
        }
    }
}
