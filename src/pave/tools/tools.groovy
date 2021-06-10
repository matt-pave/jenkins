package pave.tools

class Tools implements Serializable {
    private static final long serialVersionUID
    def steps
    Tools(steps) {
        this.steps = steps
    }
    void npmInstall() {
        steps.sh "npm install"
    }
}
