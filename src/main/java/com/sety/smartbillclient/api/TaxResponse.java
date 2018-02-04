package com.sety.smartbillclient.api;

import java.util.List;

public class TaxResponse extends GenericResponse {

    private List<Tax> taxes;

    public List<Tax> getTaxes() {
	return taxes;
    }

    public void setTaxes(List<Tax> taxes) {
	this.taxes = taxes;
    }

    @Override
    public String toString() {
	return "TaxResponse [taxes=" + taxes + "]";
    }
    
    

}
