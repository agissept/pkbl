package id.agis.pkbl.ui.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.DashboardActivity
import id.agis.pkbl.ui.forgotpasssword.ForgotPasswordActivity
import id.agis.pkbl.ui.home.MainActivity
import id.agis.pkbl.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = Color.TRANSPARENT
        }

        btn_login.setOnClickListener {
            startActivity<DashboardActivity>()
        }

        btn_user.setOnClickListener {
            startActivity<MainActivity>()
        }

        tv_forgot.setOnClickListener {
            startActivity<ForgotPasswordActivity>()
        }

        tv_sign_up.setOnClickListener {
            startActivity<SignUpActivity>()
        }
    }
}
