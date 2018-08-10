package com.abeldevelop.compile.api.resources;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ApiModel(description="Details of result")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResultData {

	@ApiModelProperty(notes="List of errors", required = true, position = 0)
	private List<String> errors;
	
}
