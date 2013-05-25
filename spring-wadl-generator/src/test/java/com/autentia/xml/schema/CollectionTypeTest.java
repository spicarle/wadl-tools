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

import com.autentia.dummy.ContactAnnotated;
import org.junit.Test;

import javax.xml.namespace.QName;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CollectionTypeTest {

    @Test
    public void givenNameOfComplexTypeCollection_whenBuild_thenReturnSchemaWithTheCollection() {
        assertThat(new CollectionType(ContactAnnotated[].class, new QName("contactAnnotatedCollection"), new QName("contactAnnotated")).toSchema(),
                is(ContactAnnotated.EXPECTED_COLLECTION_SCHEMA));
    }

    @Test
    public void basicCollection() {


    }
}
