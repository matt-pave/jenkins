def call(String repoUrl) {
  pipeline {
       agent any
       environment {
            TOOLS = "${workspace}@libs/jenkins/src/pave/tools/"
            GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
       }
       stages {
            stage("Clone") {
               steps {
                   script{
                       checkout([$class: 'GitSCM', branches: [[name: "*/${env.BRANCH_NAME}"]],
                        userRemoteConfigs: [[url: env.GIT_URL]]])
                   }
               }
           }
           stage("Install") {
               steps {
                   script{
                       def tools = load "${TOOLS}tools.groovy"
                       tools.npmInstall()
                   }
               }
           }
           stage("Build") {
               steps {
                   script{
                         sh "docker build . -t gcr.io/trove-equity/jenkins:${env.BUILD_TAG}"
                         //newApp.push()
                   }
               }
           }
           stage("Test") {
               steps {
                   script{
                       def tools = load "${TOOLS}tools.groovy"
                       tools.npmTest()
                   }
               }
           }
           stage("Deploy") {
               steps {
                   script{
                        sh "ls"
                   }
               }
           }
       }
           post {
        always {
            echo 'One way or another, I have finished'
            //deleteDir() /* clean up our workspace */
        }
        success {

            setBuildStatus("Build complete", "SUCCESS");
            echo 'I succeeded!'
        }
        unstable {
            setBuildStatus("Build unstable", "UNSTABLE");
            echo 'I am unstable :/'
        }
        failure {
            setBuildStatus("Build failed", "FAILURE");
            echo 'I failed :('
        }
        changed {
            echo 'Things were different before...'
        }
    }

   }
}

void setBuildStatus(String message, String state) {
step([
    $class: "GitHubCommitStatusSetter",
    reposSource: [$class: "ManuallyEnteredRepositorySource", url: env.GIT_URL],
    contextSource: [$class: "ManuallyEnteredCommitContextSource", context: "Jenkins"],
    commitShaSource: [$class: "ManuallyEnteredShaSource", sha: env.GIT_COMMIT],
    errorHandlers: [[$class: "ChangingBuildStatusErrorHandler", result: "UNSTABLE"]],
    statusResultSource: [ $class: "ConditionalStatusResultSource", results: [[$class: "AnyBuildResult", message: message, state: state]] ]
]);
}