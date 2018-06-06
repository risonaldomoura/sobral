package projeto.app.sobral.Introducao

import android.content.Context

/**
 * Created by Risonaldo on 26/04/2018.
 */
class SPInfo (val context : Context) {

    fun updateIntroStatus(status: Boolean){
        context
                .getSharedPreferences("PREF", Context.MODE_PRIVATE)
                .edit()
                .putBoolean("status", status)
                .apply()
    }

    fun isIntroShown() = context
            .getSharedPreferences("PREF", Context.MODE_PRIVATE)
            .getBoolean("status", false)
}

