package com.oracle.s202350101.model;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class KjoRequestDto{

    private List<String> user_id;
    private List<String> user_auth;
    private Object obj;
    private List<?> firList;
    private List<?> secList;


}

