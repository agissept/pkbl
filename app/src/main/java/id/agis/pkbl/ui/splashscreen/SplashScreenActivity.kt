package id.agis.pkbl.ui.splashscreen

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import id.agis.pkbl.ui.dashboard.DashboardActivity
import id.agis.pkbl.ui.home.HomeActivity
import id.agis.pkbl.ui.login.LoginActivity
import org.jetbrains.anko.startActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var viewModel : SplashScreenViewModel

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }

        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel::class.java)
        if (viewModel.getToken(this) != null) {
            when(viewModel.getRole(this)){
                1->{
                    startActivity<DashboardActivity>()
                }
                2->{
                    startActivity<HomeActivity>()
                }
            }


        } else {
            startActivity<LoginActivity>()
        }
        finish()

    }

}
