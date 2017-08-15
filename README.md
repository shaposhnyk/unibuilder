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

## Building URI in Java:

```java
URI uri = UniBuilder.Factory.of("https://www.shaposhnyk.com/svc")
    .appendPath("/clusters?expand=applicationBlock")
    .param("itEnv", "DEV")
    .param("expand", "hosts")
    .build();

// same as new URI("https://www.shaposhnyk.com/svc/clusters?expand=applicationBlock&itEnv=DEV&expand=hosts");
```

## Building URI in Kotlin:

```kotlin
URI uri = UniBuilder.of("https://www.shaposhnyk.com/svc")
    .appendPath("/clusters?expand=applicationBlock")
    .param("itEnv", "DEV")
    .param("expand", "hosts")
    .build();

// same as URI("https://www.shaposhnyk.com/svc/clusters?expand=applicationBlock&itEnv=DEV&expand=hosts")
```