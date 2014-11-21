package com.msevgi.facebookapiex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MyActivity extends FragmentActivity {

    private MainFragment mainFragment;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getBus().register(this);
        imageView = (ImageView) findViewById(R.id.imageview_profile);

//        if (savedInstanceState == null) {
//            // Add the fragment on initial activity setup
//            mainFragment = new MainFragment();
//            getSupportFragmentManager().beginTransaction().add(android.R.id.content, mainFragment).commit();
//        } else {
//            // Or set the fragment from restored state info
//            mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(android.R.id.content);
//        }

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
        Intent intent = new Intent(MyActivity.this, ProfileActivity.class);
        intent.putExtra("profile_image_url", user_id.getUser_id());
        startActivity(intent);
        Toast.makeText(getApplicationContext(), "aloo", Toast.LENGTH_LONG).show();

    }
}
