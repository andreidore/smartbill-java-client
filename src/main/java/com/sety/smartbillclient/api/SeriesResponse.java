package com.sety.smartbillclient.api;

import java.util.List;

public class SeriesResponse extends GenericResponse {

    private List<SeriesInfo> list;

    public List<SeriesInfo> getList() {
	return list;
    }

    public void setList(List<SeriesInfo> list) {
	this.list = list;
    }

}
