package ch.ronoli.tensing.dataloader;

import android.content.Context;

import org.apache.http.MethodNotSupportedException;

import java.sql.SQLException;
import java.util.List;

import ch.ronoli.tensing.localdb.DataSource;
import ch.ronoli.tensing.models.Exercise;

/**
 * Created by Oliver on 03.05.2015.
 */
public class ExerciseDataLoader extends AbstractDataLoader<List<Exercise>> {
    private DataSource<Exercise> mDataSource;


    public ExerciseDataLoader(Context context, DataSource dataSource) {
        super(context);
        mDataSource = dataSource;
    }

    @Override
    protected List<Exercise> buildList() {
        List<Exercise> exerciseList = null;
        try {
            exerciseList = mDataSource.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exerciseList;
    }

    public void insert(Exercise entity) {
        new InsertTask(this).execute(entity);
    }

    public void update(Exercise entity) {
        new UpdateTask(this).execute(entity);
    }

    public void delete(Exercise entity) {
        new DeleteTask(this).execute(entity);
    }

    private class InsertTask extends ContentChangingTask<Exercise, Void, Void> {
        InsertTask(ExerciseDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Exercise... params) {
            try {
                mDataSource.save(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return (null);
        }
    }

    private class UpdateTask extends ContentChangingTask<Exercise, Void, Void> {
        UpdateTask(ExerciseDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Exercise... params) {
            try {
                mDataSource.update(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (MethodNotSupportedException e) {
                e.printStackTrace();
            }
            return (null);
        }
    }

    private class DeleteTask extends ContentChangingTask<Exercise, Void, Void> {
        DeleteTask(ExerciseDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Exercise... params) {
            try {
                mDataSource.delete(params[0].getId());
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (MethodNotSupportedException e) {
                e.printStackTrace();
            }
            return (null);
        }
    }


}
