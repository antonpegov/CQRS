#!/bin/bash

# Shut down Terraform
echo "Shutting down Terraform…"
terraform destroy -auto-approve
