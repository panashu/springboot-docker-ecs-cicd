# ECS Rolling Update : Continuous Deployment

The current collection of cloudformation templates (IaC) is setup to achieve
continuous deployment of an application to Amazon
Elastic Container Service (Amazon ECS) using AWS CodePipeline and AWS
CodeBuild. With continuous deployment, software revisions are deployed to a
production environment automatically with a rolling update deployment strategy. 
This is performed without explicit approval from a developer,
making the entire software release process automated.

Launching this AWS CloudFormation stack provisions a continuous deployment
process that uses AWS CodePipeline to monitor a GitHub repository for new
commits and AWS CodeBuild to create a new Docker container image and to push it
into Amazon Elastic Container Registry (Amazon ECR).

When creating this stack, it is opted to deploy the service onto AWS
Fargate which allows us to run containers
without managing clusters or services. 

The shell script present under bin folder can be run by executing the below command.
bash deploy.sh

The CloudFormation template requires the following parameters:

- Cluster Configuration
  - **Launch Type**: Deploy the service using AWS Fargate.

- GitHub Configuration
  - **Repo**: The repo name of the sample service.
  - **Branch**: The branch of the repo to deploy continuously.
  - **User**: Your username on GitHub.
  - **Personal Access Token**: Token for the user specified above.
    (https://github.com/settings/tokens)
	Access token prerequisites
	
	- For GitHub, the personal access token must have the following scopes.
		- ***repo*** : Grants full control of private repositories.
		- ***repo:status*** : Grants read/write access to public and private repository commit statuses.
		- ***admin:repo_hook*** : Grants full control of repository hooks. This scope is not required if your token has the repo scope.

The CloudFormation stack provides the following output:

- **ServiceUrl**: The sample service that is being continuously deployed.
- **PipelineUrl**: The continuous deployment pipeline in the AWS Management
  Console.

### Testing the example

After the CloudFormation stack is created, the latest commit to the GitHub
repository is run through the pipeline and deployed to ECS. Open the
**PipelineUrl** to watch the first revision run through the CodePipeline
pipeline. After the deploy step turns green, open the URL from **ServiceUrl**


To test continuous deployment, make a change to src/index.php in the
hello-world-php-simple-docker-app repository and push it to GitHub. CodePipeline detects
the change, builds the new application, and deploys it to your cluster
automatically. After the pipeline finishes deploying the revision, reload the
page to see the changes made.

### Cleaning up the example resources

To remove all resources created by this example, do the following:

1. Delete the main CloudFormation stack which deletes the substacks and resources.
1. Manually delete resources which may contain content:

    - S3 Bucket: ArtifactBucket
    - ECR Repository: Repository

## CloudFormation template resources

The following sections explains all of the resources created by the
CloudFormation template provided with this example.

#### [DeploymentPipeline](templates/deployment-pipeline.yaml)

  Resources that compose the deployment pipeline include the CodeBuild project,
  the CodePipeline pipeline, an S3 bucket for deployment artifacts, and all
  necessary IAM roles used by those services.

#### [Service](templates/service.yaml)

  An ECS task definition, service, IAM role, and ECR repository for the sample
  application. This template is used by the CodePipeline pipeline to deploy the
  sample service continuously.

#### [Cluster](templates/ecs-cluster.yaml)

  An ECS cluster optionally backed by an Auto Scaling group of EC2 instances
  running the Amazon ECS-optimized AMI for the EC2 launch type.

#### [Load Balancer](templates/load-balancer.yaml)

  An Application Load Balancer to be used for traffic to the sample application.

#### [VPC](templates/vpc.yaml)

  A VPC with two public subnets on two separate Availability Zones, an internet
  gateway, and a route table with a default route to the public internet.