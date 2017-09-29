package pritish.sawant.com.upcomingmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pritish on 29/9/17.
 */

public class Movies implements Parcelable {

    public String title,posterpath,releasedate,overview;
    public long id;
    private Double popularity;
    public boolean adult;

    public Movies() {
    }

    protected Movies(Parcel in) {
        title = in.readString();
        posterpath = in.readString();
        releasedate = in.readString();
        overview = in.readString();
        id = in.readLong();
        popularity = in.readDouble();
        adult = in.readByte() != 0;
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterpath() {
        return posterpath;
    }

    public void setPosterpath(String posterpath) {
        this.posterpath = posterpath;
    }

    public String getReleasedate() {
        return releasedate;
    }

    public void setReleasedate(String releasedate) {
        this.releasedate = releasedate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(posterpath);
        parcel.writeString(releasedate);
        parcel.writeString(overview);
        parcel.writeLong(id);
        parcel.writeDouble(popularity);
        parcel.writeByte((byte) (adult ? 1 : 0));
    }
}
