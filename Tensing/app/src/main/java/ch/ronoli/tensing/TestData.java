package ch.ronoli.tensing;

import android.content.Context;

import java.sql.SQLException;

import ch.ronoli.tensing.localdb.ExerciseDataSource;
import ch.ronoli.tensing.models.Category;
import ch.ronoli.tensing.models.Exercise;
import ch.ronoli.tensing.models.Type;

/**
 * Created by Oliver on 01.05.2015.
 */
public class TestData {
    public static void setUp(Context context) {
        //Types
        Type t1 = new Type("Theater");
        Type t2 = new Type("Chor");

        //Choir Categories
        Category c1 = new Category("Atmen", t2);
        Category c2 = new Category("Lockern", t2);
        Category c3 = new Category("Stimme", t2);
        Category c4 = new Category("Ressonanz", t2);

        //Theater Categories
        Category c5 = new Category("Lockern", t1);
        Category c6 = new Category("Stimme", t1);
        Category c7 = new Category("Improvisation", t1);

        //Choir Exercises
        Exercise e1 =  new Exercise("1,2,3","1,2,3,4,5,6,7,8 sagen und immer einen Ton höher gehen dabei...", "1,2,3,4,5,6,7,8", "", "", c3);
        Exercise e2 = new Exercise("Liebe Sonne", "Man singt hintereinander liebe sonne liebe sonne liebe sonne liebe sooo und geht dabei in angenehmen tonschritten herunter", "liebe sonne beschreibung", "","",c3);
        Exercise e3 = new Exercise("Maybe my mama", "Maybe my mama text", "Maybe my mama description", "", "", c4);
        Exercise e4 = new Exercise("Kreisen", "Arme, Beine, Füsse etc.", "Kreisen description", "","", c2);
        Exercise e5 = new Exercise("Yoga Übung", "Yoga text", "yoga beschriebig", "", "", c1);

        //Theater Exercises
        Exercise e6 = new Exercise("KreisSpiel", "man macht eine bewegung und im kreis herum macht jeder die bewegung nach und kann sie entweder verstärkt oder in abgeschwächter version nachmachen", "whatever beschreibung", "", "", c5);
        Exercise e7 = new Exercise("Anschreien", "Anschreien Text", "Anschreien beschreibung", "", "", c6);
        Exercise e8 = new Exercise("Imporivisationsübung 1", "text zu impro 1", "beschriebig zu impro 1", "", "", c7);

        ExerciseDataSource eds = new ExerciseDataSource(context);
        try {
            eds.save(e1);
            eds.save(e2);
            eds.save(e3);
            eds.save(e4);
            eds.save(e5);
            eds.save(e6);
            eds.save(e7);
            eds.save(e8);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
