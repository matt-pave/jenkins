def npmInstall(){
    nodejs(nodeJSInstallationName: 'node16.2.0') {
        sh "npm install"
    }
}
def npmTest(){
    nodejs(nodeJSInstallationName: 'node16.2.0') {
        sh "npm test"
    }
}
return this