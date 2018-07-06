package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    Context context = this.getParent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

    }



    public void onSubmit(View v) {
        // extract the tweet text from the edit text
        EditText etName = (EditText) findViewById(R.id.etTweet);
        String tweetString = etName.getText().toString();
        if(tweetString.length() > 280) {
            Toast.makeText(this, "Tweet is too long!", Toast.LENGTH_SHORT).show();
            return;
        }
        // get the twitter client
        TwitterClient client = TwitterApp.getRestClient(this);
        // make the network request
        client.sendTweet(tweetString, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // make the tweet and parcel it
                Tweet tweet = null;
                try {
                    tweet = Tweet.fromJSON(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Parcelable wrappedTweet = Parcels.wrap(tweet);
                // Prepare data intent
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("tweet", wrappedTweet);
                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                Toast.makeText(context, "Failed to send Tweet :(", Toast.LENGTH_LONG);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                Log.d("TwitterClient", errorResponse.toString());
                Toast.makeText(context, "Failed to send Tweet :(", Toast.LENGTH_LONG);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("TwitterClient", responseString);
                Toast.makeText(context, "Failed to send Tweet :(", Toast.LENGTH_LONG);
                finish();
            }
        });

    }
}
