#!/bin/bash
echo "Build immagini partita..."
echo "--- Building auctionmanager v1.0.0..."
pwd
cd ../../auctionmanager
pwd
docker build --no-cache -t unict/auctionmanager:1.0.0 -f Dockerfile .

echo "--- Building walletmanager v1.0.0..."
cd ../walletmanager
pwd
docker build --no-cache -t unict/walletmanager:1.0.0 -f Dockerfile .

echo "--- Building sagaorchestration v1.0.0..."
cd ../SagaOrchestration
pwd
docker build --no-cache -t unict/sagaorchestration:1.0.0 -f Dockerfile .

echo "--- Building discovery-service v1.0.0..."
cd ../discovery-service
pwd
docker build --no-cache -t unict/discovery-service:1.0.0 -f Dockerfile .

echo "--- Building gateway-service v1.0.0..."
cd ../gateway-service
pwd
docker build --no-cache -t unict/gateway-service:1.0.0 -f Dockerfile .
echo "Build immagini completata..."

cd ../Script/Production
pwd
echo "Caricamento immagini su minikube da docker"
minikube image load unict/auctionmanager:1.0.0
minikube image load unict/walletmanager:1.0.0
minikube image load unict/sagaorchestration:1.0.0
minikube image load unict/discovery-service:1.0.0
minikube image load unict/gateway-service:1.0.0

echo "Loading postgres..."
kubectl apply -f postgres.yml
kubectl apply -f pgadmin.yml
echo "Si prega di configurare postgres sulla pagina pgAdmin..."

echo "Loading zookeeper & kafka..."
kubectl apply -f kafka.yml

echo "Loading discovery-service..."
kubectl apply -f discovery-service.yml

echo "Loading gateway-service..."
kubectl apply -f gateway-service.yml

echo "Loading sagaorchestration-service..."
kubectl apply -f sagaorchestration.yml

echo "Loading auctionmanager-service..."
kubectl apply -f auctionmanager.yml

echo "Loading walletmanager-service..."
kubectl apply -f walletmanager.yml

minikube service gateway-service
echo "-------------- All done! --------------"