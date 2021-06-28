# resource "google_filestore_instance" "instance" {
#   name = "jenkins-instance"
#   zone = "us-central1-a"
#   tier = "PREMIUM"

#   file_shares {
#     capacity_gb = 2560
#     name        = "jenkins"
#   }

#   networks {
#     network = "default"
#     modes   = ["MODE_IPV4"]
#   }
# }