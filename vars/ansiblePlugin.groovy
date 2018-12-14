// common plugin to general ansible tasks
def call(Map pipelineParams) {

    node(pipelineParams.agent){
        // cloning public sunbird-devops and private repo
        stage('checkout private repo') {
            dir('sunbird-devops-private'){
            git branch: pipelineParams.branch, url: pipelineParams.scmUrl, credentialsId: pipelineParams.credentials
            }
        }

        stage('ansible') {
            println pipelineParams.env
            println pipelineParams.ansiblePlaybook
            println pipelineParams.ansibleExtraArgs
            sh ""'
            ansible-playbook -i $WORKSPACE/sunbird-devops-private/ansible/inventories/$pipelineParams.env \
            $WORKSPACE/ansible/$pipelineParams.ansiblePlaybook $pipelineParams.ansibleExtraArgs -vvv
            """
        }
    }
}
