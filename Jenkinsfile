pipeline {
    agent any

    tools {
        // These names must match the 'Name' field in your 
        // Jenkins Global Tool Configuration (Manage Jenkins > Tools)
        maven 'maven-3' 
        jdk 'jdk-17'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
    }
}

