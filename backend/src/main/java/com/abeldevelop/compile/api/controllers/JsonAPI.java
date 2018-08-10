package com.abeldevelop.compile.api.controllers;

import org.springframework.http.ResponseEntity;

import com.abeldevelop.compile.api.resources.JsonData;
import com.abeldevelop.compile.api.resources.ResultData;

public interface JsonAPI {

	public ResponseEntity<ResultData> createJson(JsonData jsonData);
}
