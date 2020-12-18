package com.example.demo;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;




@SpringBootApplication
@RestController
public class DemoApplication {
    Jedis jedis = new Jedis();

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RequestMapping(value = "/repos/{gitName}", produces = { "application/json; charset=utf-8" })
    @ResponseBody
    public String getGitData(
            @PathVariable("gitName") String gitName) {
        String gitData = jedis.get(gitName);
        boolean isCached = true;
        if (gitData == null) {
            gitData = getGitReposData(gitName);
            isCached = false;
        }
        return String.format("{\"username\":\"%s\",\"repos\":\"%s\",\"cached\":%s}", gitName, gitData, isCached);
    }


    private String getGitReposData(String gitName) {
        try {


            String sURL = String.format("https://api.github.com/users/%s", gitName);
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            try {
                String gitData = jsonObject.get("public_repos").getAsString();
                jedis.setex(gitName, 3600, gitData);
                return gitData;
            } catch (Exception e){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }
}
