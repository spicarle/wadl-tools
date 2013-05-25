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

import com.autentia.dummy.Contact;
import com.autentia.dummy.ContactAnnotated;
import com.autentia.dummy.ContactAnnotatedWithDifferentName;
import com.autentia.dummy.Xxx;
import com.autentia.dummy.Xxxo;
import com.autentia.lang.ClassUtils;
import com.autentia.xml.namespace.QNameBuilder;
import net.java.dev.wadl._2009._02.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class QNameBuilderForComplexTypesTest {

    private static final QNameBuilder Q_NAME_BUILDER = new QNameBuilderFactory().getBuilder();

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {Application.class,
                        new QName("http://wadl.dev.java.net/2009/02", "application", "wadl")},
                {Contact.class,
                        new QName("http://www.autentia.com/schema/contact", "contact", "cnt")},
                {ContactAnnotated.class,
                        new QName("http://www.autentia.com/schema/contactAnnotated", "contactAnnotated", "cntc")},
                {ContactAnnotatedWithDifferentName.class,
                        new QName("http://www.autentia.com/schema/contactWithDifferentName", "contactWithDifferentName", "cntct")},
                {Xxx.class,
                        new QName("http://www.autentia.com/schema/xxx", "xxx", "xxx")},
                {Xxxo.class,
                        new QName("http://www.autentia.com/schema/xxxo", "xxxo", "xxx2")},
                {Integer[].class,
                        new QName("http://www.autentia.com/2013/BasicCollections", "integerCollection", "tcll")},
                {Contact[].class,
                        new QName("http://www.autentia.com/schema/contactCollection", "contactCollection", "cntcll")},
                {new ArrayList<String>().getClass(),
                        new QName("http://www.lang.java/schema/objectCollection", "objectCollection", "bjccll")},  // In Java isn't possible to get de generic type in runtime  :(
        });
    }

    private final Class<?> complexType;
    private final QName expectedQName;

    public QNameBuilderForComplexTypesTest(Class<?> complexType, QName expectedQName) {
        this.complexType = complexType;
        this.expectedQName = expectedQName;
    }

    @Test
    public void givenComplexType_whenBuild_thenReturnComplexSchemaRepresentation() {
        final QName qName;
        if (ClassUtils.isArrayOrCollection(complexType)) {
            qName = Q_NAME_BUILDER.buildForCollectionOf(ClassUtils.getElementsClassOf(complexType));
        } else {
            qName = Q_NAME_BUILDER.buildFor(complexType);
        }

        assertThat(qName, is(expectedQName));
        assertThat(qName.getPrefix(), is(expectedQName.getPrefix())); // Check prefix because QName::equals() ignores it.
    }
}
