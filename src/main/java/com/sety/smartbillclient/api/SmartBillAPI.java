package com.sety.smartbillclient.api;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface SmartBillAPI {

    @RequestLine("GET /SBORO/api/tax?cif={cif}")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    TaxResponse getTaxes(@Param("cif") String cif);

    
    @RequestLine("GET /SBORO/api/series?cif={cif}&type={type}")
    @Headers({ "Accept: application/json", "Content-Type: application/json" })
    SeriesResponse getSeries(@Param("cif") String cif,@Param("type") SeriesType type);

}
