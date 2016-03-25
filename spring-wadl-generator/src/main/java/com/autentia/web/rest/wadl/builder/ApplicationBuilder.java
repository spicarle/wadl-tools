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

import com.autentia.web.HttpServletRequestUtils;
import net.java.dev.wadl._2009._02.Application;
import net.java.dev.wadl._2009._02.Doc;
import net.java.dev.wadl._2009._02.Grammars;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class ApplicationBuilder {

    private final ResourcesBuilder resourcesBuilder;
    private final IncludeBuilder includeBuilder;

    public ApplicationBuilder(ApplicationContext ctx) {
        resourcesBuilder = new ResourcesBuilder(ctx);
        includeBuilder = new IncludeBuilder(ctx.getGrammarsDiscoverer());
    }

    public Application build(HttpServletRequest request) {
        return build(HttpServletRequestUtils.getBaseUrlOf(request));
    }

    public Application build(String baseUrl) {
        return new Application()
                .withDoc(new Doc().withTitle("REST Service WADL"))
                .withResources(resourcesBuilder.build(baseUrl))
                .withGrammars(new Grammars().withInclude(includeBuilder.build()));
    }

    public String buildHtml(HttpServletRequest request){
        return buildHtml(HttpServletRequestUtils.getBaseUrlOf(request));
    }

    public String buildHtml(String baseUrl) {
        Application application = build(baseUrl);
        final TransformerFactory factory = TransformerFactory.newInstance();
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Application.class);

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ClassPathResource classPathResource = new ClassPathResource("wadl.xslt");
            StreamSource streamSource = new StreamSource(classPathResource.getInputStream());
            final Transformer covTransformer = factory.newTransformer(streamSource);

            ByteArrayOutputStream applicationOutputStream = new ByteArrayOutputStream();
            jaxbMarshaller.marshal(application,applicationOutputStream);
            ByteArrayInputStream applicationXml = new ByteArrayInputStream(applicationOutputStream.toByteArray());
            Source text = new StreamSource(applicationXml);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            covTransformer.transform(text, new StreamResult(outputStream));

            byte[] test = outputStream.toByteArray();
            return IOUtils.toString(new ByteArrayInputStream(test), Charset.forName("UTF-8"));

        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
