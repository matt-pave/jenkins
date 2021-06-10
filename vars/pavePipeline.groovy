def call(String repoUrl) {
  pipeline {
       agent any
       stages {
           stage("Tools initialization") {
               steps {
                   env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
                   println env.GIT_REPO_NAME
               }
           }
       }
   }
}