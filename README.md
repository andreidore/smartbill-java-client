[SmartBill API](http://api.smartbill.ro/) client for java

Table of Contents
=================

* [Maven](#maven)
* [Usage](#usage)
  * [Configuration](#configuration)
* [Todo] (#todo)

# Maven #

SmartBill client is now in maven central repo.

```xml
<dependency>
    <groupId>com.github.andreidore</groupId>
    <artifactId>smartbillclient</artifactId>
    <version>0.1.0</version>
</dependency>
```
# Usage

## Configuration
fdfdfdfd

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

* Email
  * SendDocument

* ~~Configuration~~
  * ~~GetTaxes~~
  * ~~GetSeries~~

* Stock
  * GetStock


