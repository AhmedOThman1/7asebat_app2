package com.example.login;

import java.util.HashMap;
import java.util.Map;

public class material {
    public String person_id;
    public String title;
    public String description;
    public String Post_id;
    public int rate_num;
    public int favorite_num;
    public String material_url;

    material(){}

    public material(String person_id,String title, String description , String Post_id , int rate_num , int favorite_num  ,String material_url) {
        this.person_id = person_id ;
        this.title = title;
        this.description = description;
        this.Post_id  = Post_id;
        this.rate_num  = rate_num ;
        this.favorite_num = favorite_num;
        this.material_url = material_url;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Map<String,Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("description", description);
        result.put("material_link", description);

        return result;
    }
}
