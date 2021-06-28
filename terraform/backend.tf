terraform {
 backend "gcs" {
   bucket  = "pave-terraform-backend"
   prefix  = "poc-terraform/state"
 }
}
