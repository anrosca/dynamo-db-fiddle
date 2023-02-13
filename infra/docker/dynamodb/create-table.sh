#!/bin/bash
#aws dynamodb create-table --cli-input-json file://movies-table.json --endpoint-url http://localhost:4566
#aws dynamodb create-table \
#    --table-name movies \
#    --attribute-definitions \
#        AttributeName=id,AttributeType=S \
#    --key-schema \
#        AttributeName=id,KeyType=HASH \
#    --provisioned-throughput \
#        ReadCapacityUnits=5,WriteCapacityUnits=5 \
#    --endpoint-url http://localhost:4566

aws dynamodb create-table --table-name movies --attribute-definitions AttributeName=id,AttributeType=S \
--key-schema AttributeName=id,KeyType=HASH  \
 --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --region eu-west-1 --query TableDescription.TableArn --output text  --endpoint-url http://localhost:4566
