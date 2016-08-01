package donatienthorez.gallerycontactapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Contact> contacts;

    public GalleryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.CONTACTS, contacts.get(position));

        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setArguments(bundle);

        return galleryFragment;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }
}
