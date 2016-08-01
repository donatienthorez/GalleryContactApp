package donatienthorez.gallerycontactapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    ViewPager viewPager;
    GalleryPagerAdapter galleryPagerAdapter;

    private static final String[] PROJECTION = {
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);

        ArrayList<Contact> contacts = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(contactId));
                Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);

                try  {
                    AssetFileDescriptor fd = getContentResolver().openAssetFileDescriptor(displayPhotoUri, "r");
                    if (fd != null) {
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        contacts.add(new Contact(displayPhotoUri, name));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }

        galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(), contacts);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(galleryPagerAdapter);
    }
}
