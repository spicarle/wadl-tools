package com.autentia.web.rest.wadl.builder.doc;

import java.lang.reflect.Method;

import net.java.dev.wadl._2009._02.Doc;

class DocFromMethodAnnotationBuilder extends DocFromAnnotationBuilderCommons
		implements DocFromAnnotationBuilder {

	@Override
	public Doc build(Object object) {
		Doc doc = new Doc();

		if (object instanceof Method) {
			Method ctx = (Method) object;

			String discoverDocumentation = discoverDocumentation(ctx
					.getAnnotations());

			if (discoverDocumentation != null
					&& !discoverDocumentation.isEmpty()) {
				doc.getContent().add(discoverDocumentation);
			}
		}

		return doc;
	}

	@Override
	public boolean supports(Object object) {
		boolean result = false;

		if (object != null) {
			result = Method.class.isAssignableFrom(object.getClass());
		}

		return result;
	}

}
