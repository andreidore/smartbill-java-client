[SmartBill API](http://api.smartbill.ro/) client for java

Table of Contents
=================

* [Maven](#maven)
* [Usage](#usage)
  * [Client](#client)
  * [Email](#email)
  * [Configuration](#configuration)
* [Todo](#todo)

# Maven #

SmartBill client is now in maven central repo.

```xml
<dependency>
    <groupId>com.github.andreidore</groupId>
    <artifactId>smartbillclient</artifactId>
    <version>0.3.0</version>
</dependency>
```
# Usage

## Client
Create client

```java
SmartBillClient client = new SmartBillClient("username", "token");

// or
SmartBillClient client = new SmartBillClient("username", "token","url");
```

You can find more info about authentication data [here](http://api.smartbill.ro/#!/Autentificare).


## Email

## Configuration
Configuration

# Todo

* Invoice
  * CreateInvoice
  * GetInvoicePdf
  * DeleteInvoice
  * CancelInvoice
  * RestoreInvoice

* Payment 
  * CreatePayment
  * GetReceiptText
  * DeletePaymentByReceipt
  * DeletePayment
  * GetPaymentStatus

* Estimate (Proforma Invoice)
  * CreateEstimate
  * GetEstimatePdf
  * DeleteEstimate
  * CancelEstimate
  * RestoreEstimate

* ~~Email~~
  * ~~SendDocument~~

* ~~Configuration~~
  * ~~GetTaxes~~
  * ~~GetSeries~~

* Stock
  * GetStock


