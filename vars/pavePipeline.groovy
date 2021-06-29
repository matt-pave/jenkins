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
                       def tools = load "${TOOLS}tools.groovy"
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
            def tools = load "${TOOLS}tools.groovy"
            tools.setBuildStatus("Success", "SUCCESS")
            echo 'I succeeded!'
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