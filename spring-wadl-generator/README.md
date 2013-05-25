spring-wadl-generator
=====================

Library you can use to generate WADL automatically from your Spring Web MVC application.



Something about generation of code
----------------------------------

The classes used to build de WADL was generated automatically from the WADL schema. To do this was used de Maven plugin:
**maven-jaxb2-plugin**.

After the generation, the classes was moved to _src/main/java_ and some tuning was made to them. For example to
change some namespaces, or add some features with commons-lang 3.

This tuning should not be a problem, because WADL is a W3C specification, so it's not probable it will change.

Anyway, if is necessary generate these classes again, could be done with:

    mvn jaxb2:generate

This command will generate the classes on _target/generated-source_, and afterwards will be necessary to merge with the
code in _src/main/java_. Be careful with the merge to not destroy the tuning already done!!!
