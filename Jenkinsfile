pipeline{
    agent any // decided which node to run

    tools {
        jdk 'java-17'
        maven 'maven'
    }

    environment {
        IMAGE_NAME = "amithachar/Namma-Invoice-App:${GIT_COMMIT}"
    }

    stages{
        stage('git-checkout'){
            steps{
                git url: 'https://github.com/amithachar/Namma-Invoice-App.git', branch: 'main'
            }
            
        }

        stage('Compile'){
            steps{
                sh '''
                    mvn compile
                '''
            }
        }
        stage('packaging'){
            steps{
                sh '''
                    mvn clean package
                '''
            }
        }
        stage('docker-build'){
            steps{
                sh '''
                    printenv
                    docker build -t ${IMAGE_NAME} .
                '''
            }
        } 

        stage('Login to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-creds', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        // Login to Docker Hub
                        sh "echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin"
                    }
                }
            }
        }  

        stage('Push to dockerhub'){
            steps{
                sh '''
                    docker push ${IMAGE_NAME}
                '''
            }
        }

        
