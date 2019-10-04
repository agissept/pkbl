package id.agis.pkbl.ui.login

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.agis.pkbl.R
import id.agis.pkbl.ui.dashboard.DashboardActivity
import id.agis.pkbl.ui.forgotpasssword.ForgotPasswordActivity
import id.agis.pkbl.ui.home.MainActivity
import id.agis.pkbl.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

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

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)


        btn_login.setOnClickListener {
            requestLogin(ed_username.text.toString(), ed_password.text.toString())
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

    private fun requestLogin(username: String, password: String) {
        tv_error.visibility = View.INVISIBLE
        progress_circular.visibility = View.VISIBLE

        viewModel.requestLogin(username, password).observe(this, Observer {
            if (it != null) {
                viewModel.saveToken(applicationContext, it.userId, it.access.id, it.token)
                if (it.access.id == 1) {
                    startActivity<DashboardActivity>()
                } else if (it.access.id == 2) {
                    startActivity<MainActivity>()
                }
                finish()
            }
            if (it == null) {
                tv_error.visibility = View.VISIBLE
                progress_circular.visibility = View.INVISIBLE
            }
        })
    }
}
