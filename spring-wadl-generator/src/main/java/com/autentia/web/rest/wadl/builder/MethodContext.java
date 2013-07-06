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

import java.lang.reflect.Method;
import java.util.Set;

import org.springframework.http.MediaType;

import com.autentia.web.HttpMethod;

public abstract class MethodContext {

    private final ApplicationContext parentContext;
    private ParamBuilder paramBuilder;

    protected MethodContext(ApplicationContext parentContext) {
        this.parentContext = parentContext;
    }

    ApplicationContext getParentContext() {
        return parentContext;
    }

    ParamBuilder getParamBuilder() {
        return paramBuilder;
    }

    void setParamBuilder(ParamBuilder paramBuilder) {
        this.paramBuilder = paramBuilder;
    }

    protected abstract String discoverPath();

    //TODO
    public abstract Method getJavaMethod();

    protected abstract Set<HttpMethod> getHttpMethods();

    protected abstract Set<MediaType> getMediaTypes();
}
