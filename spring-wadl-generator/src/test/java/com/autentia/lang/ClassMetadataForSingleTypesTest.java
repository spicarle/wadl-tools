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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ClassMetadataForSingleTypesTest {

    private final ClassMetadata classMetadata;
    private final Class<? extends Collection<?>> expectedClass;

    @Parameters
    public static Collection<Object[]> parameters() throws Exception {
        return Arrays.asList(new Object[][]{
                {new ClassMetadataFromReturnType(DummyClass.class.getDeclaredMethod("returnsLong", Float.class)), Long.class},
                {new ClassMetadataFromParam(DummyClass.class.getDeclaredMethod("returnsLong", Float.class), 0), Float.class},
                {new ClassMetadataFromField(DummyClass.class.getDeclaredField("field")), String.class},
                {new ClassMetadataFromClass(Double.class), Double.class}
        });
    }

    public ClassMetadataForSingleTypesTest(ClassMetadata classMetadata, Class<? extends Collection<?>> expectedClass) {
        this.classMetadata = classMetadata;
        this.expectedClass = expectedClass;
    }

    @Test(expected = IllegalStateException.class)
    public void givenSingleType_whenExtractMetadataAndTryGetComponentType_thenThrowException() throws Exception {
        assertThat(classMetadata.isCollection(), is(false));
        assertThat(classMetadata.getType(), is(typeCompatibleWith(expectedClass)));
        assertThat(classMetadata.getComponentType(), is(typeCompatibleWith(Object.class))); // Should throw exception
    }
}
