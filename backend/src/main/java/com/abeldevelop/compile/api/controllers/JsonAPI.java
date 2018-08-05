package com.abeldevelop.compile.api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.abeldevelop.compile.api.resources.JsonData;

public interface JsonAPI {

	public ResponseEntity<List<String>> createJson(JsonData jsonData);
}
