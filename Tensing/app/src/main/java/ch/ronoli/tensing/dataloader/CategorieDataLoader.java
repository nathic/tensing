package ch.ronoli.tensing.dataloader;

import android.content.Context;

import org.apache.http.MethodNotSupportedException;

import java.sql.SQLException;
import java.util.List;

import ch.ronoli.tensing.localdb.DataSource;
import ch.ronoli.tensing.models.Category;

/**
 * Created by Oliver on 03.05.2015.
 */
public class CategorieDataLoader extends AbstractDataLoader<List<Category>> {
    private DataSource<Category> mDataSource;


    public CategorieDataLoader(Context context, DataSource dataSource) {
        super(context);
        mDataSource = dataSource;
    }

    @Override
    protected List<Category> buildList() {
        List<Category> categoryList = null;
        try {
            categoryList = mDataSource.getAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryList;
    }

    public void insert(Category entity) {
        new InsertTask(this).execute(entity);
    }

    public void update(Category entity) {
        new UpdateTask(this).execute(entity);
    }

    public void delete(Category entity) {
        new DeleteTask(this).execute(entity);
    }

    private class InsertTask extends ContentChangingTask<Category, Void, Void> {
        InsertTask(CategorieDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Category... params) {
            try {
                mDataSource.save(params[0]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return (null);
        }
    }

    private class UpdateTask extends ContentChangingTask<Category, Void, Void> {
        UpdateTask(CategorieDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Category... params) {
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

    private class DeleteTask extends ContentChangingTask<Category, Void, Void> {
        DeleteTask(CategorieDataLoader loader) {
            super(loader);
        }

        @Override
        protected Void doInBackground(Category... params) {
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
