package donatienthorez.gallerycontactapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class GalleryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layoutView = inflater.inflate(R.layout.pager_item, container,
                false);

        Contact contact = getArguments().getParcelable(Constants.CONTACTS);

        if (contact != null) {
            TextView title = ((TextView) layoutView.findViewById(R.id.contact_name));
            title.setText(contact.getName());

            ImageView iv = ((ImageView) layoutView.findViewById(R.id.contact_photo));
            Picasso.with(getContext()).load(contact.getPhoto()).into(iv);
        }

        return layoutView;
    }
}
