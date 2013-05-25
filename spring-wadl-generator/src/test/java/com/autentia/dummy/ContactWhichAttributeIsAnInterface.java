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
package com.autentia.dummy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class    ContactWhichAttributeIsAnInterface {

    public static final String EXPECTED_SCHEMA = "<?xml version=\"1.0\" standalone=\"yes\"?>\n"+
            "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"+
            '\n' +
            "  <xs:element name=\"addressInMultipleLines\" type=\"addressInMultipleLines\"/>\n"+
            '\n' +
            "  <xs:element name=\"addressInOneLine\" type=\"addressInOneLine\"/>\n"+
            '\n' +
            "  <xs:element name=\"contactWhichAttributeIsAnInterface\" type=\"contactWhichAttributeIsAnInterface\"/>\n"+
            '\n' +
            "  <xs:complexType name=\"contactWhichAttributeIsAnInterface\">\n"+
            "    <xs:sequence>\n"+
            "      <xs:element name=\"name\" type=\"xs:string\" minOccurs=\"0\"/>\n"+
            "      <xs:element name=\"email\" type=\"xs:string\" minOccurs=\"0\"/>\n"+
            "      <xs:choice>\n"+
            "        <xs:element ref=\"addressInOneLine\"/>\n"+
            "        <xs:element ref=\"addressInMultipleLines\"/>\n"+
            "      </xs:choice>\n"+
            "    </xs:sequence>\n"+
            "  </xs:complexType>\n"+
            '\n' +
            "  <xs:complexType name=\"addressInOneLine\">\n"+
            "    <xs:sequence>\n"+
            "      <xs:element name=\"addressInOneLine\" type=\"xs:string\" minOccurs=\"0\"/>\n"+
            "    </xs:sequence>\n"+
            "  </xs:complexType>\n"+
            '\n' +
            "  <xs:complexType name=\"addressInMultipleLines\">\n"+
            "    <xs:sequence>\n"+
            "      <xs:element name=\"street\" type=\"xs:string\" minOccurs=\"0\"/>\n"+
            "      <xs:element name=\"city\" type=\"xs:string\" minOccurs=\"0\"/>\n"+
            "      <xs:element name=\"country\" type=\"xs:string\" minOccurs=\"0\"/>\n"+
            "    </xs:sequence>\n"+
            "  </xs:complexType>\n"+
            "</xs:schema>\n\n";

    private final String name;
    private final String email;

    @XmlElementRefs({
        @XmlElementRef(type = AddressInOneLine.class),
        @XmlElementRef(type = AddressInMultipleLines.class)
    })
    private final Address address;

    public ContactWhichAttributeIsAnInterface() {
        this("unknown name", "unknown email", null);
    }

    public ContactWhichAttributeIsAnInterface(String name, String email, Address address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
