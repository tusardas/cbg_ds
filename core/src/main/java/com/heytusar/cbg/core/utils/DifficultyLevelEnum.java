package com.heytusar.cbg.core.utils;

public enum DifficultyLevelEnum {
	EASY(3), MEDIUM(2), HARD(3);
	
	private final Integer value;
	
	DifficultyLevelEnum(Integer value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
}
