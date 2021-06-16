# Basic Redis Rate-limiting Demo Spring (Java) 

This application demonstrates rate limiting using Redis and Spring.

![alt text](https://github.com/redis-developer/basic-rate-limiting-demo-java/raw/master/preview.png)

# Overview video

Here's a short video that explains the project and how it uses Redis:

[![Watch the video on YouTube](https://github.com/redis-developer/basic-rate-limiting-demo-java/raw/master/docs/YTThumbnail.png)](https://www.youtube.com/watch?v=_mFWjk7ONa8)

## Try it out
<p>
    <a href="https://heroku.com/deploy" target="_blank">
        <img src="https://www.herokucdn.com/deploy/button.svg" alt="Deploy to Heorku" width="200px"/>
    <a>
</p>

<p>
    <a href="https://deploy.cloud.run?dir=server" target="_blank">
        <img src="https://deploy.cloud.run/button.svg" alt="Run on Google Cloud" width="200px"/>
    </a>
    (See notes: How to run on Google Cloud)
</p>


## How to run on Google Cloud

<p>
    If you don't have redis yet, plug it in  (https://spring-gcp.saturnism.me/app-dev/cloud-services/cache/memorystore-redis).
    After successful deployment, you need to manually enable the vpc connector as shown in the pictures:
</p>

1. Open link google cloud console.

![1 step](https://github.com/redis-developer/basic-rate-limiting-demo-java/raw/master/docs/1.png)

2. Click "Edit and deploy new revision" button.

![2 step](https://github.com/redis-developer/basic-rate-limiting-demo-java/raw/master/docs/2.png)

3. Add environment.

![3 step](https://github.com/redis-developer/basic-rate-limiting-demo-java/raw/master/docs/3.png)

4.  Select vpc-connector and deploy application.

![4  step](https://github.com/redis-developer/basic-rate-limiting-demo-java/raw/master/docs/4.png)

<a href="https://github.com/GoogleCloudPlatform/cloud-run-button/issues/108#issuecomment-554572173">
Problem with unsupported flags when deploying google cloud run button
</a>

---

# How it works?

## 1. How the data is stored:
<ol>
    <li>New responses are added key-ip:<pre> SETNX your_ip:PING limit_amount
 Example: SETNX 127.0.0.1:PING 10 </pre><a href="https://redis.io/commands/setnx">
 more information</a> 
 <br> <br>
 </li>
 <li> Set a timeout on key:<pre>EXPIRE your_ip:PING timeout
Example: EXPIRE 127.0.0.1:PING 1000 </pre><a href="https://redis.io/commands/expire">
 more information</a>
 </li>
</ol>
<br/>

## 2. How the data is accessed:
<ol>
    <li>Next responses are get bucket: <pre>GET your_ip:PING
Example: GET 127.0.0.1:PING   
</pre><a href="https://redis.io/commands/get">
more information</a>
<br> <br>
</li>
    <li> Next responses are changed bucket: <pre>DECRBY your_ip:PING amount
Example: DECRBY 127.0.0.1:PING 1</pre>
<a href="https://redis.io/commands/decrby">
more information</a>  </li>
</ol>
 

---

## How to run it locally?

#### Open the files server/.env.example to see the available environment variables. You may set these variables when you start the application.
   	- REDIS_URL: Redis server url
    - REDIS_HOST: Redis server host
	- REDIS_PORT: Redis server port
	- REDIS_PASSWORD: Redis server password

#### Run backend

1. Install gradle (Use Gradle 6.3 or later) (on mac: https://gradle.org/install/) 

2. Install JDK (use 8 or later version) (on mac: https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-macos.htm)

3. Set any relevant environment variables (if not connecting to Redis on localhost:6379). For example:

``` sh
$ REDIS_PORT=6379
```

3. From the root directory of the project, run the following commands:
``` sh
cd server
./gradlew build
./gradlew run
```

4. Point your browser to `localhost:5000`.
