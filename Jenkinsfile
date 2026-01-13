pipeline {
    agent any

    tools {
        jdk 'java-17'
        maven 'maven'
    }

    environment {
        IMAGE_NAME = "amithachar/namma-invoice-app:${GIT_COMMIT}"
    }

    stages {

        stage('Git Checkout') {
            steps {
                git url: 'https://github.com/amithachar/Namma-Invoice-App.git', branch: 'main'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    echo "Building Docker image: ${IMAGE_NAME}"
                    docker build -t ${IMAGE_NAME} .
                '''
            }
        }

        stage('Login to Docker Hub') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'docker-hub-creds',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                    '''
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                sh 'docker push ${IMAGE_NAME}'
            }
        }
    }
}
