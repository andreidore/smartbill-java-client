[SmartBill API](http://api.smartbill.ro/) client for java

Table of Contents
=================

* [Maven](#maven)
* [Usage](#usage)
  * [Client](#client)
  * [Invoice](#invoice)
     * [Download](#download) 
  * [Estimate](#estimate)
     * [Download](#estimate-download)   
  * [Email](#email)
  * [Configuration](#configuration)
  * [Stock](#stock)
* [Todo](#todo)

# Maven #

SmartBill client is now in maven central repo.

```xml
<dependency>
    <groupId>com.github.andreidore</groupId>
    <artifactId>smartbillclient</artifactId>
    <version>0.5.0</version>
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

## Invoice

### Download 

```java
byte[] data = client.getInvoicePdf("cif","0","1")

```


## Estimate

### Estimate download

```java
byte[] data = client.getEstimatePdf("cif","0","1")

```

## Email

## Configuration
Configuration

## Stock
```java
List<Stock> stocks = client.getStocks("cif",new Date());
// or
List<Stock> stocks = client.getStocks("cif",new Date(),"warehouse");
// or
List<Stock> stocks = client.getStocks("cif",new Date(),"warehouse","Product 1",null);
// or
List<Stock> stocks = client.getStocks("cif",new Date(),"warehouse",null,"CODE_1");

```


# Todo

* Invoice
  * CreateInvoice
  * ~~GetInvoicePdf~~
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
  * ~~GetEstimatePdf~~
  * DeleteEstimate
  * CancelEstimate
  * RestoreEstimate

* ~~Email~~
  * ~~SendDocument~~

* ~~Configuration~~
  * ~~GetTaxes~~
  * ~~GetSeries~~

* ~~Stock~~
  * ~~GetStock~~


