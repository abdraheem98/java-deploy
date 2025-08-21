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

        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-admin', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
                    script {
                        def settingsXml = """
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <servers>
    <server>
      <id>nexus-snapshots</id>
      <username>${NEXUS_USERNAME}</username>
      <password>${NEXUS_PASSWORD}</password>
    </server>
    <server>
      <id>nexus-releases</id>
      <username>${NEXUS_USERNAME}</username>
      <password>${NEXUS_PASSWORD}</password>
    </server>
  </servers>
</settings>
"""
                        writeFile file: 'settings.xml', text: settingsXml
                    }

                    dir('productcatalogue') {
                        sh 'mvn deploy -s ../settings.xml -DskipTests'
                    }
                    dir('shopfront') {
                        sh 'mvn deploy -s ../settings.xml -DskipTests'
                    }
                    dir('stockmanager') {
                        sh 'mvn deploy -s ../settings.xml -DskipTests'
                    }
                }
            }
        }
    }
}
