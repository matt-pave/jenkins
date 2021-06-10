export TF_VAR_org_id=YOUR_ORG_ID
export TF_VAR_billing_account=014E1C-24F426-CC1AE9
export TF_ADMIN=jenkins-316218
export TF_CREDS=~/.config/gcloud/${USER}-terraform-admin.json

gcloud auth configure-docker 

gcloud container clusters get-credentials jenkins-cluster --zone us-central1-a

https://kubernetes.io/docs/reference/kubectl/cheatsheet/


# Release new Jenkins

To build the new image:
`docker build . -t docker pull gcr.io/jenkins-316218/jenkins:X.X` where X.X is the new version increment.

To push the new image to GCR:
`docker push docker pull gcr.io/jenkins-316218/jenkins:X.X`

To deploy new image to GKE:
- Edit "jenkins-deployment.yaml" 
- Replace `image: gcr.io/jenkins-316218/jenkins:X.X` version with your new version.
- Run `kubectl delete -f jenkins-deployment.yaml`
- Run `kubectl get pods` and ensure the current pod has terminated.
- Run  `kubectl apply -f jenkins-deployment.yaml`
- Confirm pod deployed successfully run `kubectl get pods`


**NOTE** _We currently have to delete the existing deployment as pods don't like sharing rea/write persistent storage_
