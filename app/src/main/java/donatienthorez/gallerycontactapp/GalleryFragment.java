package donatienthorez.gallerycontactapp;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class GalleryFragment extends Fragment {

    AssetFileDescriptor assetFileDescriptor;

    public GalleryFragment(AssetFileDescriptor assetFileDescriptor) {
        this.assetFileDescriptor = assetFileDescriptor;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.pager_item, container,
                false);

        InputStream inputStream = null;
        try {
            inputStream = assetFileDescriptor.createInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        ImageView iv = ((ImageView) layoutView.findViewById(R.id.imageView));
        iv.setImageBitmap(bitmap);

        return layoutView;
    }
}
