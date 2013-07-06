package com.autentia.web.rest.wadl.builder.doc;

import java.util.ArrayList;
import java.util.List;

public class DocFromAnnotationBuilderFactory {

	private final List<DocFromAnnotationBuilder> docBuilderByAnnotation = new ArrayList<DocFromAnnotationBuilder>();

	public DocFromAnnotationBuilderFactory() {
		docBuilderByAnnotation.add(new DocFromMethodAnnotationBuilder());
	}

	public DocFromAnnotationBuilder builderFor(Object object) {
		if (object != null) {
			for (DocFromAnnotationBuilder b : docBuilderByAnnotation) {
				if (b.supports(object.getClass())) {
					return b;
				}
			}
		}

		return null;
	}
}
