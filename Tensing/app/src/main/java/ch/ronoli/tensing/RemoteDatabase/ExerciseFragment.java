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
import ch.ronoli.tensing.models.Exercise;

/**
 * Created by Oliver on 29.04.2015.
 */
public class ExerciseFragment extends Fragment {
    public final String TAG = "ExerciseFragment";

    public ExerciseFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "Hallo ExerciseFragment");
        restoreActionBar();
        return inflater.inflate(R.layout.fragment_excercises, container, false);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Exercises");
    }
}
