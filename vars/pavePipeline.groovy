
def call(String repoUrl) {
  pipeline {
       agent any
       environment {
            TOOLS = "${workspace}@libs/jenkins/src/pave/tools/"
            GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
       }
       stages {
           stage("Tools initialization") {
               steps {
                   script{
                        def tools = load "${TOOLS}tools.groovy"
                        tools.install()
                   }
               }
           }
       }
   }
}