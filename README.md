export TF_VAR_org_id=YOUR_ORG_ID
export TF_VAR_billing_account=014E1C-24F426-CC1AE9
export TF_ADMIN=jenkins-316218
export TF_CREDS=~/.config/gcloud/${USER}-terraform-admin.json

gcloud auth configure-docker 

gcloud container clusters get-credentials jenkins-cluster --zone us-central1-a

https://kubernetes.io/docs/reference/kubectl/cheatsheet/