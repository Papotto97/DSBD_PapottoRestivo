#!/bin/bash
echo "Building auctionmanager v1.0.0..."
cd ../../auctionmanager
docker build --no-cache -t unict/auctionmanager:1.0.0 -f Dockerfile .

echo "Building walletmanager v1.0.0..."
cd ../../walletmanager
docker build --no-cache -t unict/walletmanager:1.0.0 -f Dockerfile .

echo "Building sagaorchestration v1.0.0..."
cd ../../SagaOrchestration
docker build --no-cache -t unict/sagaorchestration:1.0.0 -f Dockerfile .

echo "Deploying with compose..."
#cd ../scripts
docker-compose -p auction-service -f docker-compose.yaml up -d
