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
import org.mockito.ArgumentCaptor;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import static com.autentia.web.rest.wadl.zipper.GrammarsUrisExtractorTest.*;
import static com.autentia.web.rest.wadl.zipper.WadlZipper.DEFAULT_SCHEMA_EXTENSION;
import static com.autentia.web.rest.wadl.zipper.WadlZipper.DEFAULT_WADL_FILENAME;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class WadlZipperTest {

    private static final String HOST_URI = "http://localhost:7070";
    private static final String REST_URI = HOST_URI + "/spring-wadl-showcase/rest";
    private static final String WADL_URI = REST_URI + "/wadl";

    private final WadlZipper wadlZipper;
    private final HttpClient httpClientMock = mock(HttpClient.class);
    private final Zip zipMock = mock(Zip.class);

    public WadlZipperTest() throws URISyntaxException {
        wadlZipper = new WadlZipper(WADL_URI);
    }

    @Test
    public void givenAWadlUri_whenSaveToFile_thenCreateZipWithWadlAndAllGrammars() throws Exception {
        // Given
        doReturn(WADL_WITH_INCLUDE_GRAMMARS).when(httpClientMock).getAsString(new URI(WADL_URI));

        // When
        wadlZipper.saveTo(httpClientMock, zipMock);

        // Then
        final ArgumentCaptor<URI> uriArgument = ArgumentCaptor.forClass(URI.class);
        verify(httpClientMock, times(3)).getAsStream(uriArgument.capture());
        assertThat(uriArgument.getAllValues(),
                contains(URI.create(REST_URI + '/' + RELATIVE_URI), URI.create(HOST_URI + ABSOLUTE_URI_WITHOUT_HOST), URI.create(ABSOLUTE_URI_WITH_HOST)));

        final ArgumentCaptor<String> nameArgument = ArgumentCaptor.forClass(String.class);
        verify(zipMock, times(4)).add(nameArgument.capture(), any(InputStream.class));
        assertThat(nameArgument.getAllValues(),
                contains(DEFAULT_WADL_FILENAME,
                        RELATIVE_URI + DEFAULT_SCHEMA_EXTENSION,
                        ABSOLUTE_URI_WITHOUT_HOST.substring(1) + DEFAULT_SCHEMA_EXTENSION,
                        ABSOLUTE_URI_WITH_HOST.substring(ABSOLUTE_URI_WITH_HOST.indexOf("//") + 2)));
    }
}
