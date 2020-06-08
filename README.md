## Redshift Devops

This is to demonstrate how to use AWS CodeSuite of services to build a DevOps pipeline to test changes to the Redshift database.

## Environment Variables

### Unit Tests via `ConnectionManager`

* `TEST_JDBC_URL` - the JDBC URL for the tests.
* `TEST_JDBC_USER` - the JDBC User for the tests.
* `TEST_JDBC_PASSWORD` - the JDBC Password for the tests.
* `TEST_REDSHIFT_IAM_ROLE` - the IAM ARN that will be used to ingest test data.
* `TEST_DATA_S3_BUCKET` - the S3 bucket name where the tests data is located.

### FlyWay
Refer to [documentation](https://flywaydb.org/documentation/envvars) for a full list of variables.

The following are the pertinent ones:

* `FLYWAY_URL`
* `FLYWAY_USER`
* `FLYWAY_PASSWORD`