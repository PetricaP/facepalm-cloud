import json
import os

import boto3

current_dir = os.path.dirname(os.path.realpath(__file__))

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('facepalm-table')

items = []

with open(os.path.join(current_dir, 'items.json'), 'r') as f:
    for row in f:
        items.append(json.loads(row))

with table.batch_writer() as batch:
    for item in items:
        batch.put_item(Item=item)

