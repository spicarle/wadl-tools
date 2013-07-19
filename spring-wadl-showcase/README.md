spring-wadl-showcase
====================

This is a life example to show how to use the _spring-wadl-generator_ library.

You can view how to configure, Maven, Spring Web MVC, and the complex types (classes), to generate correctly the
WADL and the associated schemas.

Even you can view how to configure the Maven plugin to generate, on compilation time, a zip with the WADL and all the
grammars (schemas for complex types).



How to run the showcase
-----------------------

First you need to compile all the projects. If you haven't done already, you have to go to the parent module and
execute in the command line:

    mvn clean install

Afterwards, return to this module and run the _Jetty_ server with:

    mvn jetty:run

Now you can access to the WADL in the URL: <http://localhost:7070/spring-wadl-showcase/rest/wadl>

To access to some schema of a complex type, use, for example, the URL:
<http://localhost:7070/spring-wadl-showcase/rest/schema/contact>
