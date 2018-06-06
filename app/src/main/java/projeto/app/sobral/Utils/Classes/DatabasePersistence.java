package projeto.app.sobral.Utils.Classes;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Risonaldo on 26/04/2018.
 */

public class DatabasePersistence {

    private static FirebaseDatabase mDatabase;

    public static FirebaseDatabase getDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }

        return mDatabase;
    }
}
