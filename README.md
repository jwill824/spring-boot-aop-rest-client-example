# samurai - __侍__
*were the military nobility and officer caste of medieval and early-modern Japan.*
*In Chinese, the character 侍 was originally a verb meaning 'to wait upon'.*

## What it does
This is a Spring Rest interface that abstracts a couple things.
1) The invocation of an HTTP exchange
2) The object deserialization associated with the exchange
3) Error handling for the exchange

What this library actually does, isn't quite as simple though. And is quite extraordinary. The library allows for an
interface to be created and instantiated without doing so in the consumer code. This is very similar to [Square's Retrofit library](https://square.github.io/retrofit/).

## How to consume
This is how it works currently:

1) Add to application pom.xml
```xml
<dependency>
    <groupId>io.thingstead.lib.samurai</groupId>
    <artifactId>samurai</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

2) Create an interface:
```java
@Api
public interface NameOfYourApi {
    @GET("/products/{id}/attendance")
    List<Attendance> getAttendance(String id);

    @GET("/products")
    List<Product> getProducts();
}
```

3) Create a configuration class with the values you need:
```java
@ApiConfiguration
public class NameOfYourApiConfiguration {
    @Value("${url}")
    private String baseUrl;

    @Value("${token}")
    private String xToken;

    @Bean
    public NameOfYourApi api() {
        return new RestHttpClientBuilder()
                .baseUrl(baseUrl)
                .header("x-token", xToken)
                .build(NameOfYourApi.class);
    }
}
```
