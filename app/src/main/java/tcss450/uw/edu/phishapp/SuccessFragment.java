package tcss450.uw.edu.phishapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.model.Credentials;


/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessFragment extends Fragment {

    private View rootView;

    public SuccessFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_success, container, false);
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((TextView) getActivity().findViewById(R.id.textView_display))
                .setText(((Credentials) getArguments()
                        .getSerializable("credentials")).getEmail());
    }
//
//    public SuccessFragment setDisplay(Credentials c) {
//        ((TextView) getArguments().findViewById(R.id.textView_display)).setText(c.getEmail());
//        return this;
//    }
}
