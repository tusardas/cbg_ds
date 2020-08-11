package com.heytusar.cbg.core.utils;

public enum GamePlayerStatusEnum {
	THINKING(1), WAITING(2);
	
	private final Integer value;
	
	GamePlayerStatusEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
}
