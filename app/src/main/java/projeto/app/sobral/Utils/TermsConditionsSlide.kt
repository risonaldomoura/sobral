package projeto.app.sobral.Utils

import agency.tango.materialintroscreen.SlideFragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_terms_conditions_slide.*
import projeto.app.sobral.R

/**
 * Created by Risonaldo on 26/04/2018.
 */
class TermsConditionsSlide : SlideFragment() {

    override fun onCreateView(
            inflater: LayoutInflater?,
            container: ViewGroup?,
            savedInstanceState: Bundle?): View? {

        return inflater?.inflate( R.layout.fragment_terms_conditions_slide, container, false )
    }

    override fun canMoveFurther(): Boolean {
        //Log.i("Log", "canMoveFurther() : test");

        if( cb_concordo.isChecked ){
            SPInfo(activity).updateIntroStatus(true)

            val intent = Intent(activity, Login_activity::class.java)
            intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP )
            activity.startActivity( intent )
            activity.finish()
        }
        return cb_concordo.isChecked
    }

    override fun cantMoveFurtherErrorMessage(): String {
        return activity.resources.getString(R.string.slide_4_checkbox_error)
    }

    override fun backgroundColor(): Int {
        return R.color.slide_4
    }

    override fun buttonsColor(): Int {
        return R.color.slide_button
    }
}