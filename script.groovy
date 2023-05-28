def buildJar(){
  echo "building jar file............... "
  // sh 'mvn clean'
  sh 'mvn package'
}

def clearOldImages(){
  echo "clearing Old Images ..............."
  sh '''if docker images -a  | grep "*qeema*" | awk \'{print $3}\' | xargs docker rmi -f ;
  then 
    printf "clearing succsseded"
  else 
    printf "no images to clear"
    fi'''
}

def buildImagewithcred(){
  echo "building image..............."
  withCredentials([usernamePassword(credentialsId:'qeemaReg-Credentials' , passwordVariable:'PASS' , usernameVariable:'USER')]){
    sh 'echo $PASS | docker login "https://registry.tools.idp.qeema.io" -u $USER --password-stdin'
    sh "docker build -t registry.tools.idp.qeema.io/qeema_test:$BUILD_NUMBER ."
    //sh "docker push registry.tools.idp.qeema.io/qeema_test:$BUILD_NUMBER"  	  
  }
}

def buildImage(){
    echo "building image..............."
    sh 'docker login "https://registry.tools.idp.qeema.io"  -u mashour -p 9#5#kgrxd3mmUA'
    sh "docker build -t registry.tools.idp.qeema.io/qeema_test:$BUILD_NUMBER ."
    //sh "docker push registry.tools.idp.qeema.io/qeema_test:$BUILD_NUMBER" 
}

def publishArtifacts(){
  echo "push yaml files to kubernetes cluster ........."
  sh "sed -i 's/imgtag/$BUILD_NUMBER/g' dep.yml"
  sh "scp -o StrictHostKeyChecking=no dep.yml svc.yml root@192.168.1.100:/"
  }

def deploytok8s(){
  echo "deploying to kubernetes...................."
  sh '''if ! ssh root@192.168.1.100 kubectl get secrets | grep qeema-secret
    then
      ssh root@192.168.1.100 kubectl create secret docker-registry qeema-secret --docker-server=https://registry.tools.idp.qeema.io --docker-username=mashour --docker-password=9#5#kgrxd3mmUA "
    else
      printf "the secret is already exist"
    fi'''
    sh "ssh root@192.168.1.100 kubectl apply -f /dep.yml "
    sh "ssh root@192.168.1.100 kubectl apply -f /svc.yml "
   }
  
  def deploytok8swithcred(){
  echo "deploying to kubernetes...................."
  sh 'if ! [ ssh root@192.168.1.100 kubectl get secrets | grep qeema-secret ];
    then
      withCredentials( [ usernamePassword(credentialsId:qeemaReg-Credentials , passwordVariable:PASS , usernameVariable:USER) ] ) {
      ssh root@192.168.1.100 kubectl create secret docker-registry qeema-secret --docker-server=https://registry.tools.idp.qeema.io --docker-username=$USER --docker-password=$PASS 
        }
    else
      printf "the secret is already exist"
    fi'
    sh "ssh root@192.168.1.100 kubectl apply -f /dep.yml "
    sh "ssh root@192.168.1.100 kubectl apply -f /svc.yml "
   }

return this
