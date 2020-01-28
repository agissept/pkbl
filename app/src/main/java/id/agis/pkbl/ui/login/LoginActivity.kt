package id.agis.pkbl.ui.login

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.agis.pkbl.R
import id.agis.pkbl.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        btn_login.setOnClickListener {
            requestLogin(ed_username.text.toString(), ed_password.text.toString())
        }

    }

    private fun requestLogin(username: String, password: String) {
        progress_circular.visibility = View.VISIBLE

        viewModel.postLogin(username, password).observe(this, Observer {
            if (it.status) {
                viewModel.saveToken(applicationContext, it)
                startActivity<MainActivity>()
                finish()
            } else {
                progress_circular.visibility = View.INVISIBLE
                toast("Username atau password salah")
            }
        })
    }
}
