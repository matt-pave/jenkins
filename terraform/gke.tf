resource "google_service_account" "default" {
  account_id   = "jenkins-gke-account"
  display_name = "Jenkins GKE Service Account"
}

#TODO dont use owner/user account policy
# gcloud iam roles list | grep owner
data "google_iam_policy" "admin" {
  binding {
    role = "roles/owner"
    members = ["user:matt@j-bailey.com"]
  }
}

resource "google_service_account_iam_policy" "admin-account-iam" {
  service_account_id = google_service_account.default.name
  policy_data        = data.google_iam_policy.admin.policy_data
}

resource "google_container_cluster" "primary" {
  name               = "jenkins-cluster"
  location           = "us-central1-a"
  initial_node_count = 1
  project = var.project

  node_config {
    service_account = google_service_account.default.email
    oauth_scopes = [
      "https://www.googleapis.com/auth/cloud-platform"
    ]
    labels = {
      foo = "jenkins"
    }
    tags = ["jenkins"]
  }
  timeouts {
    create = "30m"
    update = "40m"
  }
}

resource "google_compute_disk" "default" {
  name  = "jenkins-disk"
  type  = "pd-ssd"
  zone = "us-central1-a"
  physical_block_size_bytes = 4096
}

#TODO add autoscaling here