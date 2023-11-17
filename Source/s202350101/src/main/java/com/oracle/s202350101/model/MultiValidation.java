package com.oracle.s202350101.model;

import lombok.Data;

import javax.validation.Valid;

@Data
public class MultiValidation {
    @Valid
    private Task task;
    private TaskSub taskSub;
}
