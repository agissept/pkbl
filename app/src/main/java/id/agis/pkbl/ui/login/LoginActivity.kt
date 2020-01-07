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
        viewModel = ViewModelFactory.getInstance().create(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            requestLogin(ed_username.text.toString(), ed_password.text.toString())
        }
        tv_forgot.setOnClickListener {
            //            startActivity<ForgotPasswordActivity>()
        }

    }

    private fun requestLogin(username: String, password: String) {
        tv_error.visibility = View.INVISIBLE
        progress_circular.visibility = View.VISIBLE

        viewModel.postLogin(username, password).observe(this, Observer {
            if (it.status) {
                viewModel.saveToken(applicationContext, it.id, it.username, it.email, it.role)
                startActivity<MainActivity>()
                finish()
            } else {
                tv_error.visibility = View.VISIBLE
                progress_circular.visibility = View.INVISIBLE
            }
        })
    }
}
