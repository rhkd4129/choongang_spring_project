package com.oracle.s202350101.model;


import lombok.Data;

import java.util.List;

@Data
public class AjaxResponse {
    private List<?> onelist;
    private Object oneObject;
}
