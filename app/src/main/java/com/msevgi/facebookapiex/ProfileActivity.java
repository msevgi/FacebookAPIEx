package com.msevgi.facebookapiex;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.squareup.picasso.Picasso;

/**
 * Created by msevgi on 11/21/2014.
 */


public class ProfileActivity extends FragmentActivity {
    private UiLifecycleHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        uiHelper = new UiLifecycleHelper(this, null);
        uiHelper.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("profile_image_url");
        Picasso.with(getApplicationContext()).load(url).into(imageView);
        Button button_share = (Button) findViewById(R.id.button_share);
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(ProfileActivity.this).setApplicationName("Oneriyorum").setDescription(
                        "Description"
                )
                        .setLink("www.google.com")
                        .build();
                uiHelper.trackPendingDialogCall(shareDialog.present());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
}
