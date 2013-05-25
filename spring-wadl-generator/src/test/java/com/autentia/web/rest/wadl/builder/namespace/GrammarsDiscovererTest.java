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
import com.autentia.lang.ClassMetadata;
import com.autentia.lang.ClassMetadataFromClass;
import com.autentia.xml.schema.ClassType;
import com.autentia.xml.schema.ClassTypeDiscoverer;
import com.autentia.xml.schema.SingleType;
import net.java.dev.wadl._2009._02.Application;
import org.junit.Test;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class GrammarsDiscovererTest {

    private static final ClassMetadata[] simpleTypes = {
            new ClassMetadataFromClass(String.class),
            new ClassMetadataFromClass(int.class),
            new ClassMetadataFromClass(Application.class) // Application class is considered a basic type because it's part of the standard.
    };
    private static final ClassMetadata[] complexTypes = {
            new ClassMetadataFromClass(Contact.class),
            new ClassMetadataFromClass(ContactAnnotated.class),
            new ClassMetadataFromClass(ContactAnnotatedWithDifferentName.class)
    };
    private static final SingleType DUMMY_CLASS_TYPE = new SingleType(null, new QName("ignored"));

    private final ClassTypeDiscoverer classTypeDiscovererMock = mock(ClassTypeDiscoverer.class);
    private final GrammarsDiscoverer grammarsDiscoverer = new GrammarsDiscoverer(classTypeDiscovererMock);

    @Test
    public void givenSeveralClass_whenBuild_thenGenerateCorrespondingQNamesOnlyForComplexTypes() {
        doReturn(DUMMY_CLASS_TYPE).when(classTypeDiscovererMock).discoverFor(any(ClassMetadata.class));

        add(complexTypes);
        add(simpleTypes);

        verify(classTypeDiscovererMock, times(complexTypes.length)).discoverFor(argThat(isIn(complexTypes)));
        verify(classTypeDiscovererMock, never()).discoverFor(argThat(isIn(simpleTypes)));
    }

    private void add(ClassMetadata[] classMetadatas) {
        for (ClassMetadata classMetadata : classMetadatas) {
            grammarsDiscoverer.discoverQNameFor(classMetadata);
        }
    }

    @Test
    public void givenSeveralClasses_whenGetURLSchemas_thenReturnURLsBasedOnLocalParts() {
        doReturn(new HashMap<String, ClassType>() {{
            put("a", DUMMY_CLASS_TYPE);
            put("b", DUMMY_CLASS_TYPE);
            put("c", DUMMY_CLASS_TYPE);
        }}).when(classTypeDiscovererMock).getAllByLocalPart();

        final List<String> schemaUrlsForComplexTypes = grammarsDiscoverer.getSchemaUrlsForComplexTypes();

        assertThat(schemaUrlsForComplexTypes, containsInAnyOrder("schema/a", "schema/b", "schema/c"));
    }
}
