## Retarus Java Fax SDK
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.retarus.fax/retarus-java-fax/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.retarus.fax/retarus-java-fax)
<br>[![Tests](https://github.com/retarus/retarus-java-fax/actions/workflows/build-and-test.yml/badge.svg)](https://github.com/retarus/retarus-java-fax/blob/main/.github/workflows/build-and-test.yml)

The official Java Fax SDK provided by Retarus GmbH to contact our messaging services.
Retarus Faxolution for Applications Webservice SDK facilitates automation of the fax transmission process.
<br>In addition, it also allows you to monitor the status of a sent fax and delete archived reports.
The Webservice communicates via REST 1.0 (JSON).
<br>The Java SDK is a wrapper around the REST API and provides a simple interface to the Retarus Faxolution for Applications Webservice.
<br>The minimum Java version required is 1.8.

### Allowed operations:

- Create a fax job request and send it to the webservice
- Retrieve completed fax jobs status
- Delete the retrieved jobs individually or all at once
- Receive the fax job status per HTTP push and/or email status report

## Installation
To install the SDK, you can either download the source code and build it yourself or you can use Maven\Gradle to install it.
<br>Using Maven, download the following dependency:
```xml
<dependency>
  <groupId>com.retarus.fax</groupId>
  <artifactId>retarus-java-fax</artifactId>
  <version>1.0</version>
</dependency> 
```
or Gradle:
```groovy
implementation 'com.retarus.fax:retarus-java-fax:1.0' 
```
If you want to build the .jar file yourself, you can clone the repository and build it using Maven:
```shell
git clone git@github.com:retarus/retarus-java-fax.git
cd retarus-java-fax
mvn package -DskipTests
```
If you need to download Maven, you can do so here: https://maven.apache.org/download.html

### Usage
The Java SDK implements different operations that are offered by Retarus. Each service provides a small variety of examples to get a better understanding of how to use their functionality.<br>Furthermore, you can check out our OpenAPI documents on the interfaces here: https://developers.retarus.com/docs/fax/api/sending-fax/

#### Configuring the SDK
First, you need to configure the SDK with your details:
Username, Password, Customer Number and Region (Optional).
#### Region/Location
The chosen location will determine where your jobs will be processed. By default, the SDK will use the Europe region if no location is specified.
<br>A region represents a group of locations. For example, the Europe region contains the following locations: Munich and Frankfurt.
<br> The following regions/locations are available:
- Europe:
  - Munich
  - Frankfurt
- USA:
  - Secaucus
  - Ashburn
- Switzerland:
  - Switzerland
- Singapore:
  - Singapore

You can choose a location or a region to process your jobs. If you choose a location, your jobs will be processed in that location only, if you choose a region, your jobs will be processed in all locations of that region and you will get a list of FaxStatusReport objects for each location.

## Credentials
Credentials are required for making requests to the API, otherwise, you will get an error.
<br>You can introduce your credentials manually, by adding the following lines of code:


```Java
import de.retarus.*;

//Initialize the SDK with your credentials, customer number and location like this:
FaxServiceClient client = FaxServiceClient.builder()
        .username("YOUR_USERNAME")
        .password("YOUR_PASSWORD")
        //Optional
        .region(Region.EUROPE)
        //Optional
        .customerNumber("YOUR_CUSTOMER_NUMBER")
        .build();
```
Or you can use the configuration file to initialize the SDK. by adding a .env file to the root of your project or adding the values to the system environment variables.
It should be named .env and should be placed in the root of your project.
<br> The configuration (.env) file should look like this:

```Java
RETARUS_USERNAME=username
RETARUS_PASSWORD=password
RETARUS_CUSTOMER_NUMBER=123456
RETARUS_LOCALE=EUROPE
        
//And call the following method to initialize the SDK:
FaxServiceClient client = FaxServiceClient.initFromEnvFile();
```
**Please note:** Using the SDK and the Fax API will only work with valid credentials, otherwise you will get an error.

<br>The SDK offers two different ways to process your jobs. You can either use the SDK in an asynchronous way or in a synchronous way.
<br>Each operation that is available can also be accessed via the Retarus object statically. By providing the required input parameters (if required), you can execute the operation.
For example, to send a fax, you can use the following code:
```Java
//Send a fax in an synchronous way
FaxRequest faxRequest = ...;
client.sendFaxRequest(faxRequest);

//Send a fax in an asynchronous way
FaxRequest faxRequest = ...;
client.sendFaxRequestAsync(faxRequest);
```

### 1. Send a Fax
To send a fax, you need to create a FaxRequest object (Fax Job Request) and provide the required parameters.
<br>The request is made to prepare the fax job to be transferred for processing. The fax job is not sent immediately, but is queued for processing.
<br>In case of a successful request, which means, status code 200 or 201, the jobRequestResult object (Response) will contain the fax job ID, which can be used to check the status of the fax job or perform other operations on it.
<br>Otherwise, an exception will be thrown, or you can check the status code of the jobRequestResult object.
```Java
FaxRequest faxRequest = ...;
ApiResponse<String> response = client.sendFaxRequest(faxRequest);
        
//In case of a successful request (status code 200 or 201), the response object will contain the fax job ID.
        
if (apiResponse.getStatusCode() == HttpStatus.SC_OK || apiResponse.getStatusCode() == HttpStatus.SC_ACCEPTED) {
    String jobId = apiResponse.getValue();
    System.out.println("Fax job ID: " + jobId);
}
```
For more information check: [Send Fax README](https://github.com/retarus/retarus-java-fax/blob/main/SEND_FAX.md),  [Retarus OpenAPI Documentation](https://developers.retarus.com/docs/fax/api/sending-fax/#send-a-fax) and [Send Fax Example](https://github.com/retarus/retarus-java-fax/blob/main/examples/SendFaxExample.java).
<br>

### 2. Retrieve Available Fax Reports
Depending if you choose a location or a region, you will get a list of FaxStatusReport objects for each location or region.
<br> For Example, if you choose the Munich location, you will get a list of FaxStatusReport objects for the Munich location only.
<br>But, if you choose Europe, you will get a list of FaxStatusReport objects for Munich and Frankfurt
<br>That allows you to treat the response differently, depending on the location or region you choose.
```Java
List<ApiResponse<List<FaxStatusReport>>> allReportsResponseList = client.getReports();
//Filter and do something with the reports
//...
```
**Please note:** The status reports are only available for 30 days after the fax job has been processed or until the fax job has been deleted.
<br> **Please note:** The results are limited to the oldest 1000 entries per request. If you want to fetch more, you need to call the method again.

For more information check: [Get Fax Report README](https://github.com/retarus/retarus-java-fax/blob/main/GET_AND_DELETE_FAX_REPORT.md),  [Retarus OpenAPI Documentation](https://developers.retarus.com/docs/fax/api/sending-fax/#send-a-fax) and [Get Fax Report Example](https://github.com/retarus/retarus-java-fax/blob/main/examples/GetFaxReportAndDeleteExample.java).

### 3. Delete Fax Job Report Individually or All Reports at Once
To delete a fax job report individually, you need to provide the fax status report you would like to delete.
```Java
FaxStatusReport faxStatusReport = ...;
ApiResponse<FaxDeletionReport> response = client.deleteReport(faxStatusReport);
```
Or you can delete all fax job reports at once.
```Java
ApiResponse<FaxDeletionReport> response = client.deleteReports();
```
**Please note:** The method is limited to the oldest 1000 entries per request. If you want to delete more, please call the method multiple times.
For more information check: [Delete Fax Report README](https://github.com/retarus/retarus-java-fax/blob/main/GET_AND_DELETE_FAX_REPORT.md),  [Retarus OpenAPI Documentation](https://developers.retarus.com/docs/fax/api/sending-fax/#send-a-fax) and [Delete Fax Report Example](https://github.com/retarus/retarus-java-fax/blob/main/examples/GetFaxReportAndDeleteExample.java).