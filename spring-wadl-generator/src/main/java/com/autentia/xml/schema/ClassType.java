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
package com.autentia.xml.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
//import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import javax.xml.namespace.QName;

public abstract class ClassType {

    private static final Logger logger = LoggerFactory.getLogger(ClassType.class);

    final Class<?> clazz;
    final QName qName;

    ClassType(Class<?> clazz, QName qName) {
        this.qName = qName;
        this.clazz = clazz;
    }

    public abstract String toSchema();

    public String toJsonSchema(){
        try {
            ObjectMapper m = Jackson2ObjectMapperBuilder.json().build();
            SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
            m.acceptJsonFormatVisitor(m.constructType(clazz), visitor);
            JsonSchema jsonSchema = visitor.finalSchema();
             return m.writeValueAsString(jsonSchema);
        } catch (Exception e) {
            // Add e to the logger message because JAXB Exceptions has a lot of information in the toString().
            // and some loggers implementations just print the getMessage();
            logger.warn("Cannot generate schema from JAXB annotations for class: " + clazz.getName()
                    + ". Preparing generic Schema.\n" + e, e);
            return null;
        }
    }

    public QName getQName() {
        return qName;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
