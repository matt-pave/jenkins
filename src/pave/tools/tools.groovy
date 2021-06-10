def npmInstall(){
    nodejs(nodeJSInstallationName: 'node16.2.0') {
        sh "npm install"
    }
}

return this