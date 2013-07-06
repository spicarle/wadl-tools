package com.autentia.web.rest.wadl.builder;

import net.java.dev.wadl._2009._02.Doc;

import com.autentia.web.rest.wadl.builder.doc.DocFromAnnotationBuilder;
import com.autentia.web.rest.wadl.builder.doc.DocFromAnnotationBuilderFactory;

public class DocBuilder {

	private final DocFromAnnotationBuilderFactory factory;

	DocBuilder() {
		factory = new DocFromAnnotationBuilderFactory();
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
