pipeline {
    agent any

    tools {
        // Install the Maven version configured as "M3" in Jenkins
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your GitHub repository
                git 'https://github.com/Dumisani21/Advanced_Java'
            }
        }
        stage('Build') {
            steps {
                // Run Maven build
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run Maven tests
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Deploy your application (this is just an example, adjust to your needs)
                sh 'mvn deploy'
            }
        }
    }

    post {
        always {
            // Archive the build artifacts
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
            // Publish the test results
            junit 'target/surefire-reports/*.xml'
        }
    }
}