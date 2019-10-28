package id.agis.pkbl.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import id.agis.pkbl.R
import id.agis.pkbl.main.MainActivity
import id.agis.pkbl.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        supportActionBar?.hide()
        viewModel = ViewModelFactory.getInstance(this).create(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            startActivity<MainActivity>()

//            requestLogin(ed_username.text.toString(), ed_password.text.toString())
        }
        tv_forgot.setOnClickListener {
//            startActivity<ForgotPasswordActivity>()
        }

    }

    private fun requestLogin(username: String, password: String) {
        tv_error.visibility = View.INVISIBLE
        progress_circular.visibility = View.VISIBLE

        viewModel.requestLogin(username, password).observe(this, Observer {
            if (it != null) {
                viewModel.saveToken(applicationContext, it.userId, it.access.id, it.token, it.username)
                if (it.access.id == 1) {
//                    startActivity<DashboardActivity>()
                } else if (it.access.id == 2) {
//                    startActivity<UnusedActivity>()
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
