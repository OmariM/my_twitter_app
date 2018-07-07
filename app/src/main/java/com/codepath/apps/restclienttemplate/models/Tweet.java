package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class Tweet {

    // list out the attributes
    public String body;
    public long uid; // database ID for the tweet
    public User user;
    public String createdAt;
    public String likes;
    public String retweets;


    public Tweet() {}

    // deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();

        // extract all the values from the JSON
        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.likes = jsonObject.getString("favorite_count");
        tweet.retweets = jsonObject.getString("retweet_count");


        return tweet;
    }


    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLikes() {
        return likes;
    }

    public String getRetweets() {
        return retweets;
    }
}
