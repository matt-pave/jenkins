terraform {
 backend "gcs" {
   bucket  = "jenkins-316218"
   prefix  = "terraform/state"
 }
}
