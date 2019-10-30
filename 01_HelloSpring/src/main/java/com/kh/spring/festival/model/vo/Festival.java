package com.kh.spring.festival.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Festival {
	
	private int boardNo;
	private String boardContent;
	private String boardTitle;
	private Date boardDate;
	private String boardWriter;
	private int boardCount;
	private String festival_Pic;
	private String festival_Thumbnail;
	private String fastival_StartDate;
	private String fastival_EndDate;
	private String fastival_Phone;
	private String fastival_Homepage;
	private String fastival_Address;
	private String festival_Host;
	private String festival_Sub;
	private String fastival_Price;
	private String festical_Hashtag;
	private String festival_Proceeding;
	
}
