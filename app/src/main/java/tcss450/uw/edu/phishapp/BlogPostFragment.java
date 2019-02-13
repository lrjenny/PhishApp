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

import tcss450.uw.edu.phishapp.blog.BlogPost;

public class BlogPostFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public BlogPostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_blog_post, container, false);
        ((Button) v.findViewById(R.id.buttonFullPost))
                .setOnClickListener(this::fullPost);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public void onStart() {
        super.onStart();
        BlogPost blogPost = ((BlogPost) getArguments().get("post"));
        if (blogPost != null) {
            ((TextView) getActivity().findViewById(R.id.textViewTitleFull)).setText(blogPost.getTitle());
            ((TextView) getActivity().findViewById(R.id.textViewDateFull)).setText(blogPost.getPubDate());
            Spanned htmlAsSpanned = Html.fromHtml(blogPost.getTeaser(), 0);
            ((TextView) getActivity().findViewById(R.id.textViewTeaserFull)).setText(htmlAsSpanned);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fullPost(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((BlogPost)
                getArguments().get("post")).getUrl()));
        startActivity(browserIntent);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
