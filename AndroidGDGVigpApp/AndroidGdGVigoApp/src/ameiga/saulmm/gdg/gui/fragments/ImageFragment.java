package ameiga.saulmm.gdg.gui.fragments;

import ameiga.saulmm.gdg.Configuration;
import ameiga.saulmm.gdg.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

public class ImageFragment extends Fragment{

	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_image, null);
		initGui(rootView);

		return rootView;
	}


	private void initGui (View rootView) {
		ImageView image = (ImageView) rootView.findViewById(R.id.fi_image);

		Ion.with(getActivity(), "https://plus.google.com/s2/photos/profile/" + Configuration.GROUP_ID + "?sz=300")
				.withBitmap()
				.placeholder(R.drawable.placeholder)
				.intoImageView(image);
	}
}
