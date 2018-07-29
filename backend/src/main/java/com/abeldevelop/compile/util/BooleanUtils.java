package com.abeldevelop.compile.util;

public abstract class BooleanUtils {

	private BooleanUtils() {
		
	}
	
	public static boolean isTrue(Boolean value) {
		return (value != null && value.booleanValue());
	}
}
