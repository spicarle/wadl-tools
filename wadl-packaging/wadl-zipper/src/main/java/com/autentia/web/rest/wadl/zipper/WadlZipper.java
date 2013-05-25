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

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class WadlZipper {

    static final String DEFAULT_WADL_FILENAME = "wadl.xml";
    static final String DEFAULT_SCHEMA_EXTENSION = ".xsd";
    private final URI wadlUri;

    public WadlZipper(String wadlUri) throws URISyntaxException {
        this.wadlUri = new URI(wadlUri);
    }

    public void saveTo(String zipPathName) throws IOException, URISyntaxException {
        saveTo(new File(zipPathName));
    }

    public void saveTo(File zipFile) throws IOException, URISyntaxException {
        final HttpClient httpClient = new HttpClient();
        final Zip zip = new Zip(zipFile);
        saveTo(httpClient, zip);
    }

    /**
     * Just for easily inject dependencies on tests.
     */
    void saveTo(HttpClient httpClient, Zip zip) throws IOException, URISyntaxException {
        try {
            final String wadlContent = httpClient.getAsString(wadlUri);
            zip.add(DEFAULT_WADL_FILENAME, IOUtils.toInputStream(wadlContent));

            for (String grammarUri : new GrammarsUrisExtractor().extractFrom(wadlContent)) {
                final URI uri = new URI(grammarUri);
                final String name = composesGrammarFileNameWith(uri);
                final InputStream inputStream = httpClient.getAsStream(wadlUri.resolve(uri));
                zip.add(name, inputStream);
            }

        } finally {
            zip.close();
        }
    }

    private String composesGrammarFileNameWith(URI grammarUri) {
        String pathName = "";

        final String host = grammarUri.getHost();
        if (host != null) {
            pathName = host + '/';
        }

        String uriPath = grammarUri.getPath();
        if (uriPath.startsWith("/")) {
            uriPath = uriPath.substring(1);
        }
        pathName += uriPath;

        if (!pathName.toLowerCase().endsWith(DEFAULT_SCHEMA_EXTENSION)) {
            pathName += DEFAULT_SCHEMA_EXTENSION;
        }
        return pathName;
    }
}
