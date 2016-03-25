/**
 *    Copyright 2013 Autentia Real Business Solution S.L.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.autentia.dummy;

import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.autentia.annotation.Documented;

public class JavaMethod {

	public static final Method WITHOUT_PARAMETERS;
	public static final Method WITH_PATH_VARIABLE_PARAMETER;
	public static final Method WITH_PATH_VARIABLE_PARAMETER_WITH_EXPLICIT_NAME;
	public static final Method WITH_REQUEST_PARAM_PARAMETER;
	public static final Method WITH_REQUEST_PARAM_PARAMETER_WITH_EXPLICIT_NAME;
	public static final Method WITH_DOCUMENTED_ANNOTATION;
	public static final Method WITH_DOCUMENTED_PARAMETER;
	public static final Method WITH_DOCUMENTED_RETURN_TYPE;

	public static final String WITH_DOCUMENTED_ANNOTATION_VALUE = "WITH_DOCUMENTED_ANNOTATION";

	static {
		try {
			WITHOUT_PARAMETERS = JavaMethod.class
					.getDeclaredMethod("methodWithoutParameters");
			WITH_PATH_VARIABLE_PARAMETER = JavaMethod.class.getDeclaredMethod(
					"methodWithPathVariableParameter", String.class);
			WITH_PATH_VARIABLE_PARAMETER_WITH_EXPLICIT_NAME = JavaMethod.class
					.getDeclaredMethod(
							"methodWithPathVariableParameterWithExplicitName",
							Date.class);
			WITH_REQUEST_PARAM_PARAMETER = JavaMethod.class.getDeclaredMethod(
					"methodWithRequestParamParameter", int.class);
			WITH_REQUEST_PARAM_PARAMETER_WITH_EXPLICIT_NAME = JavaMethod.class
					.getDeclaredMethod(
							"methodWithRequestParamParameterWithExplicitName",
							Long.class);
			WITH_DOCUMENTED_ANNOTATION = JavaMethod.class
					.getDeclaredMethod("methodWithDocumentedAnnotation");
			WITH_DOCUMENTED_PARAMETER = JavaMethod.class.getDeclaredMethod(
					"methodWithDocumentedParameter", String.class);
			WITH_DOCUMENTED_RETURN_TYPE = JavaMethod.class
					.getDeclaredMethod("methodWithDocumentedReturnType");

		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public void methodWithoutParameters() {
	}

	public void methodWithPathVariableParameter(@PathVariable String paramName) {
	}

	public void methodWithPathVariableParameterWithExplicitName(
			@PathVariable("explicitParamName") Date paramName) {
	}

	public void methodWithRequestParamParameter(@RequestParam int paramName) {
	}

	public void methodWithRequestParamParameterWithExplicitName(
			@RequestParam(value = "explicitParamName", required = false, defaultValue = "dummyDefaultValue") Long paramName) {
	}

	@Documented(WITH_DOCUMENTED_ANNOTATION_VALUE)
	public void methodWithDocumentedAnnotation() {
	}

	public void methodWithDocumentedParameter(
			@Documented("parameterDocumentation") String paramName) {
	}

	public DocumentedType methodWithDocumentedReturnType() {
		return new DocumentedType();
	}
}
