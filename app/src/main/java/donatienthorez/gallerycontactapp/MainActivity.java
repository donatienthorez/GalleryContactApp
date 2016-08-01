package donatienthorez.gallerycontactapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private ViewPager viewPager;
    private GalleryPagerAdapter galleryPagerAdapter;
    private ArrayList<Contact> contacts;

    final private int REQUEST_READ_CONTACTS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contacts = new ArrayList<>();

        galleryPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager());
        galleryPagerAdapter.setContacts(contacts);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(galleryPagerAdapter);

        // Request permissions to read contact for Android 6 and higher.
        int hasReadContactsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
        if (hasReadContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_READ_CONTACTS);
        } else {
            new ImportContacts().execute();
        }
    }

    /**
     * Displays and instantiates the buttons of the view pager
     */
    public void instantiateViewPagerButtons(){
        LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.buttonsLayout);
        buttonsLayout.setVisibility(View.VISIBLE);

        Button previous = (Button) findViewById(R.id.previous);
        Button next = (Button) findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem()+1 < contacts.size()) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem()-1 >= 0) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                }
            }
        });
    }

    /**
     * Returns the contacts that have picture from the phone.
     */
    public ArrayList<Contact> getContacts(){

        final String[] PROJECTION = {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, PROJECTION, null, null, null);

        ArrayList<Contact> contacts = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            for (int i = 0; i<1; i++) {
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
                cursor.moveToFirst();

            }
            cursor.close();
        }
        return contacts;
    }


    private class ImportContacts extends AsyncTask<Void, Void, ArrayList<Contact>> {
        protected ArrayList<Contact> doInBackground(Void... urls) {
            return getContacts();
        }

        protected void onPostExecute(ArrayList<Contact> result) {
            contacts = getContacts();
            galleryPagerAdapter.setContacts(contacts);
            instantiateViewPagerButtons();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    new ImportContacts().execute();
                }
            }
        }
    }
}
