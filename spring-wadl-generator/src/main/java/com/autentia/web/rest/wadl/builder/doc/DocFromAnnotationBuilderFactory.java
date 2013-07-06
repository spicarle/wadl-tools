package com.autentia.web.rest.wadl.builder.doc;

import java.util.ArrayList;
import java.util.List;

public class DocFromAnnotationBuilderFactory {

	private static final DocFromAnnotationBuilderFactory INSTANCE = new DocFromAnnotationBuilderFactory();

	private final List<DocFromAnnotationBuilder> docBuilderByAnnotation = new ArrayList<DocFromAnnotationBuilder>();

	private DocFromAnnotationBuilderFactory() {
		docBuilderByAnnotation.add(new DocFromMethodAnnotationBuilder());
		docBuilderByAnnotation.add(new DocFromAnyAnnotationBuilder());
		docBuilderByAnnotation.add(new DocFromTypeAnnotationBuilder());
	}

	public static DocFromAnnotationBuilderFactory getInstance() {
		return INSTANCE;
	}

	public DocFromAnnotationBuilder builderFor(Object object) {
		if (object != null) {
			for (DocFromAnnotationBuilder b : docBuilderByAnnotation) {
				if (b.supports(object)) {
					return b;
				}
			}
		}

		return null;
	}
}
