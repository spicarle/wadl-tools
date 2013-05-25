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
import com.autentia.dummy.ContactWhichAttributeIsAnInterface;
import org.junit.Test;

import javax.xml.namespace.QName;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class SingleTypeTest {

    private static final QName IGNORED_Q_NAME = null;

    @Test
    public void givenNameOfNonAnnotatedJaxbClass_whenBuild_thenReturnGenericSchema() {
        assertThat(new SingleType(Contact.class, IGNORED_Q_NAME).toSchema(), is(Contact.EXPECTED_SCHEMA));
    }

    @Test
    public void givenNameOfAnnotatedJaxbClass_whenBuild_thenReturnSchemaGeneratedByJaxb() {
        assertThat(new SingleType(ContactAnnotated.class, IGNORED_Q_NAME).toSchema(),
                is(ContactAnnotated.EXPECTED_SCHEMA));
    }

    @Test
    public void givenNameOfAnnotatedJaxbClassWithSpecificTypeName_whenBuild_thenReturnSchemaGeneratedByJaxbWithSpecificName() {
        assertThat(new SingleType(ContactAnnotatedWithDifferentName.class, IGNORED_Q_NAME).toSchema(),
                is(ContactAnnotatedWithDifferentName.EXPECTED_SCHEMA));
    }

    @Test
    public void givenClassWhichAttributeIsAnInterface_whenBuild_thenReturnSchemaWithTheInterface() {
        assertThat(new SingleType(ContactWhichAttributeIsAnInterface.class, IGNORED_Q_NAME).toSchema(),
                is(ContactWhichAttributeIsAnInterface.EXPECTED_SCHEMA));
    }

}
