package ch.ronoli.tensing.fragments;

import android.app.ActionBar;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

import ch.ronoli.tensing.dataloader.ExerciseDataLoader;
import ch.ronoli.tensing.localdb.ExerciseDataSource;
import ch.ronoli.tensing.models.Exercise;

/**
 * Created by Oliver on 29.04.2015.
 */
public class ExerciseFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<Exercise>> {
    public final String TAG = "ExerciseFragment";
    private static final String ARG_SECTION_NUMBER = "section_number";

    private static final int LOADER_ID = 1;
    private ArrayAdapter adapter;

    public ExerciseFragment(){}


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //setHasOptionsMenu(true);
        adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_list_item_1);
        setEmptyText("No data, please add from menu.");
        setListAdapter(adapter);
        setListShown(false);
        // Initialize a Loader with id '1'. If the Loader with this id already
        // exists, then the LoaderManager will reuse the existing Loader.
        getLoaderManager().initLoader(LOADER_ID, null, this);
        restoreActionBar();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Exercises");
    }

    /**
     * Diese Methode erstellt eine neue Instanz der Klasse ContactFragment
     * @param sectionNumber die seite auf der sich dieses Fragment befinden soll
     * @return
     */
    public static ExerciseFragment newInstance(int sectionNumber) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Loader<List<Exercise>> onCreateLoader(int i, Bundle bundle) {
        ExerciseDataLoader loader = new ExerciseDataLoader(getActivity(),new ExerciseDataSource(getActivity().getApplicationContext()));
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Exercise>> loader, List<Exercise> exercises) {
        adapter.clear();
        for (Exercise exercise : exercises){
            adapter.add(exercise);
        }
        if(isResumed()){
            setListShown(true);
        } else
        {
            setListShownNoAnimation(true);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Exercise>> loader) {
        adapter.clear();
    }
}
