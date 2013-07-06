package com.autentia.web.rest.wadl.builder.doc;

import java.lang.annotation.Annotation;

import net.java.dev.wadl._2009._02.Doc;

public class DocFromAnyAnnotationBuilder extends
		DocFromAnnotationBuilderCommons implements DocFromAnnotationBuilder {

	@Override
	public boolean supports(Class<?> clazz) {
		return Annotation[].class.isAssignableFrom(clazz);
	}

	@Override
	public Doc build(Object object) {
		Doc doc = new Doc();

		if (object instanceof Annotation[]) {
			Annotation[] m = (Annotation[]) object;
			String discoverDocumentation = discoverDocumentation(m);

			if (discoverDocumentation != null) {
				doc.getContent().add(discoverDocumentation);
			}
		}

		return doc;
	}
}
