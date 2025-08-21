pipeline {
    agent any

    tools {
        maven 'maven'
        jdk 'jdk17'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/abdraheem98/java-deploy.git'
            }
        }

        stage('Build with Maven') {
            steps {
                dir('productcatalogue') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('shopfront') {
                    sh 'mvn clean package -DskipTests'
                }
                dir('stockmanager') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'productcatalogue/target/*.jar', fingerprint: true
                archiveArtifacts artifacts: 'shopfront/target/*.jar', fingerprint: true
                archiveArtifacts artifacts: 'stockmanager/target/*.jar', fingerprint: true
            }
        }
    }
}
