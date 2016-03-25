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

import com.autentia.dummy.JavaMethod;
import com.autentia.xml.schema.ClassTypeDiscoverer;
import com.autentia.web.rest.wadl.builder.namespace.GrammarsDiscoverer;
import com.autentia.web.rest.wadl.builder.namespace.QNameBuilderFactory;
import com.autentia.web.rest.wadl.builder.namespace.QNameConstants;
import net.java.dev.wadl._2009._02.Param;
import net.java.dev.wadl._2009._02.ParamStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(Parameterized.class)
public class ParamBuilderTest {

    private static final MethodContextIterator IGNORED = null;

    @Parameters
    public static Collection<Object[]> parameters() {
        return Arrays.asList(new Object[][]{
                {JavaMethod.WITHOUT_PARAMETERS, null, null},

                {JavaMethod.WITH_PATH_VARIABLE_PARAMETER, new Param()
                        .withStyle(ParamStyle.TEMPLATE)
                        .withRequired(true)
                        .withName("paramName")
                        .withType(QNameConstants.STRING), null},

                {JavaMethod.WITH_PATH_VARIABLE_PARAMETER_WITH_EXPLICIT_NAME, new Param()
                        .withStyle(ParamStyle.TEMPLATE)
                        .withRequired(true)
                        .withName("explicitParamName")
                        .withType(QNameConstants.DATE), null},

                {JavaMethod.WITH_REQUEST_PARAM_PARAMETER, null, new Param()
                        .withStyle(ParamStyle.QUERY)
                        .withRequired(true)
                        .withName("paramName")
                        .withType(QNameConstants.INTEGER)},

                {JavaMethod.WITH_REQUEST_PARAM_PARAMETER_WITH_EXPLICIT_NAME, null, new Param()
                        .withStyle(ParamStyle.QUERY)
                        .withRequired(false)
                        .withDefault("dummyDefaultValue")
                        .withName("explicitParamName")
                        .withType(QNameConstants.LONG)}
        });
    }

    private final Method javaMethod;
    private final Param templateParam;
    private final Param noTemplateParam;

    public ParamBuilderTest(Method javaMethod, Param templateParam, Param noTemplateParam) {
        this.javaMethod = javaMethod;
        this.templateParam = templateParam;
        this.noTemplateParam = noTemplateParam;
    }

    @Test
    public void givenJavaMethod_whenBuildRequest_thenConvertJavaParametersToWadlParams() {
      /*  final MethodContext methodContextMock = mock(MethodContext.class);
        doReturn(javaMethod).when(methodContextMock).getJavaMethod();
        doReturn(new ApplicationContext(IGNORED, new GrammarsDiscoverer(new ClassTypeDiscoverer(new QNameBuilderFactory().getBuilder())))).when(methodContextMock).getParentContext();

        final ParamBuilder paramBuilder = new ParamBuilder(methodContextMock);
        if (templateParam != null) {
            assertThat(paramBuilder.buildTemplateParams().get(0), is(templateParam));
        }
        if (noTemplateParam != null) {
            assertThat(paramBuilder.buildNoTemplateParams().get(0), is(noTemplateParam));
        }*/
    }
}
