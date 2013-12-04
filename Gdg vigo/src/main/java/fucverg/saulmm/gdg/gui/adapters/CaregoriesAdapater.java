package fucverg.saulmm.gdg.gui.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import fucverg.saulmm.gdg.gui.fragments.EventsFragment;
import fucverg.saulmm.gdg.gui.fragments.PostsFragment;
import fucverg.saulmm.gdg.gui.fragments.SearchResultFragment;

public class CaregoriesAdapater extends FragmentPagerAdapter {
	private String[] pagerTitles = {"Events", "Posts", "Members", "Gdg Vigo	"};

	private final SearchResultFragment searchFragment = new SearchResultFragment();
	private final EventsFragment eventsFragment = new EventsFragment();
	private final PostsFragment postFragment = new PostsFragment();
	private final PostsFragment postsFragment2 = new PostsFragment();


	public CaregoriesAdapater (FragmentManager fragmentManager, Context context) {
		super(fragmentManager);
	}


	@Override
	public CharSequence getPageTitle (int position) {
		return pagerTitles[position];
	}


	@Override
	public int getCount () {
		return pagerTitles.length;
	}


	@Override
	public Fragment getItem (int selectedPage) {

		switch (selectedPage) {
			case 0: return eventsFragment;
			case 1: return postFragment;
			case 2: return searchFragment;
			case 3: return postsFragment2;

			default: return null;
		}


	}
}
