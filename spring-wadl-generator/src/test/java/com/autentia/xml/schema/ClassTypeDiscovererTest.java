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

import com.autentia.dummy.Contact;
import com.autentia.dummy.ContactAnnotated;
import com.autentia.dummy.ContactAnnotatedWithDifferentName;
import com.autentia.dummy.Xxx;
import com.autentia.dummy.Xxxo;
import com.autentia.lang.ClassMetadataFromClass;
import com.autentia.web.rest.wadl.builder.namespace.QNameBuilderFactory;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ClassTypeDiscovererTest {

    private static final int THREE_TIMES = 3;

    private final ClassTypeDiscoverer classTypeDiscoverer = new ClassTypeDiscoverer(new QNameBuilderFactory().getBuilder());
    private final Map<String, Class<?>> classTypeByLocalPart = new HashMap<String, Class<?>>() {{
        put("contact", Contact.class);
        put("contactAnnotated", ContactAnnotated.class);
        put("contactWithDifferentName", ContactAnnotatedWithDifferentName.class);
        put("xxx", Xxx.class);
        put("xxxo", Xxxo.class);
    }};

    @Test
    public void givenSeveralClassWithDuplications_whenDiscoverClassTypes_thenGenerateClassTypesWithoutRepetition() {
        addElementsOf(classTypeByLocalPart, THREE_TIMES);

        assertThat(classTypeDiscoverer.getAllByLocalPart().keySet(), hasSize(classTypeByLocalPart.size()));
        for (Map.Entry<String,Class<?>> entry : classTypeByLocalPart.entrySet()) {
            final String localPart = entry.getKey();
            final Class<?> classType = entry.getValue();
            assertThat(classTypeDiscoverer.getBy(localPart).getClazz(), is(typeCompatibleWith(classType)));
        }
    }

    private void addElementsOf(Map<String, Class<?>> classTypeByLocalPart, int times) {
        for (int i = 0; i < times; i++) {
            for (Class<?> clazz : classTypeByLocalPart.values()) {
                classTypeDiscoverer.discoverFor(new ClassMetadataFromClass(clazz));
            }
        }
    }

    @Test(expected = ClassTypeNotFoundException.class)
    public void givenNonExistingLocalPart_whenFind_thenThrowException() {
        classTypeDiscoverer.getBy("nonExistingLocalPart");
    }
}
