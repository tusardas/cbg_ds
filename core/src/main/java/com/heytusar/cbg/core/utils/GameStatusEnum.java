package com.heytusar.cbg.core.utils;

public enum GameStatusEnum {
	IN_PROGRESS(1), PAUSED(2), FINISHED(3);
	
	private final Integer value;
	
	GameStatusEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
	
}
