/**
 *    Copyright 2013 Autentia Real Business Solution S.L.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.autentia.showcase.web.rest.wadl;

import com.autentia.web.rest.wadl.builder.ApplicationBuilder;
import com.autentia.web.rest.wadl.builder.impl.springframework.SpringWadlBuilderFactory;
import com.autentia.xml.schema.ClassType;
import com.autentia.xml.schema.ClassTypeNotFoundException;
import com.autentia.xml.schema.SchemaBuilder;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import net.java.dev.wadl._2009._02.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

import static com.autentia.showcase.web.rest.contacts.AppRestConstants.REST_SERVICES_LOCATION;

@Controller
@Profile("wadl")
@RequestMapping(value = REST_SERVICES_LOCATION)
public class WadlController {

    private final ApplicationBuilder applicationBuilder;
    private final SchemaBuilder schemaBuilder;

    @Autowired
    public WadlController(RequestMappingHandlerMapping handlerMapping) {
        final SpringWadlBuilderFactory wadlBuilderFactory = new SpringWadlBuilderFactory(handlerMapping);
        applicationBuilder = wadlBuilderFactory.getApplicationBuilder();
        schemaBuilder = wadlBuilderFactory.getSchemaBuilder();
    }

    @ResponseBody
    @RequestMapping(value = "wadl", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public Application generateWadl(HttpServletRequest request) {
        return applicationBuilder.build(request);
    }

    @ResponseBody
    @RequestMapping(value = "wadl.html", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
    public String generateWadlHtml(HttpServletRequest request) {
        return applicationBuilder.buildHtml(request);
    }

    @ResponseBody
    @RequestMapping(value = "schema/{classTypeName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_XML_VALUE)
    public String generateSchema(@PathVariable String classTypeName) {
        return schemaBuilder.buildFor(classTypeName);
    }

    @ResponseBody
    @RequestMapping(value = "jsonschema/{classTypeName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String generateJsonSchema(@PathVariable String classTypeName) throws ClassNotFoundException, JsonMappingException {
        return schemaBuilder.buildJsonSchemaFor(classTypeName);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ClassTypeNotFoundException.class)
    public void handleException() {
        // Do nothing, just magic annotations to return 404.
    }
}
