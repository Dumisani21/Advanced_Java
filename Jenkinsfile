pipeline {
    agent any

    // stages {
    //     stage('Hello') {
    //         steps {
    //             echo 'Hello World'
    //         }
    //     }
    // }

    tools {
        // Install the Maven version configured as "M3" in Jenkins
        maven 'M3'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from your GitHub repository
                git url: 'https://github.com/Dumisani21/Advanced_Java', branch: 'main', credentialsId: 'your-credentials-id'
            }
        }
        stage('Build') {
            steps {
                // Run Maven build
                bat '''cd client
                mvn clean install
                '''
                // bat 'cd client'
                // bat 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                // Run Maven tests
                bat '''cd client
                mvn test
                '''
                // bat 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                // Deploy your application (this is just an example, adjust to your needs)
                bat 'mvn deploy'
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

    // tools {
    //     // Install the Maven version configured as "M3" in Jenkins
    //     maven 'M3'
    // }

    // stages {
    //     stage('Checkout') {
    //         steps {
    //             // Checkout the code from your GitHub repository
    //             git 'https://github.com/Dumisani21/Advanced_Java'
    //         }
    //     }
    //     stage('Build') {
    //         steps {
    //             // Run Maven build
    //             // sh 'mvn clean install'
    //             echo 'Hello BUILD'
    //         }
    //     }
    //     stage('Test') {
    //         steps {
    //             // Run Maven tests
    //             // sh 'mvn test'
    //             echo 'Hello TEST'
    //         }
    //     }
    //     stage('Deploy') {
    //         steps {
    //             // Deploy your application (this is just an example, adjust to your needs)
    //             // sh 'mvn deploy'
    //             echo 'Hello Deploy'
    //         }
    //     }
    // }

    // post {
    //     always {
    //         // Archive the build artifacts
    //         archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
    //         // Publish the test results
    //         junit 'target/surefire-reports/*.xml'
    //     }
    // }
}
