package com.autentia.web.rest.wadl.builder.doc;

import java.util.HashMap;
import java.util.Map;

import com.autentia.web.rest.wadl.builder.MethodContext;

public class DocFromAnnotationBuilderFactory {
	
    private final Map<String, DocFromAnnotationBuilder> docBuilderByAnnotation = new HashMap<String, DocFromAnnotationBuilder>();

    public DocFromAnnotationBuilderFactory() {
        docBuilderByAnnotation.put(MethodContext.class.getName(), new DocFromMethodAnnotationBuilder());
      //  paramBuilderByAnnotation.put(PathVariable.class.getName(), new ParamFromPathVariableBuilder(grammarsDiscoverer));
    }

    public DocFromAnnotationBuilder builderFor(Object object) {
        return docBuilderByAnnotation.get(object.getClass().getName());
    }
}
