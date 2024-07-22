terraform {
  required_providers {
    null = {
      source  = "hashicorp/null"
      version = "3.1.0"
    }
  }
}

// ----------------------------- Resources -----------------------------

resource "null_resource" "this" {
  // Capture the network name at creation time to use in destroy-time provisioner
  triggers = {
    network_name = "${var.NETWORK_NAME}"
  }

  // Create-time provisioners

  provisioner "local-exec" {
    when    = create
    command = "docker network create --attachable -d bridge ${self.triggers.network_name}"
  }

  // Kafka
  provisioner "local-exec" {
    when    = create
    command = "docker compose -f ./docker/docker-compose.yml up -d"
  }

  // MongoDB
  provisioner "local-exec" {
    when    = create
    command = "docker run -it -d --name mongo-container -p 27017:27017 --network ${self.triggers.network_name} --restart always -v mongodb_data_container:/data/db mongo:latest"
  }

  // MySQL
  provisioner "local-exec" {
    when    = create
    command = "docker run -it -d --name mysql-container -p 3306:3306 --network ${self.triggers.network_name} --restart always -e MYSQL_ROOT_PASSWORD=root -v mysql_data_container:/var/lib/mysql mysql:latest"
  }

  // Adminer for MySQL
  provisioner "local-exec" {
    when    = create
    command = "docker run -it -d --name adminer-container -p 8080:8080 --network ${self.triggers.network_name} -e ADMINER_DEFAULT_SERVER=mysql-container --restart always adminer:latest"
  }

  // Destroy-time provisioners

  provisioner "local-exec" {
    when    = destroy
    command = "docker compose -f ./docker/docker-compose.yml down"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "docker stop mongo-container && docker rm mongo-container"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "docker stop mysql-container && docker rm mysql-container"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "docker stop adminer-container && docker rm adminer-container"
  }

  provisioner "local-exec" {
    when    = destroy
    command = "docker network rm ${self.triggers.network_name}"
  }
}

