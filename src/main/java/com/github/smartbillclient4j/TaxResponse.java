package com.github.smartbillclient4j;

import java.util.List;

public class TaxResponse extends Response {

    private List<Tax> taxes;


    public List<Tax> getTaxes() {
        return this.taxes;
    }

    public void setTaxes(List<Tax> taxes) {
        this.taxes = taxes;
    }


}
