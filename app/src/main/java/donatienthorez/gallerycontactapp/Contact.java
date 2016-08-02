package donatienthorez.gallerycontactapp;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable{
    Uri photo;
    String name;

    public Contact(Uri photo, String name) {
        this.photo = photo;
        this.name = name;
    }

    public Uri getPhoto() {
        return photo;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeValue(this.photo);
    }

    protected Contact(Parcel in) {
        photo = in.readParcelable(Uri.class.getClassLoader());
        name = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
