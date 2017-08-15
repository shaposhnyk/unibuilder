# UniBilder - Lightweight URL Builder

## What is UniBuilder ?
UniBuilder is a lightweight URL Builder library focused on simplifying building URL for REST webservices.

## Motivation
* Finding that URL/URI java classes are too cumbersome to use?
* Wanting something not so heavy as apache http client, with minimal dependencies?

UniBuilder is for you!

## Installing With Maven

You can use Maven (actually not yet) by including the library:

```xml
<dependency>
    <groupId>com.shaposhnyk</groupId>
    <artifactId>unibuilder</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Building URI in Java

```java
URI uri = UniBuilder.Factory.of("http://petstore.swagger.io/v2")
    .appendPath("/pet/findByStatus")  // request path
    .param("status", "available")     // first value query parameter status
    .param("status", "pending")       // second value query parameter status
    .build();

// same as new URI("http://petstore.swagger.io/v2/pet/findByStatus?status=available&status=pending");
```

## Building URI in Kotlin

```kotlin
URI uri = UniBuilder.of("http://petstore.swagger.io/v2")
    .appendPath("/pet/findByStatus")  // request path
    .param("status", "available")     // first value query parameter status
    .param("status", "pending")       // second value query parameter status
    .build();

// same as URI("http://petstore.swagger.io/v2/pet/findByStatus?status=available&status=pending")
```