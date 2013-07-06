package com.autentia.web.rest.wadl.builder.doc;

import java.util.List;

import net.java.dev.wadl._2009._02.Doc;

class DocFromTypeAnnotationBuilder extends DocFromAnnotationBuilderCommons
		implements DocFromAnnotationBuilder {

	@Override
	public Doc build(Object object) {
		Doc doc = new Doc();

		if (object instanceof Class) {
			Class<?> clazz = (Class<?>) object;

			List<String> discoverDocumentation = discoverDocumentedAnnotations(clazz
					.getAnnotations());

			if (discoverDocumentation != null
					&& !discoverDocumentation.isEmpty()) {

				for (String typeDoc : discoverDocumentation) {
					doc.getContent().add(typeDoc);
				}
			}
		}

		return doc;
	}

	@Override
	public boolean supports(Object object) {
		boolean result = false;

		if (object instanceof Class) {
			Class<?> clazz = (Class<?>) object;

			List<String> discoverDocumentation = discoverDocumentedAnnotations(clazz
					.getAnnotations());

			result = !discoverDocumentation.isEmpty();
		}

		return result;
	}
}
