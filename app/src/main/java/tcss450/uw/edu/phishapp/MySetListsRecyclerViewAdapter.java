package tcss450.uw.edu.phishapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tcss450.uw.edu.phishapp.SetListsFragment.OnSetListFragmentInteractionListener;
import tcss450.uw.edu.phishapp.setlists.SetList;

public class MySetListsRecyclerViewAdapter extends RecyclerView.Adapter<MySetListsRecyclerViewAdapter.ViewHolder> {

    private final List<SetList> mValues;
    private final OnSetListFragmentInteractionListener mListener;

    public MySetListsRecyclerViewAdapter(List<SetList> items, OnSetListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_setlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mLongDateView.setText(mValues.get(position).getLongDate());
        holder.mLocationView.setText(mValues.get(position).getLocation());
        holder.mVenueView.setText(mValues.get(position).getLocation());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onSetListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mLongDateView;
        public final TextView mLocationView;
        public final TextView mVenueView;
        public SetList mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mLongDateView = (TextView) view.findViewById(R.id.textViewLongDate);
            mLocationView = (TextView) view.findViewById(R.id.textViewLocation);
            mVenueView = (TextView) view.findViewById(R.id.textViewVenue);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLongDateView.getText() + "'";
        }
    }
}