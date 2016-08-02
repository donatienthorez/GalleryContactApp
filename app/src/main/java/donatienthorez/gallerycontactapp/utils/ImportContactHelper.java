package donatienthorez.gallerycontactapp.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.io.IOException;
import java.util.ArrayList;

import donatienthorez.gallerycontactapp.Constants;
import donatienthorez.gallerycontactapp.GalleryContactApplication;
import donatienthorez.gallerycontactapp.models.Contact;

public class ImportContactHelper {

    private static volatile int cursorFurthestPosition = 0;
    private static Cursor cursor;

    /**
     * Returns the contacts that have picture from the phone.
     */
    public static ArrayList<Contact> getContacts(){
        ContentResolver contentResolver = GalleryContactApplication.getContext().getContentResolver();
        cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, Constants.PROJECTION, null, null, null);

        ArrayList<Contact> contacts = new ArrayList<>();

        if(cursor.moveToPosition(cursorFurthestPosition))
        {
            while (cursor.moveToNext()) {

                Uri photoUri = buildPhotoUri();
                try  {
                    if (contentResolver.openAssetFileDescriptor(photoUri, Constants.READ_ONLY) != null) {
                        contacts.add(buildContact(photoUri));
                        if (contacts.size() == Constants.ITEM_PER_PAGE) {
                            cursorFurthestPosition = cursor.getPosition();
                            return contacts;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
        cursorFurthestPosition = cursor.getPosition();
        return contacts;
    }

    private static Uri buildPhotoUri(){
        String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
        return Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);
    }

    private static Contact buildContact(Uri displayPhotoUri){
        final String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        return new Contact(displayPhotoUri, name);
    }
}
