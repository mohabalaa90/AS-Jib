//use this file when you use the normal way 
def gv                                                  // define variable to read groovy script
pipeline{
  agent any                                            // use the default agent
  tools{
    maven 'maven'                                      // use the maven plugin
  }
  stages{
    stage('init'){
      steps{
        script{
          gv = load "script.groovy"                    // load the groovy script & store it in the variable
        }
      }
    }
    stage('build jar'){
      steps{
        script{
          gv.buildJar()                                 // packaging the applcation
        }
      }
    }
    stage('clear old images'){
      steps{
        script{
          gv.clearOldImages()                           // remove the old images 
        }
      }
    }
    stage('build image'){
      steps{
          script{
            gv.buildImage()                            // dockarize the application and push it to registery
          }
        }
    }
    stage('push artifacts'){
      steps{
        sshagent(['k8s-cred']) {
          script{
            gv.publishArtifacts()                      // send the deployment & service files to k8s cluster
          }
        }
      }
    }
    stage('deploy to k8s'){
      steps{
        sshagent(['k8s-cred']) {
          script{
            gv.deploytok8swithcred()                  // deploy the deployment & service on the k8s cluster
          }
        }
      }
    }
  }
}