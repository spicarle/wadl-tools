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
package com.autentia.web.rest.wadl.zipper;

import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GrammarsUrisExtractorTest {

    static final String RELATIVE_URI = "schema/contactCollection";
    static final String ABSOLUTE_URI_WITHOUT_HOST = "/schema/contact";
    static final String ABSOLUTE_URI_WITH_HOST = "http://otherServer/schema/absoluteUrl.xsd";
    static final String WADL_WITH_INCLUDE_GRAMMARS = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><application xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://wadl.dev.java.net/2009/02\"><doc title=\"REST Service WADL\"/><grammars><include href=\"" + RELATIVE_URI + "\"/><include href=\"" + ABSOLUTE_URI_WITHOUT_HOST + "\"/><include href=\"" + ABSOLUTE_URI_WITH_HOST + "\"/></grammars><resources base=\"http://localhost:7070/spring-wadl-showcase\"><resource path=\"/rest/contacts\"><method id=\"filterContactsBy\" name=\"GET\"><request><param name=\"name\" style=\"query\" type=\"xs:string\" required=\"true\"/></request><response status=\"200\"><representation xmlns:cntcll=\"http://www.autentia.com" + ABSOLUTE_URI_WITHOUT_HOST + "Collection\" element=\"cntcll:contactCollection\" mediaType=\"application/json\"/></response></method></resource><resource path=\"/rest/contacts\"><method id=\"getAllContacts\" name=\"GET\"><response status=\"200\"><representation xmlns:cntcll=\"http://www.autentia.com/" + RELATIVE_URI + "\" element=\"cntcll:contactCollection\" mediaType=\"application/json\"/></response></method></resource><resource path=\"/rest/contacts/{contactId}\"><param name=\"contactId\" style=\"template\" type=\"xs:string\" required=\"true\"/><method id=\"getContact\" name=\"GET\"><request><param name=\"block\" style=\"query\" type=\"xs:boolean\" default=\"false\" required=\"false\"/></request><response status=\"200\"><representation xmlns:cnt=\"http://www.autentia.com/schema/contact\" element=\"cnt:contact\" mediaType=\"application/json\"/></response></method></resource><resource path=\"/rest/contacts\"><method id=\"addContact\" name=\"POST\"><response status=\"200\"/></method></resource><resource path=\"/rest/echo\"><method id=\"echo\" name=\"GET\"><request><param name=\"msg\" style=\"query\" type=\"xs:string\" default=\"This is the default message\" required=\"false\"/></request><response status=\"200\"><representation element=\"xs:string\" mediaType=\"text/plain\"/></response></method></resource><resource path=\"/rest/wadl\"><method id=\"generateWadl\" name=\"GET\"><response status=\"200\"><representation element=\"application\" mediaType=\"application/xml\"/></response></method></resource><resource path=\"/rest/schema/{classTypeName}\"><param name=\"classTypeName\" style=\"template\" type=\"xs:string\" required=\"true\"/><method id=\"generateSchema\" name=\"GET\"><response status=\"200\"><representation element=\"xs:string\" mediaType=\"application/xml\"/></response></method></resource></resources></application>";
    static final String WADL_WITHOUT_INCLUDE_GRAMMARS = "<application xmlns=\"http://wadl.dev.java.net/2009/02\" xmlns:ns=\"http://superbooks\">\n <grammars>\n  <xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" \n        xmlns:tns=\"http://superbooks\" attributeFormDefault=\"unqualified\" elementFormDefault=\"unqualified\" \n        targetNamespace=\"http://superbooks\">\n    <xs:element name=\"thebook\" type=\"tns:book\"/>\n    <xs:complexType name=\"book\">\n        <xs:sequence>\n            <xs:element minOccurs=\"0\" ref=\"tns:thechapter\"/>\n            <xs:element name=\"id\" type=\"xs:int\"/>\n        </xs:sequence>\n    </xs:complexType>\n  </xs:schema>\n </grammars>\n <resources base=\"http://localhost:7070/\">\n   <resource path=\"/bookstore/{id}\">\n     <param name=\"id\" style=\"template\"/>\n     <method name=\"GET\">\n      <response>\n       <representation mediaType=\"application/xml\" element=\"ns:thebook\"/>\n      </response>\n    </method>\n   </resource>\n   <resource path=\"/books\">\n      <resource path=\"/bookstore/{id}\">\n        <param name=\"id\" style=\"template\"/>\n        <method name=\"GET\">\n          <response>\n           <representation mediaType=\"application/xml\" element=\"ns:thebook\"/>\n          </response>\n        </method>\n      </resource>\n   </resource>\n </resources>  \n</application>";

    private final GrammarsUrisExtractor grammarsUrisExtractor = new GrammarsUrisExtractor();

    @Test
    public void givenWadlWithSeveralGrammars_whenExtract_thenReturnListOfURIs() {
        assertThat(grammarsUrisExtractor.extractFrom(WADL_WITH_INCLUDE_GRAMMARS),
                contains(RELATIVE_URI, ABSOLUTE_URI_WITHOUT_HOST, ABSOLUTE_URI_WITH_HOST));
    }

    @Test
    public void givenWadlWithoutGrammars_whenExtract_thenReturnEmptyListOfURIs() {
        assertThat(grammarsUrisExtractor.extractFrom(WADL_WITHOUT_INCLUDE_GRAMMARS), is(empty()));
    }
}
