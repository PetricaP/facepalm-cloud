import os

import boto3

current_dir = os.path.dirname(os.path.realpath(__file__))

s3 = boto3.resource('s3')

bucket = s3.Bucket('ppetrica-facepalm-bucket')

photos = []

for dirpath, _, filenames in os.walk(os.path.join(current_dir, 'photos')):
    for f in filenames:
        relative_dirpath = os.path.relpath(dirpath, current_dir)

        object_key = os.path.join(relative_dirpath, f)

        with open(object_key, 'rb') as file:
            bucket.put_object(Key=object_key, Body=file)
