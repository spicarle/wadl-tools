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

import net.java.dev.wadl._2009._02.Doc;

import com.autentia.web.rest.wadl.builder.doc.DocFromAnnotationBuilder;
import com.autentia.web.rest.wadl.builder.doc.DocFromAnnotationBuilderFactory;

public class DocBuilder {

	private final DocFromAnnotationBuilderFactory factory;

	DocBuilder() {
		factory = DocFromAnnotationBuilderFactory.getInstance();
	}

	Doc build(Object object) {
		Doc doc = null;

		DocFromAnnotationBuilder builderFor = factory.builderFor(object);

		if (builderFor != null) {
			doc = builderFor.build(object);
		}

		return doc;
	}

}
