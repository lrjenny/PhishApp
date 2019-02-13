package tcss450.uw.edu.phishapp.setlists;

import java.io.Serializable;

public class SetList implements Serializable {

    private final String mLongDate;
    private final String mLocation;
    private final String mVenue;
    private final String mData;
    private final String mNotes;
    private final String mUrl;

    public static class Builder {
        private final String mLongDate;
        private final String mLocation;
        private  String mUrl = "";
        private  String mVenue;
        private  String mData = "";
        private  String mNotes = "";

        public Builder(String longDate, String location, String venue) {
            this.mLongDate = longDate;
            this.mLocation = location;
            this.mVenue = venue;
        }

        public Builder addUrl(final String val) {
            mUrl = val;
            return this;
        }

        public Builder addData(final String val) {
            mData = val;
            return this;
        }

        public Builder addNotes(final String val) {
            mNotes = val;
            return this;
        }

        public SetList build() {
            return new SetList(this);
        }
    }

    private SetList(final Builder builder) {
        this.mLongDate = builder.mLongDate;
        this.mLocation = builder.mLocation;
        this.mUrl = builder.mUrl;
        this.mVenue = builder.mVenue;
        this.mData = builder.mData;
        this.mNotes = builder.mNotes;
    }

    public String getLongDate() {
        return mLongDate;
    }

    public String getLocation() {
        return mLocation;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getVenue() {
        return mVenue;
    }

    public String getData() {
        return mData;
    }

    public String getNotes() {
        return mNotes;
    }


}
