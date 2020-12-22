# Redis rate-limiting example

![alt text](preview.png)

## Development

```
git clone https://github.com/deliveryweb/redis-rate-limiting-java.git

#### If you install redis manually open src/main/resources/ folder and provide the values for environment variables in application.properties
    spring.redis.database=
    spring.redis.host=
    spring.redis.port=
    spring.redis.password=
    spring.redis.timeout=


#### Setup and run 
``` sh
gradle wrapper
./gradlew build
./gradlew run
```