package com.codepath.apps.restclienttemplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailActivity extends AppCompatActivity {

    Tweet tweet;
    TextView tvDetailName;
    TextView tvDetailBody;
    TextView tvDetailTimestamp;
    TextView tvDetailLikes;
    TextView tvDetailRetweets;
    ImageView ivDetailProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // unwarp tweet
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        tvDetailBody = findViewById(R.id.tvDetailBody);
        tvDetailBody.setText(tweet.body);

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailName.setText(tweet.user.name);

        tvDetailTimestamp = findViewById(R.id.tvDetailTimestamp);
        tvDetailTimestamp.setText(getRelativeTimeAgo(tweet.createdAt));

        tvDetailLikes = findViewById(R.id.tvDetailLikes);
        tvDetailLikes.setText(tweet.likes);

        tvDetailRetweets = findViewById(R.id.tvDetailRetweets);
        tvDetailRetweets.setText(tweet.retweets);

        ivDetailProfileImage = findViewById(R.id.ivDetailProfileImage);
        final RoundedCornersTransformation cornersTransformation = new RoundedCornersTransformation( 10, 10);
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .apply(RequestOptions.bitmapTransform(cornersTransformation))
                .into(ivDetailProfileImage);


    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }


}
