#!/bin/bash

# Initialize Terraform
echo "Terraforming..."
terraform init
terraform apply -auto-approve
