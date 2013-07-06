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

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import net.java.dev.wadl._2009._02.Doc;

import org.junit.Test;

import com.autentia.dummy.JavaMethod;
import com.autentia.web.rest.wadl.builder.doc.DocFromMethodAnnotationBuilder;

public class DocBuilderTest {

    private static final MethodContextIterator IGNORED_METHOD_CONTEXT_ITERATOR = null;

    private final DocFromMethodAnnotationBuilder docBuilder = new DocFromMethodAnnotationBuilder();

    @Test
    public void givenVoidAsMethodReturnType_whenBuildRepresentation_thenDoNotAddAnything() throws NoSuchMethodException {
       // final ApplicationContext appCtx = new ApplicationContext(IGNORED_METHOD_CONTEXT_ITERATOR, new GrammarsDiscoverer(new ClassTypeDiscoverer(new QNameBuilderFactory().getBuilder())));
        final MethodContext methodCtxMock = mock(MethodContext.class);

        //doReturn(appCtx).when(methodCtxMock).getParentContext();
//        doReturn(new HashSet<MediaType>() {{
//            add(MediaType.APPLICATION_JSON);
//        }}).when(methodCtxMock).getJavaMethod()
//        ;
        doReturn(JavaMethod.WITH_DOCUMENTED_ANNOTATION).when(methodCtxMock).getJavaMethod();

        final Doc docs = docBuilder.build(methodCtxMock);

        System.out.println(docs);
        //assertThat(docs.);
    }
}
