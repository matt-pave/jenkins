def tools = load "${TOOLS}tools.groovy"
def call(String repoUrl) {
  pipeline {
       agent any
       environment {
            TOOLS = "${workspace}@libs/jenkins/src/pave/tools/"
            GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
       }
       stages {
           stage("Build") {
               steps {
                   script{
                        tools.npmInstall()
                   }
               }
           }
           stage("Deploy") {
               steps {
                   script{
                        def tools = load "${TOOLS}tools.groovy"
                        tools.npmInstall()
                   }
               }
           }
       }
           post {
        always {
            echo 'One way or another, I have finished'
            deleteDir() /* clean up our workspace */
        }
        success {
            echo 'I succeeded!'
            ithubNotify status: "SUCCESS", credentialsId: "", account: "", repo: env.GIT_URL
        }
        unstable {
            echo 'I am unstable :/'
        }
        failure {
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }

   }
}