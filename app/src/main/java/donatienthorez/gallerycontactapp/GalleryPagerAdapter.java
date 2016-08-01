package donatienthorez.gallerycontactapp;


import android.content.res.AssetFileDescriptor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class GalleryPagerAdapter extends FragmentStatePagerAdapter {

    ArrayList<AssetFileDescriptor> arrayList;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<AssetFileDescriptor> arrayList) {
        super(fm);
        this.arrayList = arrayList;
    }

    @Override
    public Fragment getItem(int position) {

        return new GalleryFragment(arrayList.get(position));
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
}
