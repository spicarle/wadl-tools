package com.autentia.web.rest.wadl.builder;

import net.java.dev.wadl._2009._02.Doc;

import com.autentia.web.rest.wadl.builder.doc.DocFromAnnotationBuilder;
import com.autentia.web.rest.wadl.builder.doc.DocFromAnnotationBuilderFactory;

public class DocBuilder {

	// private final Method javaMethod;
	private final DocFromAnnotationBuilderFactory factory;

	// private final List<Param> templateParams = new ArrayList<Param>();
	// private final List<Param> noTemplateParams = new ArrayList<Param>();
	// private final boolean allParametersAreBuilt = false;

	DocBuilder() {
		// DocBuilder(MethodContext ctx) {
		// javaMethod = ctx.getJavaMethod();
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
