package donatienthorez.gallerycontactapp;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class GalleryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.pager_item, container,
                false);

        Uri uri = getArguments().getParcelable(Constants.URI);

        ImageView iv = ((ImageView) layoutView.findViewById(R.id.imageView));
        Picasso.with(getContext()).load(uri).into(iv);

        return layoutView;
    }
}
