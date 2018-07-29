package com.abeldevelop.compile.api.controllers;

import org.springframework.http.ResponseEntity;

import com.abeldevelop.compile.api.resources.JsonData;

public interface JsonAPI {

	public ResponseEntity<Void> createJson(JsonData jsonData);
}
