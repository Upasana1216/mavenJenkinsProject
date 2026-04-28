pipeline {
    agent any

    tools {
        // These names must match the 'Name' field in your 
        // Jenkins Global Tool Configuration (Manage Jenkins > Tools)
        maven 'maven-3' 
        jdk 'jdk-17'
    }

    environment {
        APP_NAME = "my_first_jenkins_app"
        BUILD_ENV = "dev"
    }

    stages {
        stage('Build') {
            steps {
                echo "Building ${APP_NAME} in ${BUILD_ENV}"
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
        success {
            archiveArtifacts artifacts: '**/target/*.jar'
            echo "Build is successfull"
        }
        failure {
            echo "Build Failed !!"
        }
    }
}

