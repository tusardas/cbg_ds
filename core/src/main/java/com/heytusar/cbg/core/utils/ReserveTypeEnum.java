package com.heytusar.cbg.core.utils;

public enum ReserveTypeEnum {
	USEABLE(1), WINNING_RESERVE(2);
	
	private final Integer value;
	
	ReserveTypeEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
        return value;
    }
}
