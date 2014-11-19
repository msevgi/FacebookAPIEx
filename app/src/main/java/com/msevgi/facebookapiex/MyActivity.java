package com.msevgi.facebookapiex;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.facebook.widget.ProfilePictureView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MyActivity extends FragmentActivity {

    private MainFragment mainFragment;
private ProfilePictureView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getBus().register(this);
        imageView=(ProfilePictureView) findViewById(R.id.imageview_profile);
        if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
        } else {
            // Or set the fragment from restored state info
            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getBus().unregister(this);
    }

    protected Bus getBus() {
        return BusProvider.getInstance();
    }

    @Subscribe
    public void getId(Model user_id) {
       imageView.setProfileId(user_id.getUser_id());
//        Picasso.with(getApplicationContext()).load("http://graph.facebook.com/" + user_id.getUser_id() + "/picture").into(imageView);

    }
}
