package com.heytusar.cbg.core.utils;

public enum CardAttributeKeyEnum {
	NAME,
	FILE,
	DOB,
	COUNTRY,
	_PLAYED,
	
	_RUNS,
	_HIGHEST_RUNS,
	_RUNS_AVG,
	_100S,
	_50S,
	
	_WICKETS,
	_BBM_W,
	_BBM_R,
	_5WS,
	_10WS;
	
	private String attributeKey; 
	
	public String getAttributeKey(String format) {
		return format.concat(this.attributeKey);
	}
}
