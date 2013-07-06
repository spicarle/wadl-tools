package com.autentia.web.rest.wadl.builder;



import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import net.java.dev.wadl._2009._02.Doc;


public class DocBuilder {


	
    Collection<Doc> build(MethodContext ctx) {
    	Method javaMethod = ctx.getJavaMethod();
    	Annotation[] annotations = javaMethod.getAnnotations();
    	
    	for(Annotation a : annotations) {
    		
    		String name = a.annotationType().getSimpleName();
    		
    		if("Documented".equals(name)) {    			
    			try {
					Field[] declaredField = a.getClass().getDeclaredFields();
					
					for(Field f : declaredField) {
						f.setAccessible(true);
						Method object = (Method) f.get(a.getClass());
						
						if("value".equals(object.getName())) {				
							Object invoke = object.invoke(a);
							
							if(invoke instanceof String[]) {
								String[] s = (String[]) invoke;
								
								System.out.println("return: " + s[0]);
							}
							if(invoke instanceof String) {
								String s = (String) invoke;
								
								System.out.println("return: " + s);
							}
						}
					}
				
    			} catch (Exception e) {
    				throw new RuntimeException(e.getMessage(), e);
    			}
    		}
    	}

        return null;
    }
}
