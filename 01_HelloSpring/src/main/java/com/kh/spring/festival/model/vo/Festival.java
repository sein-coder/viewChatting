package com.kh.spring.festival.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Festival {
	
	private int bboardNo;
	private String bboardContent;
	private String bboardTitle;
	private Date bboardDate;
	private String bboardWriter;
	private int bboardCount;
	private String festival_Pic;
	private String festival_Thumbnail;
	private Date festival_Start;
	private Date festival_End;
	private String festival_Phone;
	private String festival_Homepage;
	private String festival_Address;
	private String festival_Host;
	private String festival_Sub;
	private String festival_Price;
	private String festival_Hashtag;
	private String festival_Proceeding;
	
}
