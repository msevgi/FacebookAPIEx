package com.msevgi.facebookapiex;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";

    private UiLifecycleHelper uiHelper;
    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session,
                         final SessionState state,
                         final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

//    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
//        imageView = (ImageView) view.findViewById(R.id.imageview_profile);
        LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        authButton.setFragment(this);
        // authButton.setReadPermissions(Arrays.asList("user_likes", "user_status"));

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        // For scenarios where the main activity is launched and user
        // session is not null, the session state change notification
        // may not be triggered. Trigger it if it's open/closed.
        Session session = Session.getActiveSession();
        if (session != null && (session.isOpened() || session.isClosed())) {
            onSessionStateChange(session, session.getState(), null);
        }

        uiHelper.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        uiHelper.onSaveInstanceState(outState);
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (state.isOpened()) {
            Log.i(TAG, "Logged in...");
            Bundle params = new Bundle();
            params.putBoolean("redirect", false);
            params.putString("height", "200");
            params.putString("type", "normal");
            params.putString("width", "200");
/* make the API call */
            new Request(
                    session,
                    "/me/picture",
                    params,
                    HttpMethod.GET,
                    new Request.Callback() {
                        public void onCompleted(Response response) {
            /* handle the result */
                            Log.i("mustafa", response.toString());
                            GraphObject responseGraphObject = response.getGraphObject();

                            //Create the JSON object
                            JSONObject json = responseGraphObject.getInnerJSONObject();
                            try {
                                String profilImageUrl=json.getJSONObject("data").getString("url");
                                Log.i("mustafa profil url",profilImageUrl);
                                BusProvider.getInstance().post(new Model(profilImageUrl));
//                                Picasso.with(getActivity().getApplicationContext()).load(profilImageUrl).into(imageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
            ).executeAsync();

//            Request request = Request.newMeRequest(session, new Request.GraphUserCallback() {
//                @Override
//                public void onCompleted(final GraphUser user, Response response) {
//                    // If the response is successful
//
//                    if (session == Session.getActiveSession()) {
//                        if (user != null) {
//                            Log.i("userd id", "mustafa " + user.getId());
//                            Log.i("name view", user.getName());
//                            BusProvider.getInstance().post(new Model(user.getId()));
//                            imageView.setProfileId(user.getId());
//                           // new Async("http://graph.facebook.com/" + user.getId()).execute();
//                        }
////                            user_ID = user.getId();//user id
////                            profileName = user.getName();//user's profile name
////                            userNameView.setText(user.getName());
//
//                    }
//                }
//            });
//            Request.executeBatchAsync(request);

        } else if (state.isClosed()) {
            Log.i(TAG, "Logged out...");
        }
    }

    private class Async extends AsyncTask<Void, Void, Void> {
        private String url;

        public Async(String url) {
            this.url = url;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONObject json = JsonReader.readJsonFromUrl(url);
                Log.i("mustafa", "mustafa" + json.getString("url"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
