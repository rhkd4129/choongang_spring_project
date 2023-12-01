package com.oracle.s202350101.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class KjoResponse {

    private List<?> firList;
    private Object obj;
    private Object secobj;
    private Object trdobj;
    private List<?> secList;
    private Map<?, ?> firMap;
}
