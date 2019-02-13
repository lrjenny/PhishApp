package tcss450.uw.edu.phishapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import tcss450.uw.edu.phishapp.model.Credentials;
import tcss450.uw.edu.phishapp.utils.SendPostAsyncTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    private View rootView;
    private OnRegisterFragmentInteractionListener mListener;
    private Credentials mCredentials;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ((Button) rootView.findViewById(R.id.button_registerB)).setOnClickListener(this::finish);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RegisterFragment.OnRegisterFragmentInteractionListener) {
            mListener = (RegisterFragment.OnRegisterFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void finish(View view) {
        EditText email = rootView.findViewById(R.id.editText_emailB);
        EditText pass = rootView.findViewById(R.id.editText_passB);
        EditText user = rootView.findViewById(R.id.editText_username);
        EditText first = rootView.findViewById(R.id.editText_firstname);
        EditText last = rootView.findViewById(R.id.editText_lastname);
        EditText retype_pass = rootView.findViewById(R.id.editText_retype_pass);

        if(email.getText().toString().equals("")) {
            email.setError("Email Cannot Be Empty!");
        } else if (pass.getText().toString().equals("")) {
            pass.setError("Password Cannot Be Empty!");
        } else if (user.getText().toString().equals("")) {
            user.setError("Cannot Be Empty!");
        } else if (first.getText().toString().equals("")) {
            first.setError("Cannot Be Empty!");
        } else if (last.getText().toString().equals("")) {
            last.setError("Cannot Be Empty!");
        } else if(retype_pass.getText().toString().equals("")) {
            retype_pass.setError("Cannot Be Empty!");
        } else if (pass.getText().toString().length() < 6) {
            pass.setError("Password Must Be At Least 6 Characters");
        } else if (!pass.getText().toString().equals(retype_pass.getText().toString())) {
            retype_pass.setError("Passwords must match!");
        } else {
            Credentials c = new Credentials.Builder(email.getText().toString(),
                    pass.getText().toString())
                    .addFirstName(first.getText().toString())
                    .addLastName(last.getText().toString())
                    .addUsername(user.getText().toString())
                    .build();
            //build the web service URL
            Uri uri = new Uri.Builder()
                    .scheme("https")
                    .appendPath(getString(R.string.ep_base_url))
                    .appendPath(getString(R.string.ep_register))
                    .build();
            //build the JSONObject
            JSONObject msg = c.asJSONObject();
            mCredentials = c;
            //instantiate and execute the AsyncTask.
            new SendPostAsyncTask.Builder(uri.toString(), msg)
                    .onPreExecute(this::handleRegisterOnPre)
                    .onPostExecute(this::handleRegisterOnPost)
                    .onCancelled(this::handleErrorsInTask)
                    .build().execute();
//            if (mListener != null) {
//                mListener.onRegisterSuccess(c);
//            }
        }
    }

    /**
     * Handle errors that may occur during the AsyncTask.
     * @param result the error message provide from the AsyncTask
     */
    private void handleErrorsInTask(String result) {
        Log.e("ASYNC_TASK_ERROR", result);
    }

    /**
     * Handle the setup of the UI before the HTTP call to the webservice.
     */
    private void handleRegisterOnPre() {
        mListener.onWaitFragmentInteractionShow();
    }

//TODO: LOGAN DO handleRegisterOnPost for step 61c-f

    /**
     * Handle onPostExecute of the AsynceTask. The result from our webservice is
     * a JSON formatted String. Parse it for success or failure.
     * @param result the JSON formatted String response from the web service
     */
    private void handleRegisterOnPost(String result) {
        try {
            JSONObject resultsJSON = new JSONObject(result);
            boolean success =
                    resultsJSON.getBoolean(
                            getString(R.string.keys_json_login_success));
            if (success) {
                //Login was successful. Switch to the loadSuccessFragment.
                mListener.onRegisterSuccess(mCredentials);
                return;
            } else {
                //Login was unsuccessful. Don’t switch fragments and
                // inform the user
                ((TextView) getView().findViewById(R.id.editText_emailB))
                        .setError("Register Unsuccessful");
            }
            mListener.onWaitFragmentInteractionHide();
        } catch (JSONException e) {
            //It appears that the web service did not return a JSON formatted
            //String or it did not have what we expected in it.
            Log.e("JSON_PARSE_ERROR", result
                    + System.lineSeparator()
                    + e.getMessage());
            mListener.onWaitFragmentInteractionHide();
            ((TextView) getView().findViewById(R.id.editText_emailB))
                    .setError("Register Unsuccessful");
        }
    }


    public interface OnRegisterFragmentInteractionListener extends WaitFragment.OnFragmentInteractionListener{
        void onRegisterSuccess(Credentials c);
    }
}
