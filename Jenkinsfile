pipeline {
    agent any
    
    environment {
        REPO_URL = 'https://github.com/your-repo.git'
        BRANCH = 'main'
    }
    
    stages {
        stage('Checkout') {
            steps {
                git branch: "${BRANCH}", url: "${REPO_URL}"
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo "Building the application..."
                    sh 'mvn clean package'  // Change to npm install or gradle build if needed
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running tests..."
                    sh 'mvn test'  // Adjust if using different test framework
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying application..."
                    sh './deploy.sh'  // Replace with actual deployment script
                }
            }
        }
    }
    
    post {
        success {
            echo 'BUILD SUCCESSFUL!'
        }
        failure {
            echo 'BUILD FAILED!'
        }
    }
}
