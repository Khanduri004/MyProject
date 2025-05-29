pipeline {
    agent any

    tools {
        maven 'mymaven'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/Sonal0409/SonarQubeCoverageJava.git'
            }
        }

        stage('Parallel Execution') {
            parallel {
                stage('Code Review') {
                    steps {
                        sh 'mvn pmd:pmd'
                    }
                }

                stage('Code Coverage using Jacoco') {
                    steps {
                        sh 'mvn clean package'
                    }
                    post {
                        always {
                            echo 'Generating code coverage report...'
                        }
                        success {
                            jacoco(execPattern: '**/target/jacoco.exec')
                        }
                    }
                }
            }
        }
    }
}
