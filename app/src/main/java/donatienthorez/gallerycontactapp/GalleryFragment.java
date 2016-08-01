package donatienthorez.gallerycontactapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GalleryFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.pager_item, container,
                false);
        ImageView tv = ((ImageView) layoutView.findViewById(R.id.imageView));
        tv.setImageResource(R.drawable.image);

        return layoutView;
    }
}
