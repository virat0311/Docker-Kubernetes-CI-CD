Docker-Kubernetes-CI-CD
This project demonstrates creating and managing Docker images for microservices and deploying them locally and on Kubernetes.

Creating Docker Images
Steps to Build and Run Docker Images Locally
Build Docker images for your services and run them locally to verify they work correctly.
Use Docker Compose to run all the images together.
Options to Build Docker Images
Option 1: Basic Dockerfile
Create a Dockerfile in your project directory with the following content:

dockerfile
Copy code
FROM openjdk:18.0-slim
COPY target/*.jar app.jar
EXPOSE 5000
ENTRYPOINT ["java", "-jar", "/app.jar"]
Build the Docker image:

bash
Copy code
docker build -t <image_name> .
Run the container:

bash
Copy code
docker run -d -p 5001:5001 <image_name>
Option 2: Multi-Stage Build
For better efficiency, use a multi-stage build. Create the following Dockerfile:

dockerfile
Copy code
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /home/app
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:21-jre
EXPOSE 5000
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
Build and run the container using the same commands as above.

Option 3: Multi-Stage Build with Caching
Use the following Dockerfile to leverage caching:

dockerfile
Copy code
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /home/app

# Copy only necessary files for dependency resolution
COPY ./pom.xml /home/app/pom.xml
COPY ./src/main/java/com/in28minutes/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java /home/app/src/main/java/com/in28minutes/rest/webservices/restfulwebservices/RestfulWebServicesApplication.java
RUN mvn -f /home/app/pom.xml clean package

# Copy the remaining project files
COPY . /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:21-jre
EXPOSE 5001
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
Pushing Docker Images to Docker Hub
Steps:
Log in to Docker Hub:

bash
Copy code
docker login
Tag the Docker image:

bash
Copy code
docker tag <local-image-name> <your-dockerhub-username>/<repository-name>:<tag>
Push the image to Docker Hub:

bash
Copy code
docker push <your-dockerhub-username>/<repository-name>:<tag>
Pull the image on another system:

bash
Copy code
docker pull <your-dockerhub-username>/<repository-name>:<tag>
Run the pulled image:

bash
Copy code
docker run -p 5001:5000 <your-dockerhub-username>/<repository-name>:<tag>
Deploying on Google Kubernetes Engine (GKE)
Steps to Host Containers on GKE:
Set Up GKE Project:

bash
Copy code
gcloud config set project <project-id>
gcloud container clusters get-credentials my-cluster --zone us-central1-c --project <project-id>
Create and Manage Deployments:

bash
Copy code
kubectl create deployment hello-world-rest-api --image=<docker-image>
kubectl get deployment
Expose Deployment:

bash
Copy code
kubectl expose deployment hello-world-rest-api --type=LoadBalancer --port=8080
kubectl get services
Scale and Autoscale:

bash
Copy code
kubectl scale deployment hello-world-rest-api --replicas=3
kubectl autoscale deployment hello-world-rest-api --max=4 --cpu-percent=70
kubectl get hpa
ConfigMap and Secrets:

bash
Copy code
kubectl create configmap hello-world-config --from-literal=RDS_DB_NAME=todos
kubectl create secret generic hello-world-secrets-1 --from-literal=RDS_PASSWORD=dummytodos
Update Deployment Image:

bash
Copy code
kubectl set image deployment hello-world-rest-api hello-world-rest-api=<new-docker-image>
Delete Services and Deployments:

bash
Copy code
kubectl delete service hello-world-rest-api
kubectl delete deployment hello-world-rest-api
Delete Cluster:

bash
Copy code
gcloud container clusters delete my-cluster --zone us-central1-c
Key Kubernetes Commands
Viewing Resources:
bash
Copy code
kubectl get pods
kubectl get services
kubectl get replicasets
kubectl get configmap
kubectl describe secret hello-world-secrets-1
Deleting Pods or Services:
bash
Copy code
kubectl delete pod <pod-name>
kubectl delete service <service-name>

 
