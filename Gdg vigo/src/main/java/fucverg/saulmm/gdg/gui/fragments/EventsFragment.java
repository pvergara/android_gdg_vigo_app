package fucverg.saulmm.gdg.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.koushikdutta.async.future.FutureCallback;
import fucverg.saulmm.gdg.Configuration;
import fucverg.saulmm.gdg.R;
import fucverg.saulmm.gdg.data.api.ApiHandler;
import fucverg.saulmm.gdg.data.db.DBHandler;
import fucverg.saulmm.gdg.data.db.entities.Event;
import fucverg.saulmm.gdg.gui.adapters.EventsAdapter;

import java.util.LinkedList;
import java.util.List;

import static android.util.Log.d;

public class EventsFragment extends Fragment {
	private DBHandler dbHandler;
	private LinkedList<Event> linkedEvents;


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_event, null);


		if(savedInstanceState != null) {
			LinkedList<Event> recoveredEvents = (LinkedList<Event>) savedInstanceState.get("events");

			if(recoveredEvents != null) {
				Log.d("[DEBUG] fucverg.saulmm.gdg.gui.fragments.EventsFragment.onCreateView ", "events: " + recoveredEvents.size());
			}
		}

		initApi();
		initUI(rootView);

		return rootView;
	}


	@Override
	public void onSaveInstanceState (Bundle outState) {
		outState.putSerializable("events", linkedEvents);
		d("[DEBUG] fucverg.saulmm.gdg.gui.fragments.EventsFragment.onSaveInstanceState ", "Data saved...");

		super.onSaveInstanceState(outState);

	}


	private void initApi () {
		ApiHandler apiHanler = new ApiHandler(getActivity());
		apiHanler.getEventURL(Configuration.GDG_VIGO_ID);
		apiHanler.getEvents(gdgEventsCallback);

		dbHandler = new DBHandler(getActivity());
		linkedEvents = (LinkedList<Event>) dbHandler.getEvents();
	}


	private void initUI (View rootView) {
		ListView eventList = (ListView) rootView.findViewById(R.id.fe_events_list);

		EventsAdapter eventsAdapter = new EventsAdapter(getActivity(), linkedEvents);
		eventList.setAdapter(eventsAdapter);
	}



	FutureCallback<List<Event>> gdgEventsCallback = new FutureCallback<List<Event>>() {
		@Override
		public void onCompleted (Exception e, List<Event> events) {
			for (Event event : events) {
				String id = event.getId();
				String end = event.getEnd();
				String start = event.getStart();
				String description = event.getDescription();
				String plus_url = event.getgPlusEventLink();
				String group_url = event.getGroup_url();
				String location = event.getLocation();
				String title = event.getTitle();
				String temporal_relation = event.getTemporalRelation();

				dbHandler.insertEvent(id, end, description, start, temporal_relation,
						title, group_url, plus_url, location);

//				d("[DEBUG] fucverg.saulmm.gdg.gui.fragments.EventsFragment.onCompleted ", "ID: "+
//						"\nEND: "+end+
//						"\nSTART: "+start+
//						"\nDESCRIPTION: "+description+
//						"\nPLUS_URL: "+plus_url+
//						"\nGROUP_URL: "+group_url+
//						"\nLOCATION: "+location+
//						"\nTEMPORAL_RELATION: "+temporal_relation);

				// Debug
//				eventsAdapter.add(event);
			}
		}};
}



