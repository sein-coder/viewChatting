package com.kh.spring.demo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//객체 생성시에 기본적으로 생성해야하는 setter/getter,
//생성자, toString(), hash(), equals()를
//알아서 생성해주는 라이브러리 lombok를 사용하겠음
/*@Setter
@Getter

@EqualsAndHashCode*/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Dev {
	private String devName;
	private int devAge;
	private String devEmail;
	private String devGender;
	private String[] devLang;
}



