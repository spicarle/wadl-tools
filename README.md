spring-wadl
===========

Introduction
------------

This project is a set of libraries to generate the WADL of REST services implemented with Spring Web MVC.

Some of the features that make interesting this project are:

 *  Very **easy to integrate** in your already Spring Web MVC project.

 *  Very **low intrusive** with your actual code. Just add one controllers and all is working.

 *  Capable of **generate automatically the grammars sections of the WADL**, and the corresponding schemas for the
    complex types of your REST services. Just adding one standard JAXB annotation in the complex types of your REST
    services.

 *  **Manages automatically simple types**, like strings, integers, booleans, ... **and collections** of these simple
    types.


In this project you can find the next subprojects:

 *  **spring-wadl-generator** - it's the library you need to add in the Maven dependencies of your project pom.xml.

 *  **spring-wadl-showcase** - it's an example use of the library, where you can view how to configure the controllers,
    the annotations, ...



Installation
------------

### Download

Right now we are not in the Maven central repository (we are working on it), so you have to clone the project and
install it in you local machine or your own Maven repository (Artifactory, Archiva, Nexus, or whatever other you use).

But don't worry, it's very easy. In the command line, go to the directory where you want to install the project and
type:

    git clone ...
    cd spring-wadl-generator
    mvn clean install

If you have any problem, be sure you have installed correctly:
[Java](http://www.oracle.com/technetwork/java/javase/downloads/index.html),
[Maven](http://maven.apache.org/), and [Git](http://git-scm.com/)



### Add dependencies

You need to modify the _dependencies_ section of your pom.xml and add:

```xml
...
<dependencies>
    ...
    <dependency>
        <groupId>com.autentia.web.rest</groupId>
        <artifactId>spring-wadl-generator</artifactId>
        <version>1.0</version>
    </dependency>
    ...
</dependencies>
...
```



### Add the Spring Web MVC Controllers

Add the next class to your project:

```java
package whatever.you.want;

import com.autentia.web.rest.wadl.builder.ApplicationBuilder;
import com.autentia.web.rest.wadl.builder.impl.springframework.SpringWadlBuilderFactory;
import com.autentia.xml.schema.SchemaBuilder;
import net.java.dev.wadl._2009._02.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/rest", produces = {"application/xml"})
public class WadlController {

    private final ApplicationBuilder applicationBuilder;
    private final SchemaBuilder schemaBuilder;

    @Autowired
    public WadlController(RequestMappingHandlerMapping handlerMapping) {
        final SpringWadlBuilderFactory wadlBuilderFactory = new SpringWadlBuilderFactory(handlerMapping);
        applicationBuilder = wadlBuilderFactory.getApplicationBuilder();
        schemaBuilder = wadlBuilderFactory.getSchemaBuilder();
    }

    @ResponseBody
    @RequestMapping(value = "wadl", method = RequestMethod.GET)
    public Application generateWadl(HttpServletRequest request) {
        return applicationBuilder.build(request);
    }

    @ResponseBody
    @RequestMapping(value = "schema/{classTypeName}", method = RequestMethod.GET)
    public String generateSchema(@PathVariable String classTypeName) {
        return schemaBuilder.buildFor(classTypeName);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleException() {
        // Do nothing, just magic annotations.
    }
}
```

Be sure this controller is in the Spring Web MVC context (maybe put in a package scanned by the context, or adding it
manually).

Now the WADL generation should be working. With this example you can access it on:
**<http://localhost:8080/my-app/rest/wadl>**



### Schema of complex types

JAXB is used to generate the schema for complex types.

In the simple and common case, you can generate the schema for your complex types (the classes you use in your request
or responses) just annotating the classes with `@XmlAccessorType(XmlAccessType.FIELD)` and `@XmlRootElement`.
For example:

```java
...
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Contact {

    private final String id;
    private final String name;
    private final String email;
    ...
```

In more complex cases you can use any other JAXB annotations in whatever way you want.



Generating WADL on compilation time
-----------------------------------

We show how to configure our projects to access the WADL in runtime like any other resource on our REST API. But
sometimes is either not desirable, or impossible (imagine you have to send the development version of your WADL to some
partner or colleague that don't have access to your development network).

In this cases we can generate the WADL on compilation time, and afterwards send it by the way we want.

To view how to do this, check the projects under **wadl-packaging**.



URLs of interest
----------------

 *  W3C XML Schema Definition Language (XSD) 1.1 Part 1: Structures --> <http://www.w3.org/TR/xmlschema11-1/>

 *  XML Schema Patterns for Common Data Structures --> <http://www.w3.org/2005/07/xml-schema-patterns.html>

 *  JAXB annotations <http://docs.oracle.com/javaee/5/api/javax/xml/bind/annotation/package-summary.html>

 *  Unofficial JAXB Guide --> <https://jaxb.java.net/guide/>
