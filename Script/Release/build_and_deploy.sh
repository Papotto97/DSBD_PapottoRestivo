#!/bin/bash
echo "Building auctionmanager v1.0.0..."
pwd
cd ../../auctionmanager
pwd
docker build --no-cache -t unict/auctionmanager:1.0.0 -f Dockerfile .

echo "Building walletmanager v1.0.0..."
cd ../walletmanager
pwd
docker build --no-cache -t unict/walletmanager:1.0.0 -f Dockerfile .

echo "Building sagaorchestration v1.0.0..."
cd ../SagaOrchestration
pwd
docker build --no-cache -t unict/sagaorchestration:1.0.0 -f Dockerfile .

echo "Building discovery-service v1.0.0..."
cd ../discovery-service
pwd
docker build --no-cache -t unict/discovery-service:1.0.0 -f Dockerfile .

echo "Building gateway-service v1.0.0..."
cd ../gateway-service
pwd
docker build --no-cache -t unict/gateway-service:1.0.0 -f Dockerfile .

echo "Deploying with compose..."
cd ../Script/Release
pwd
docker-compose -p auction-service-PROVA -f docker-compose.yaml up -d
