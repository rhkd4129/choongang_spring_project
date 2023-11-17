package com.oracle.s202350101.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paging {
	private int currentPage = 1;	private int rowPage = 15;
	private int pageBlock = 10;
	private int start;				private int end;
	private int startPage;			private int endPage;
	private int total;				private int totalPage;
	
	public Paging(int total, String currentPage1) {
		this.total = total;  //140
		if (currentPage1 != null) {
			this.currentPage = Integer.parseInt(currentPage1); // 2
		}
		//				1				10
		start 	= (currentPage - 1) * rowPage + 1;	//시작시  1	11
		end		= start + rowPage - 1;				//시작시 10	20
		
		//									25    /   10
		totalPage = (int) Math.ceil((double)total / rowPage);
		//				2			2
		startPage = currentPage - (currentPage - 1) % pageBlock; // 시작시1
		endPage = startPage + pageBlock - 1; //10
		//		10		14
		if (endPage > totalPage) {
			endPage = totalPage;
		}
	}
	
	public Paging(int total, String currentPage1, int rowPage) {
		this.total = total;										//140
		this.rowPage = rowPage;
		if (currentPage1 != null) {
			this.currentPage = Integer.parseInt(currentPage1);	//2
		}
		//	1				10
		start = (currentPage - 1) * rowPage + 1;				// 시작시 1
		end = start + rowPage - 1;								// 시작시 10
		//							25		10
		totalPage = (int) Math.ceil((double) total / rowPage);
		//		2				2
		startPage = currentPage - (currentPage - 1) % pageBlock;//	시작시 1
		endPage = startPage + pageBlock - 1;		//	10
		//		10			14
		if (endPage > totalPage) {
			endPage = totalPage;
		}
	}
}
