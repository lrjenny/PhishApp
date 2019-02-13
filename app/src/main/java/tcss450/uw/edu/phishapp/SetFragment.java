package tcss450.uw.edu.phishapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import tcss450.uw.edu.phishapp.setlists.SetList;

public class SetFragment extends Fragment {

    private OnSetFragmentInteractionListener mListener;

    public SetFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_set, container, false);
        ((Button) v.findViewById(R.id.buttonSetFullPost)).setOnClickListener(this::fullPost);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetFragmentInteractionListener) {
            mListener = (OnSetFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onStart() {
        super.onStart();
        SetList setList = ((SetList) getArguments().get("set"));
        if (setList != null) {
            ((TextView) getActivity().findViewById(R.id.textViewLongDateFull)).setText(setList.getLongDate());
            ((TextView) getActivity().findViewById(R.id.textViewLocationFull)).setText(setList.getLocation());
            Spanned htmlAsSpanned = Html.fromHtml(setList.getData(), 0);
            ((TextView) getActivity().findViewById(R.id.textViewDataFull)).setText(htmlAsSpanned);
            Spanned htmlAsSpanned2 = Html.fromHtml(setList.getNotes(), 0);
            ((TextView) getActivity().findViewById(R.id.textViewNotesFull)).setText(htmlAsSpanned2);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fullPost(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((SetList)
                getArguments().get("set")).getUrl()));
        startActivity(browserIntent);
    }

    public interface OnSetFragmentInteractionListener {
        void onSetFragmentInteraction(Uri uri);
    }
}
