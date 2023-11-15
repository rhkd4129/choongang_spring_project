package com.oracle.s202350101.model;

import java.util.List;

import lombok.Data;

@Data
public class HijSearchRequestDto {

	// 검색 요건
	private String user_id;
	private int project_id;
	
	private String keyword;
	
	private String tablename;
	
}
