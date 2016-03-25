package com.autentia.web.rest.wadl.builder.doc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class DocFromAnnotationBuilderCommons {

	String discoverDocumentation(Annotation annotation) {
		return discoverDocumentation(new Annotation[] { annotation });
	}

	public List<String> discoverDocumentedAnnotations(Annotation[] annotations) {
		List<String> l = new ArrayList<String>();

		for (Annotation a : annotations) {
			String name = a.annotationType().getSimpleName();

			if ("Documented".equals(name)) {
				try {
					Field[] declaredField = a.getClass().getDeclaredFields();

					for (Field f : declaredField) {
						f.setAccessible(true);
						Method object = (Method) f.get(a.getClass());

						if ("value".equals(object.getName())) {
							Object invoke = object.invoke(a);

							if (invoke instanceof String[]) {

								String[] s = (String[]) invoke;

								for (String ss : s) {
									if (ss != null && !ss.isEmpty()) {
										l.add(ss);
									}
								}

							}
							if (invoke instanceof String) {
								String s = (String) invoke;

								l.add(s);
							}
						}
					}

				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}

		return l;
	}

	String discoverDocumentation(Annotation[] annotations) {
		StringBuilder sb = new StringBuilder();

		for (Annotation a : annotations) {

			String name = a.annotationType().getSimpleName();

			if ("Documented".equals(name)) {
				try {
					Field[] declaredField = a.getClass().getDeclaredFields();

					for (Field f : declaredField) {
						f.setAccessible(true);
						Method object = (Method) f.get(a.getClass());

						if ("value".equals(object.getName())) {
							Object invoke = object.invoke(a);

							if (invoke instanceof String[]) {
								String[] s = (String[]) invoke;

								for (String ss : s) {
									if (ss != null && !ss.isEmpty()) {
										sb.append(ss);
									}
								}

							}
							if (invoke instanceof String) {
								String s = (String) invoke;

								sb.append(s);
								// System.out.println("return: " + s);
							}
						}
					}

				} catch (Exception e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}

		String s = sb.toString();

		if (s.isEmpty()) {
			return null;
		} else {
			return s;
		}
	}
}