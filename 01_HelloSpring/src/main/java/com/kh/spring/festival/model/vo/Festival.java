package com.kh.spring.festival.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Festival {
	
	private int fastivalNo;
	private String fastivalContent;
	private String fastivalTitle;
	private Date fastivalDate;
	private String fastivalWriter;
	private int fastivalCount;
	private String fastivalStartDate;
	private String fastivalEndDate;
	private String fastivalPhone;
	private String fastivalHomepage;
	private String fastivalAddress;
	private String festivalHost;
	private String festivalSub;
	private String fastivalPrice;
	private String festivalProceeding;
	
}
