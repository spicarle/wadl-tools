package com.autentia.web.rest.wadl.builder.doc;

import net.java.dev.wadl._2009._02.Doc;

import com.autentia.web.rest.wadl.builder.MethodContext;

public class DocFromMethodAnnotationBuilder extends
		DocFromAnnotationBuilderCommons implements DocFromAnnotationBuilder {

	@Override
	public Doc build(Object object) {
		Doc doc = null;

		// Method javaMethod = ctx.getJavaMethod();

		if (object instanceof MethodContext) {
			MethodContext ctx = (MethodContext) object;

			String discoverDocumentation = discoverDocumentation(ctx
					.getJavaMethod().getAnnotations());

			System.out.println("discoverDocumentation: "
					+ discoverDocumentation);

			doc = new Doc().withContent(discoverDocumentation);
		}

		return doc;
	}

}
