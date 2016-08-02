package donatienthorez.gallerycontactapp;

import android.provider.ContactsContract;

public class Constants {
    public final static String CONTACTS = "contacts";

    public final static int ITEM_PER_PAGE = 3;


    public final static String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
    };

    public final static String READ_ONLY = "r";

    public final static int REQUEST_READ_CONTACTS = 123;


}
