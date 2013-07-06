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
package com.autentia.web.rest.wadl.builder;

import java.util.Arrays;

import net.java.dev.wadl._2009._02.Doc;

import com.autentia.dummy.JavaMethod;
import com.autentia.web.rest.wadl.builder.doc.DocFromMethodAnnotationBuilder;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DocBuilderTest {

	private final DocFromMethodAnnotationBuilder docBuilder = new DocFromMethodAnnotationBuilder();

	@Test
	public void givenAnnotatedMethod_whenBuildDoc_thenConvertDocumentedAnnotationToWadlDoc()
			throws NoSuchMethodException {
		final Doc doc = docBuilder.build(JavaMethod.WITH_DOCUMENTED_ANNOTATION);

		assertEquals(
				Arrays.asList(JavaMethod.WITH_DOCUMENTED_ANNOTATION_VALUE),
				doc.getContent());
	}

	@Test
	public void givenNoAnnotatedMethod_whenBuildDoc_thenDoNothing()
			throws NoSuchMethodException {
		final Doc doc = docBuilder
				.build(JavaMethod.WITH_PATH_VARIABLE_PARAMETER);

		assertThat(doc.getContent(), is(empty()));
	}
}
