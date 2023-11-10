package com.oracle.s202350101.model;

import lombok.Data;

import java.util.List;

@Data
public class KjoRequestDto{

    private List<String> user_id;
    private List<String> user_auth;
}

