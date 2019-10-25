package id.agis.pkbl.ui.splashscreen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import id.agis.pkbl.ui.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel : SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        if (viewModel.getToken(this) != null) {
            when(viewModel.getRole(this)){
                1->{
//                    startActivity<DashboardActivity>()
                }
                2->{
//                    startActivity<UnusedActivity>()
                }
            }


        } else {
            startActivity<LoginActivity>()
        }
        finish()

    }

}
