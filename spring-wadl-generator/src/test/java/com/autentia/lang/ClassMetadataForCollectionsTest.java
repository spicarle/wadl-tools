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
package com.autentia.lang;

import com.autentia.dummy.Contact;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClassMetadataForCollectionsTest {

    private final ClassMetadata classMetadata;
    private final Class<? extends Collection<?>> expectedCollectionClass;
    private final Class<?> expectedComponentClass;

    @Parameters
    public static Collection<Object[]> parameters() throws Exception {
        return Arrays.asList(new Object[][]{
                {new ClassMetadataFromReturnType(DummyClass.class.getDeclaredMethod("returnsCollection", Collection.class)),
                        Set.class, Integer.class},
                {new ClassMetadataFromParam(DummyClass.class.getDeclaredMethod("returnsCollection", Collection.class), 0),
                        Collection.class, Contact.class},
                {new ClassMetadataFromField(DummyClass.class.getDeclaredField("fieldCollection")),
                        List.class, String.class},
                {new ClassMetadataFromClass(Double[].class),
                        Double[].class, Double.class}
        });
    }

    public ClassMetadataForCollectionsTest(ClassMetadata classMetadata, Class<? extends Collection<?>> expectedCollectionClass, Class<?> expectedComponentClass) {
        this.classMetadata = classMetadata;
        this.expectedCollectionClass = expectedCollectionClass;
        this.expectedComponentClass = expectedComponentClass;
    }

    @Test
    public void givenCollection_whenExtractMetadata_thenDiscoverGenericTypeOfCollection() throws Exception {
        assertThat(classMetadata.isCollection(), is(true));
        assertThat(classMetadata.getType(), is(typeCompatibleWith(expectedCollectionClass)));
        assertThat(classMetadata.getComponentType(), is(typeCompatibleWith(expectedComponentClass)));
    }
}
