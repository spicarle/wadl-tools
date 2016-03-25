wadl-zipper
===========

Maven plugin to generate on compilation time a zip file with the WADL, and all the grammars (schemas for complex types),
from an URL.

To use the plugin you only have to add to your pom:

```xml
...
<build>
    ...
    <plugins>
        ...
        <plugin>
            <groupId>com.autentia.web.rest</groupId>
            <artifactId>wadl-zipper-maven-plugin</artifactId>
            <version>1.0-SNAPSHOT</version>
            <configuration>
                <wadlUri>http://localhost:7070/spring-wadl-showcase/rest/wadl</wadlUri>
                <zipFile>${project.build.directory}/wadl.zip</zipFile>
            </configuration>
            <executions>
                <execution>
                    <id>wadl-zip</id>
                    <phase>integration-test</phase>
                    <goals>
                        <goal>zip</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        ...
    </plugins>
    ...
<build>
...
```

Check the version of the plugin, and try to use always the last one.

In _configuration_ section you can set:

 *  **wadlURI** --> This parameter is mandatory, and defines the URI where the WADL resource is located.

 *  **zipFile** --> This parameter is optional (you can see its default value on the example above), and defines
    where the generated zip file will be saved.

With _executions_ section you can see how to configure the plugin to generate the zip file during _integration-test_
Maven phase (to know more about this, see the standard Maven documentation).

Check the `pom.xml` file of **spring-wadl-showcase** project, if you want to see a life example.
