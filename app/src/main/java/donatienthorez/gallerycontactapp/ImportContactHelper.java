package donatienthorez.gallerycontactapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.IOException;
import java.util.ArrayList;

public class ImportContactHelper {

    private static volatile int cursorFarestPosition = 0;
    private static final int ITEM_TO_LOAD = 3;


    /**
     * Returns the contacts that have picture from the phone.
     */
    public static ArrayList<Contact> getContacts(){

        final String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        ContentResolver cr = GalleryContactAppApplication.getContext().getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);

        ArrayList<Contact> contacts = new ArrayList<>();

        if(cursor.moveToPosition(cursorFarestPosition))
        {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
                final Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);

                try  {
                    AssetFileDescriptor fd = cr.openAssetFileDescriptor(displayPhotoUri, "r");
                    if (fd != null) {
                        final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contacts.add(new Contact(displayPhotoUri, name));
                        if (contacts.size() == ITEM_TO_LOAD) {
                            cursorFarestPosition = cursor.getPosition();
                            return contacts;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        cursorFarestPosition = cursor.getPosition();
        return contacts;
    }
}
