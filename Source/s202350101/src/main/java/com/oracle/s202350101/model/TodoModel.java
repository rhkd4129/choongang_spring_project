package com.oracle.s202350101.model;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Map;
@Data
public class TodoModel {
    private List<Code> onelist;
    private Map<Date,List<Todo>> mapData;
}
