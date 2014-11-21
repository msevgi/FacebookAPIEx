package com.msevgi.facebookapiex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by msevgi on 11/21/2014.
 */
public class ProfileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("profile_image_url");
        Picasso.with(getApplicationContext()).load(url).into(imageView);
    }
}
