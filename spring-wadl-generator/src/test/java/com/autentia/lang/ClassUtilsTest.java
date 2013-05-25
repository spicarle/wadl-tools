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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.autentia.lang.ClassUtils.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.typeCompatibleWith;
import static org.junit.Assert.assertThat;

public class ClassUtilsTest {

    @Test
    public void givenSingleType_whenAskIsArrayOrCollection_thenReturnFalse() {
        assertThat(isArrayOrCollection(String.class), is(false));
        assertThat(isArrayOrCollection(Integer.class), is(false));
        assertThat(isArrayOrCollection(Contact.class), is(false));
    }

    @Test
    public void givenArray_whenAskIsArrayOrCollection_thenReturnTrue() {
        assertThat(isArrayOrCollection(String[].class), is(true));
        assertThat(isArrayOrCollection(Integer[].class), is(true));
        assertThat(isArrayOrCollection(Contact[].class), is(true));
    }

    @Test
    public void givenCollection_whenAskIsArrayOrCollection_thenReturnTrue() {
        final List<String> stringsList = new ArrayList<String>();
        final Set<Integer> integersSet = new HashSet<Integer>();
        final Collection<Contact> contactsCollection = new ArrayList<Contact>();

        assertThat(isArrayOrCollection(stringsList.getClass()), is(true));
        assertThat(isArrayOrCollection(integersSet.getClass()), is(true));
        assertThat(isArrayOrCollection(contactsCollection.getClass()), is(true));
    }

    @Test
    public void givenNormalClass_whenAskIsVoid_thenReturnFalse() {
        assertThat(isVoid(String.class), is(false));
        assertThat(isVoid(Integer.class), is(false));
        assertThat(isVoid(Contact[].class), is(false));
    }

    @Test
    public void givenVoidClass_whenAskIsVoid_thenReturnTrue() {
        assertThat(isVoid(Void.class), is(true));
        assertThat(isVoid(void.class), is(true));
    }

    @Test
    public void givenArray_whenGetElementsClassOf_thenReturnTheClassOfElements() {
        assertThat(getElementsClassOf(String[].class), is(typeCompatibleWith(String.class)));
        assertThat(getElementsClassOf(Integer[].class), is(typeCompatibleWith(Integer.class)));
        assertThat(getElementsClassOf(Contact[].class), is(typeCompatibleWith(Contact.class)));
    }
}
