package ameiga.saulmm.gdg.gui.fragments;

import static android.util.Log.d;

import java.io.Serializable;
import java.util.List;

import ameiga.saulmm.gdg.R;
import ameiga.saulmm.gdg.data.api.ApiHandler;
import ameiga.saulmm.gdg.data.db.entities.Member;
import ameiga.saulmm.gdg.gui.adapters.MembersAdapter;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class MembersFragment extends Fragment {
	private List<Member> mMembers;


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_member, null);

		if(savedInstanceState != null) {
			savedInstanceState.get("members");

		}

		initApi();
		initUI(rootView);

		return rootView;
	}


	@Override
	public void onSaveInstanceState (Bundle outState) {
		outState.putSerializable("members", (Serializable) mMembers);
		d("[DEBUG] fucverg.saulmm.gdg.gui.fragments.MembersFragment.onSaveInstanceState ", "Members saved...");

		super.onSaveInstanceState(outState);

	}


	private void initApi () {
		ApiHandler apiHanler = new ApiHandler(getActivity());
		mMembers = apiHanler.getMembersAndUpdateDB();
	}

	private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick (AdapterView<?> adapterView, View view, int i, long l) {
			String userID = mMembers.get(i).getId();

			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://plus.google.com/"+userID+"/posts")));

		}
	};


	private void initUI (View rootView) {
		ListView eventList = (ListView) rootView.findViewById(R.id.fm_member_list);
		eventList.setOnItemClickListener(onItemClickListener);

		MembersAdapter membersAdapter = new MembersAdapter(getActivity(), mMembers);
		eventList.setAdapter(membersAdapter);
	}


}



