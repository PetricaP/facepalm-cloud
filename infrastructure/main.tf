terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 4.6.0"
    }
    random = {
      source  = "hashicorp/random"
      version = "~> 3.1.0"
    }
    archive = {
      source  = "hashicorp/archive"
      version = "~> 2.2.0"
    }
  }

  required_version = "~> 1.0"
}

provider "aws" {
  region = var.aws_region
}

module "vpc" {
  source = "terraform-aws-modules/vpc/aws"
  version = "~> 1.26.0"

  name = "backend-vpc"
  cidr = "10.10.10.0/24"
  azs = ["us-east-1a", "us-east-1b", "us-east-1c"]
  private_subnets = ["10.10.10.0/27", "10.10.10.32/27", "10.10.10.64/27"]
  public_subnets = ["10.10.10.96/27", "10.10.10.128/27", "10.10.10.160/27"]
  enable_nat_gateway = true
  single_nat_gateway = true
}

resource "aws_ecs_task_definition" "backend" {
  family = "backend"
  container_definitions = <<EOF
  [
    {
      "name": "nginx",
      "image": "nginx:1.13-alpine",
      "essential": true,
      "portMappings": [
        {
          "containerPort": 80
        }
      ]
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "backend",
          "awslogs-region": "${var.aws_region}"
        }
      }
      "memory": 128
      "cpu": 100
    }
  ]
  EOF
}
