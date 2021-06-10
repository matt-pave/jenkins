@Library('tools')
import pave.tools.Tools

def call(String repoUrl) {
  pipeline {
       agent any
       environment {
            GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
       }
       stages {
           stage("Tools initialization") {
               steps {
                   script{
                        tools.npmInstall()
                   }
               }
           }
       }
   }
}