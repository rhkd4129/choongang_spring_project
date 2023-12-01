package com.oracle.s202350101.model;

import lombok.Data;

import java.util.List;

@Data
public class HijRequestDto{

    private List<String> project_id;
    private List<String> project_approve;
    private List<String> del_status;
    private List<String> project_order;
    private List<String> project_step_seq;
}
