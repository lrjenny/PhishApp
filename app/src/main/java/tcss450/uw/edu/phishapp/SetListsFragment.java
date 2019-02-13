package tcss450.uw.edu.phishapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tcss450.uw.edu.phishapp.setlists.SetList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetListsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    public static final String ARG_SET_LIST = "lists";
    private List<SetList> mSetLists;
    private OnSetListFragmentInteractionListener mListener;

    public SetListsFragment() {
        // Required empty public constructor
    }

    public static SetListsFragment newInstance(int columnCount) {
        SetListsFragment fragment = new SetListsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("tag", getArguments().getSerializable(ARG_SET_LIST).toString());
        if (getArguments() != null) {
            mSetLists = new ArrayList<SetList>(
                    Arrays.asList((SetList[]) getArguments().getSerializable("lists")));
        } else {
            Log.e("tag", "bad args");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_lists, container, false);

        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MySetListsRecyclerViewAdapter(mSetLists, mListener));
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSetListFragmentInteractionListener) {
            mListener = (OnSetListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSetListFragmentInteractionListener {
        void onSetListFragmentInteraction(SetList setlist);
    }

}
