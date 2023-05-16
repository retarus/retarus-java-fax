## Send Fax Request
To send a fax request, you need to create a FaxRequest object (Fax Job Request) and provide the required parameters.
<br>The SDK is quite flexible and allows you to set multiple recipients, multiple attachments and multiple cover pages.
<br>To understand how to create a FaxRequest object, you first need to understand the structure of the FaxRequest object and all its sub-objects.

### 1. Document
The Document object is one of, if not, the main object of the FaxRequest object.
<br>The data and additional information for sent documents must be set with this object, if there is no document data and no cover page, the API **will not accept the job.**
It contains the following attributes:
- Name: The name of the document. This is a required field, the allowed characters are **a-zA-Z0-9-_.** and the maximum length is 32 characters, and no whitespaces, slashes, or other special characters are
  permitted.
- Charset: Character encoding of plain text documents (*.txt). This is an optional field. Possible values are:
    - US-ASCII
    - UTF-8
    - UTF-16
    - UTF-16BE
    - UTF-16LE
    - ISO-8859-1
    - WINDOWS-1252
- Reference: An URL pointing to the document to be transmitted. Example: http://mydomain.retarus.com/my-path/mydocument.pdf
- Data: The document data as a Base64 encoded string. Example: "JVBERi0xLjMKJcfs..."
> **Please note:** If both are provided, the reference (see above) is used.

<br>Creating a document object with reference:
```Java
//Create a document object with a name and a reference
Document document = Document.builder()
                            .filename("mydocument.pdf")
                            .reference("http://mydomain.retarus.com/my-path/mydocument.pdf")
                            .build();
//From there, we can add the document to the fax request object
FaxRequest.builder()
 .document(document)
 .build();
```

<br>Creating a document object with data:
```Java
//Create a document object with a name and a reference
Document document = Document.builder()
        .filename("test-document-inline-byte-array.txt")
        .data("SGVsbG8sIHRoaXMgaXMgYSB0ZXN0aW5nIGRvY3VtZW50IGJvZHkgY3JlYXRlZCBmb3IgOTk5OTlURQ==")
        //Since it is a text document, we need to set the charset
        .setCharset(Charset.UTF_8)
        .build();
        
//From there, we can add the document to the fax request object
FaxRequest.builder()
 .document(document)
 .build();
```

<br>There is a third way to generate a document object, using the file path, that is, the file is read from the file system and the data is automatically set, as well as the file name:
```Java
//Create a document object based on a file path
Document document = Document.builder()
                            .filePath("C:\\Users\\myuser\\Desktop\\mydocument.pdf")
                            .build();
//If the file cannot be found, an Api exception will be thrown. If the file is found, the data will be set automatically and converted to base64 and the file name will be set as the document name.
        
//From there, we can add the document to the fax request object
FaxRequest.builder()
 .document(document)
 .build();
```

### 2. Recipient(s)

If a fax can be successfully sent to one of the two fax numbers, the transmission to the respective recipient is completed and considered successful. 
<br>The destination number which received the fax will be indicated in the report data for each recipient under RecipientStatus -> sentToNumber (see number X).
It contains the following attributes:
- Number: The fax number of the recipient. This is a required field. Example: +12015551000
- Properties: A cover page can be personalized for each individual recipient and then attached to the front of each fax document. This object allows specifying a value for each of the keys in the template for each recipient.
```Java
FaxRecipient recipient = FaxRecipient.builder().faxNumber("+12015551000").property("name", "John Doe").build();

//From there, we can add the recipient to the fax request object
FaxRequest.builder()
 .faxRecipient(recipient)
 .build();
```

### 3. Transport Options
The transport options object contains the information on the transmission of the fax. It contains the following attributes:
- CSID: The sender ID the received fax was sent from (max. 20 characters).
- isExpress: If set to true, the fax will be sent as an express fax.
- isDenylisted: Flag for the use of the Robinson List (only for numbers in Germany), ECOFAX (for numbers in France), or Retarus' own denylist.

 ```Java
//Create a transport options object
TransportOptions transportOptions = TransportOptions.builder()
                                                    .csid("test-csid")
                                                    .isExpress(true)
                                                    .isDenyListed(false)
                                                    .build();
        
//From there, we can add the transport options to the fax request object
FaxRequest.builder()
    .transportOptions(transportOptions)
    .build();
 ```

### 4. Rendering Options
The rendering options object contains the information on the rendering of the fax. It contains the following attributes:
- Paper format: The size of the paper. Possible options are:
    - A4
    - LETTER
- Resolution: The resolution of the fax. Possible options are:
    - LOW
    - HIGH
- Cover page template: The name of the cover page's template.
- Header: The content of the header, including control characters.
- Overlay: A template (e.g., with letter header and footer) can be applied to all or specific pages in the fax. A template consists of a one-page, black-and-white document. In order to install an overlay, the customer transfers a template to Retarus, and the template is then saved in Retarus' infrastructure under a mutually agreed upon name.
    - Name: The name of the overlay template, without the path information and file extension.
    - Mode: Specifies the page in the document where the template (overlay) will be applied. Possible values:
      - ALL_PAGES: the overlay is applied to all pages. 
      - NO_OVERLAY: no overlay is used (returns the same result as if "no overlay" had been specified in the options).
      - FIRST_PAGE: the overlay is applied only to the first page (if you are using a cover page, it is considered the first page).
      - LAST_PAGE: the overlay is applied only to the last page.
      - ALL_BUT_FIRST_PAGE: the overlay is applied to all pages except for the first (if you are using a cover page, the overlay will be applied to all other pages because the cover page is considered the first page).
      - ALL_BUT_LAST_PAGE: the overlay is applied to all pages except the last one.
      - ALL_BUT_FIRST_AND_LAST_PAGE: the overlay is applied to all pages except for the first and the last (the cover page is considered the first page if this mode is used).
      - FIRST_FILE: if the faxed document consists of multiple files, the overlay will only be used on the first file's pages (the cover page is considered not to belong to any file and does not an overlay in this mode).

```Java
//Create a rendering options object with all the parameters
RenderingOptions renderingOptions = RenderingOptions.builder()
                                                    .paperFormat(PaperFormat.A4)
                                                    .paperResolution(PaperResolution.HIGH)
                                                    .coverPageTemplate( "cover-page-template")
                                                    .overlay("overlayName", OverlayMode.ALL_PAGES)
                                                    .header("header")
                                                    .build();


//From there, we can add the rendering options to the fax request object
FaxRequest.builder()
    .renderingOptions(renderingOptions)
    .build();
```

### 5. Status Report Options
The status report object contains the information on the status report of the fax. It contains the following attributes:
- Report mail: In addition to querying via Webservice, it is possible to request notification for each fax job as soon as processing is completed. The status information can be sent by either HTTP POST or email, or both. Separate email addresses can each be specified for delivery and failed delivery confirmations. If an email address is deleted for either type of confirmation, no notification email will be sent for the confirmation type that was removed. The report emails' format is specified through a template which is filled out with relevant data (Job ID, job status, details on the fax recipients). A default template is available for all customers; however, you can install a customized template. Templates must be encoded in UTF-8 format. In addition, it is possible to specify whether the fax image should be attached to the report or not and if so in which format. Contains the following attributes:
    - Success address: Email address, to which delivery confirmations notifications should be sent.
    - Failure address: Email address, to which failed delivery confirmations notifications should be sent.
    - Attached fax image mode: Determines when the fax image will be attached to the email. Possible options are:
        - NEVER: The fax image will never be attached to the email.
        - ALWAYS: The fax image will always be attached to the email.
        - FAILURE_ONLY: The fax image will be attached to the email only if the fax job failed.
        - SUCCESS_ONLY: The fax image will be attached to the email only if the fax job succeeded.
    - Attached fax image format: The format of the attached fax image. Possible options are:
        - PDF: The fax image will be attached to the email in PDF format.
        - TIFF: The fax image will be attached to the email in TIFF format.
        - PDF_WITH_OCR: The fax image will be attached to the email in PDF format with OCR. 
- HTTP status push: In order to receive notification via HTTP, the JobRequest must contain the httpStatusPush element. The URL to which the HTTP POST request is sent has to be specified. The attributes of the push element are:
    - Target URL: The HTTP URL to post the fax job status to (Webhook). A default URL can be configured for the account.
    - Principal: The username to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client id to obtain access tokens.
    - Credentials: The password to authenticate with the status push endpoint, if required. When using OAUTH2 this is the client secret to obtain access tokens.
    - Authentication mode: The authentication mode to use for the status push endpoint. Supported methods are:
      - HTTP_BASIC: Basic authentication.
      - OAUTH: OAuth authentication.
      - NONE: No authentication.
      - HTTP_DIGEST: Digest authentication.
```Java
//Generate the report mail object
ReportMail reportMail = ReportMail.builder()
                                  .successAddress("success-address")
                                  .failureAddress("failure-address")
                                  .attachedFaxImageMode(FaxImageMode.SUCCESS_ONLY)
                                  .attachedFaxImageFormat(FaxImageFormat.PDF)
                                  .build();

//Generate the HTTP status push object
StatusPush httpStatusPush = StatusPush.builder()
                                      .url("target-url")
                                      .authenticationMethod(AuthenticationMode.HTTP_BASIC)
                                      .username("principal")
                                      .password("credentials")
                                      .build();

//From there we can generate the status report object and add it to the fax request object        
StatusReportOptions statusReport = StatusReportOptions.builder()
                                                      .statusPush(httpStatusPush)
                                                      .reportMail(reportMail)
                                                      .build();

FaxRequest.builder()
          .statusReportOptions(statusReport)
          .build();

```

### 6. Metadata
The metadata object contains the information on the metadata of the fax. It contains the following attributes:
- Customer reference: Information that the customer can use for internal references.
- Job Validity: Contains the valid start/end of a fax job (in ISO 8601 format). If this data is not defined correctly, you will receive a Job Expiration error.
```Java

//Generate the metadata object
Metadata metadata = Metadata.builder().customerReference("customer-reference").jobValidation("2019-01-01T00:00:00Z", "2019-01-01T00:00:00Z").build();

//Or if you want, you can use instances of the java.time.Instant class
Metadata metadata = Metadata.builder().customerReference("customer-reference").jobValidation(Instant.now(), Instant.now().plus(1, ChronoUnit.DAYS)).build();

//From there we can add the metadata object to the fax request object
FaxRequest.builder()
    .metadata(metadata)
    .build();
```

### 7. Reference
The reference object contains the information on the reference of the fax. It contains the following attributes:
- Billing code: Information on the cost center; format is arbitrary (max. 80 characters).
- Billing information: Additional data for internal customer accounting (max. 80 characters).
- Customer defined ID: Freely-defined ID string (max. 256 characters).
    - CUSTOMER_REFERENCE: The reference is a customer reference.
    - JOB_ID: The reference is a job ID.

```Java

//Generate the reference object
Reference reference = Reference.builder()
                               .customerDefinedId("customerDefinedId")
                               .billingCode("billingCode")
                               .billingInfo("billingInfo")
                               .build();


//From there we can add the reference object to the fax request object
FaxRequest.builder()
    .reference(reference)
    .build();
```
### Complete example:
<br>Adding everything together, we can generate the fax request object:
```Java
//Generate the fax request object
FaxRequest faxRequest = FaxRequest.builder()
                .customerNumber("1234567890")
                .reference(reference)
                .faxRecipient(faxRecipient)
                .document(document)
                .transportOptions(transportOptions)
                .renderingOptions(renderingOptions)
                .statusReportOptions(statusReportOptions)
                .metadata(metadata)
                .build();
```
<br> And finally, we can send the fax request to the API (Having already initialized the client object):

```Java
FaxServiceClient client = ...; //Initialize the client object
        
FaxRequest faxRequest = ...; //Generate the fax request object
ApiResponse<String> apiResponse = client.sendFaxRequest(faxRequest);
//Or asynchronously
CompletableFuture<ApiResponse<String>> apiResponseAsync = client.sendFaxRequestAsync(faxRequest);
        
//In case of a successful request (status code 200 or 201), the response object will contain the fax job ID.
//Otherwise, an exception will be thrown, or you can check the status code of the response object.
if(apiResponse.getStatusCode() == 200 || response.getStatusCode() == 201) {
    String faxJobId = response.getValue();
    System.out.println("Fax job ID: " + faxJobId);
}
```

For more information check: [Retarus OpenAPI Documentation](https://developers.retarus.com/docs/fax/api/sending-fax/#send-a-fax) and [Send Fax Example](https://github.com/retarus/retarus-java-fax/blob/main/examples/SendFaxExample.java).


        
