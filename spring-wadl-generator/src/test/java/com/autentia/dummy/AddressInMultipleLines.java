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
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AddressInMultipleLines implements Address {

    private final String street;
    private final String city;
    private final String country;

    public AddressInMultipleLines() {
        this("unknown street", "unknown city", "unknown country");
    }

    public AddressInMultipleLines(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }


    @Override
    public String getStreet() {
        return street + ", " + city + ", " + country;
    }
}
