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

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class WadlZipper {

    static final String DEFAULT_WADL_FILENAME = "wadl.xml";
    static final String HTML_WADL_FILENAME = "wadl.html";

    static final String DEFAULT_SCHEMA_EXTENSION = ".xsd";
    static final String DEFAULT_JSON_SCHEMA_EXTENSION = ".json";

    private final URI wadlUri;
    private URI wadlHtmlUri = null;
    private boolean generateJsonschema;

    public WadlZipper(String wadlUri) throws URISyntaxException {
        this.wadlUri = new URI(wadlUri);
    }

    public WadlZipper(String wadlUri,String wadlHtmlUri) throws URISyntaxException {
        this.wadlUri = new URI(wadlUri);
        this.wadlHtmlUri = new URI(wadlHtmlUri);
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

            if(wadlHtmlUri!=null){
                final String wadlHtmlContent = httpClient.getAsString(wadlHtmlUri);
                if(wadlHtmlContent!=null&& !wadlHtmlContent.isEmpty()){
                zip.add(HTML_WADL_FILENAME, IOUtils.toInputStream(wadlHtmlContent));
                }
            }

            for (String grammarUri : new GrammarsUrisExtractor().extractFrom(wadlContent)) {
                final URI uri = new URI(grammarUri);
                final String name = composesGrammarFileNameWith(uri,DEFAULT_SCHEMA_EXTENSION);

                final InputStream inputStream = httpClient.getAsStream(wadlUri.resolve(uri));
                zip.add(name, inputStream);

                if(generateJsonschema){
                    final URI jsonUri = new URI(grammarUri.replace("schema","jsonschema"));
                    final String jsonName = composesGrammarFileNameWith(jsonUri,DEFAULT_JSON_SCHEMA_EXTENSION);
                    final InputStream jsonInputStream = httpClient.getAsStream(wadlUri.resolve(jsonUri));
                    zip.add(jsonName, jsonInputStream);
                }
            }

        } finally {
            zip.close();
        }
    }

    private String composesGrammarFileNameWith(URI grammarUri,String extension) {
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

        if (!pathName.toLowerCase().endsWith(extension)) {
            pathName += extension;
        }
        return pathName;
    }

    public void generateJsonschema(boolean generateJsonschema) {
        this.generateJsonschema = generateJsonschema;
    }
}
