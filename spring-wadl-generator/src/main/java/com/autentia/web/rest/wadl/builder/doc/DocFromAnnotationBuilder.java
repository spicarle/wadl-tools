package com.autentia.web.rest.wadl.builder.doc;

import net.java.dev.wadl._2009._02.Doc;

public interface DocFromAnnotationBuilder {

	boolean supports(Class<?> clazz);

	Doc build(Object object);

}
