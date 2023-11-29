package com.oracle.s202350101.model;


import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AjaxResponse {
    private List<?> onelist;
    private Object oneObject;
//    private List<Object> listObject;
    private Map<String,List<String>> mapData;


}
