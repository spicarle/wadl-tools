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
package com.autentia.web.rest.wadl.builder.namespace;

import com.autentia.xml.namespace.QNameBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.namespace.QName;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class QNameBuilderForBasicTypesTest {

    private static final QNameBuilder Q_NAME_BUILDER = new QNameBuilderFactory().getBuilder();

    private final Class<?> basicType;
    private final String expectedLocalPart;

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {boolean.class, "boolean"},
                {boolean.class, "boolean"},
                {byte.class, "byte"},
                {Byte.class, "byte"},
                {short.class, "short"},
                {Short.class, "short"},
                {int.class, "integer"},
                {Integer.class, "integer"},
                {long.class, "long"},
                {Long.class, "long"},
                {float.class, "float"},
                {Float.class, "float"},
                {double.class, "double"},
                {Double.class, "double"},
                {String.class, "string"},
                {Date.class, "date"}
        });
    }

    public QNameBuilderForBasicTypesTest(Class<?> basicType, String expectedLocalPart) {
        this.basicType = basicType;
        this.expectedLocalPart = expectedLocalPart;
    }

    @Test
    public void givenBasicType_whenBuild_thenReturnStandardSchemaRepresentation() {
        final QName qName = Q_NAME_BUILDER.buildFor(basicType);

        assertThat(qName.getNamespaceURI(), is("http://www.w3.org/2001/XMLSchema"));
        assertThat(qName.getLocalPart(), is(expectedLocalPart));
        assertThat(qName.getPrefix(), is("xs"));
    }
}
