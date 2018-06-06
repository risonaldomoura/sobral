package projeto.app.sobral.Introducao

import agency.tango.materialintroscreen.MaterialIntroActivity
import agency.tango.materialintroscreen.SlideFragmentBuilder
import android.content.Intent
import android.os.Bundle
import projeto.app.sobral.R
import projeto.app.sobral.Utils.Activities.Login_activity

/**
 * Created by Risonaldo on 26/04/2018.
 */
class IntroActivity : MaterialIntroActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verifyIntroActivity()

        addSlide(
                SlideFragmentBuilder()
                        .backgroundColor(R.color.slide_1)
                        .buttonsColor( R.color.slide_button )
                        .title( resources.getString(R.string.slide_1_title) )
                        .description( resources.getString(R.string.slide_1_description) )
                        .image( R.drawable.main_sobral_low_bimestre_icones )
                        .build()
        )


        addSlide(
                SlideFragmentBuilder()
                        .backgroundColor(R.color.slide_2)
                        .buttonsColor( R.color.slide_button )
                        .title( resources.getString(R.string.slide_2_title) )
                        .description( resources.getString(R.string.slide_2_description) )
                        .image( R.drawable.main_sobral_linha_tempo )
                        .build()
        )


        addSlide(
                SlideFragmentBuilder()
                        .backgroundColor(R.color.slide_3)
                        .buttonsColor( R.color.slide_button )
                        .title( resources.getString(R.string.slide_3_title) )
                        .description( resources.getString(R.string.slide_3_description) )
                        .image( R.drawable.main_sobral_marcacoes_anotacoes)
                        .build()
        )

        addSlide(
                SlideFragmentBuilder()
                        .backgroundColor(R.color.slide_4)
                        .buttonsColor( R.color.slide_button )
                        .title( resources.getString(R.string.slide_4_1_title) )
                        .description( resources.getString(R.string.slide_4_1_description) )
                        .image( R.drawable.main_sobral_escolhas_datas)
                        .build()
        )


        addSlide(TermsConditionsSlide())


    }


    private fun verifyIntroActivity(){
        if( SPInfo(this).isIntroShown() ){
            startActivity(
                    Intent(this, Login_activity::class.java)
            )
            finish()
        }
    }
}