package com.crudmadtek.demo.services;

import java.text.SimpleDateFormat;
import java.util.HashSet;


import com.crudmadtek.demo.models.Jobindeed;
import com.crudmadtek.demo.repositories.IndeedRepository;
import com.crudmadtek.demo.repositories.JobRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class scheduleraction   {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "HH:mm:ss");
    @Autowired
    IndeedRepository indeedRepository ;

    @Autowired
    JobRepository jobRepository ;

    RestTemplate restTemplate = new RestTemplate();
    //http://127.0.0.1:5000/scrape
    String fooResourceUrl
            = "http://127.0.0.1:5000/run";

    @Scheduled(fixedRate = 86400000)  // every 30 seconds
    public void pullRandomComment() {
        System.out.println("hello");

        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl , String.class);

        //String data = "[{\"userName\": \"sandeep\",\"age\":30},{\"userName\": \"vivan\",\"age\":5}]  ";
        JSONObject jsnobject = new JSONObject(response.getBody());
        JSONArray jsonArray = jsnobject.getJSONArray("data");
        HashSet<Jobindeed> save=new HashSet<Jobindeed>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject explrObject = jsonArray.getJSONObject(i);
            save.add(new Jobindeed(explrObject.get("title").toString(),explrObject.get("location").toString(),

                    explrObject.get("description").toString(),explrObject.get("link_url").toString()
                    ));
            System.out.println(explrObject.get("title").toString());
        }
        indeedRepository.saveAll(save);


    }



}