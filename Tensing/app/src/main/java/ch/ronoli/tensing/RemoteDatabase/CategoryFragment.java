package ch.ronoli.tensing.RemoteDatabase;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.ronoli.tensing.R;

/**
 * Created by Oliver on 29.04.2015.
 */
public class CategoryFragment extends Fragment {
    public final String TAG = "CategoryFragment";

    public CategoryFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Hallo CategoryFragment");
        restoreActionBar();
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Categories");
    }
}
