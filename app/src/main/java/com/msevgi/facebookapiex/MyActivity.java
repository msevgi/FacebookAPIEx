package com.msevgi.facebookapiex;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

public class MyActivity extends FragmentActivity {

    private MainFragment mainFragment;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getBus().register(this);

//        if (savedInstanceState == null) {
//            // Add the fragment on initial activity setup
//            mainFragment = new MainFragment();
//            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
//        } else {
//            // Or set the fragment from restored state info
//            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
//        }
        imageView = (ImageView) findViewById(R.id.imageview_profile);
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
//       imageView.setProfileId(user_id.getUser_id());
        Picasso.with(getApplicationContext()).load(user_id.getUser_id()).into(imageView);

    }
}
