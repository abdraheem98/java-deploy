pipeline {
    agent any

    tools {
        maven 'maven'     // Jenkins Maven tool name
        jdk 'jdk17'       // Jenkins JDK tool name
    }

    environment {
        NEXUS_CRED = credentials('nexus-admin')   // <-- your Nexus Jenkins credential ID
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
                // Write settings.xml with credentials
                writeFile file: 'settings.xml', text: """
                <settings>
                  <servers>
                    <server>
                      <id>nexus</id>
                      <username>${NEXUS_CRED_USR}</username>
                      <password>${NEXUS_CRED_PSW}</password>
                    </server>
                  </servers>
                </settings>
                """

                // Deploy each module
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
