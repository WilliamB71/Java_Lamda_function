"""An AWS Python Pulumi program"""

import pulumi
import pathlib
from pulumi_aws import lambda_, iam 

jarFileName = 'lamda-weather-getter-1.jar'

assume_role = iam.get_policy_document(statements=[iam.GetPolicyDocumentStatementArgs(
    effect="Allow",
    principals=[iam.GetPolicyDocumentStatementPrincipalArgs(
        type="Service",
        identifiers=["lambda.amazonaws.com"],
    )],
    actions=["sts:AssumeRole"],
)])

iam_for_lambda = iam.Role("iamForLambda", assume_role_policy=assume_role.json)

lambdaFunction = lambda_.Function(
    'JAVA_LAMBDA_FUNCTION',
    runtime='java17',
    memory_size=512,
    role=iam_for_lambda.arn,
    code=f'{pathlib.Path(__file__).parent.resolve()}/../target/{jarFileName}',
    handler='com.example.LamdaWeather::handleRequest', 
)

# Export the name of the lambda function
pulumi.export('functionName', lambdaFunction.name)
