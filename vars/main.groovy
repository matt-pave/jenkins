// jenkinsForJava.groovy
def call(String repoUrl) {
  pipeline {
       agent any
       stages {
           stage("Tools initialization") {
               steps {
                   println "dave"
               }
           }
       }
   }
}