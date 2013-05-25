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

public class Contact {

    public static final String EXPECTED_SCHEMA = "<?xml version=\"1.0\" standalone=\"yes\"?>\n" +
            "<xs:schema version=\"1.0\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" +
            '\n' +
            "  <xs:complexType name=\"contact\">\n" +
            "    <xs:sequence/>\n" +
            "  </xs:complexType>\n" +
            "</xs:schema>" +
            "\n\n";

    final String name;
    final String email;

    public Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Contact() {
        this("unknown name", "unknown email");
    }

}
