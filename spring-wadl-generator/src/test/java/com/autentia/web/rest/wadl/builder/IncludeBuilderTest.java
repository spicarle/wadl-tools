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
package com.autentia.web.rest.wadl.builder;

import com.autentia.web.rest.wadl.builder.namespace.GrammarsDiscoverer;
import net.java.dev.wadl._2009._02.Include;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;

public class IncludeBuilderTest {

    private static final String[] DUMMY_URLS = {"schema/a"};

    private final GrammarsDiscoverer grammarsDiscovererMock = Mockito.mock(GrammarsDiscoverer.class);
    private final IncludeBuilder builder = new IncludeBuilder(grammarsDiscovererMock);

    @Test
    public void name() {
        doReturn(new ArrayList<String>(Arrays.asList(DUMMY_URLS))).when(grammarsDiscovererMock).getSchemaUrlsForComplexTypes();

        final Collection<Include> expectedIncludes = new ArrayList<Include>();
        for (String dummyUrl : DUMMY_URLS) {
            expectedIncludes.add(new Include().withHref(dummyUrl));
        }

        assertThat(builder.build(), containsInAnyOrder(expectedIncludes.toArray()));
    }
}
