package tcss450.uw.edu.phishapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.Serializable;

import tcss450.uw.edu.phishapp.model.Credentials;

public class MainActivity extends AppCompatActivity
        implements LoginFragment.OnLoginFragmentInteractionListener,
        RegisterFragment.OnRegisterFragmentInteractionListener {

    private LoginFragment rootFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootFragment = new LoginFragment();
        if(savedInstanceState == null) {
            if (findViewById(R.id.frame_main_container) != null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.frame_main_container, rootFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onLoginSuccess(final Credentials credentials, String jwt) {
        Intent i = new Intent(this, HomeActivity.class);
        i.putExtra(getString(R.string.keys_intent_credentials), (Serializable) credentials);
        i.putExtra(getString(R.string.keys_intent_jwt), jwt);
        startActivity(i);
        finish();
    }

    @Override
    public void onRegisterClicked() {
        if (findViewById(R.id.frame_main_container) != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_main_container, new RegisterFragment())
                    .addToBackStack("tag")
                    .commit();
        }
    }

    @Override
    public void onRegisterSuccess(Credentials c) {
        onLoginSuccess(c, "");
//        rootFragment.assign(c);
//        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onWaitFragmentInteractionShow() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_main_container, new WaitFragment(), "WAIT")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onWaitFragmentInteractionHide() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(getSupportFragmentManager().findFragmentByTag("WAIT"))
                .commit();
    }
}
