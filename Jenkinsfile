def gv
pipeline{
  agent any
  tools{
    maven 'maven'
  }
  stages{
    stage('init'){
      steps{
        script{
          gv = load "script.groovy"
        }
      }
    }
    stage('build jar'){
      steps{
        script{
          gv.buildJar()
        }
      }
    }
    stage('clear old images'){
      steps{
        script{
          gv.clearOldImages()
        }
      }
    }
    stage('build image'){
      steps{
          script{
            gv.buildImage()
          }
        }
    }
    stage('push artifacts'){
      steps{
        sshagent(['k8s-cred']) {
          script{
            gv.publishArtifacts()
          }
        }
      }
    }
    stage('deploy to k8s'){
      steps{
        sshagent(['k8s-cred']) {
          script{
            gv.deploytok8swithcred()
          }
        }
      }
    }
  }
}