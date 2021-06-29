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
                        echo "Deploy"
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
            void setBuildStatus(String message, String state) {
            step([
                $class: "GitHubCommitStatusSetter",
                reposSource: [$class: "ManuallyEnteredRepositorySource", url: "https://github.com/my-org/my-repo"],
                contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "ci/jenkins/build-status"],
                errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
                statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
            ]);
            }
            setBuildStatus("Build complete", "SUCCESS");
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