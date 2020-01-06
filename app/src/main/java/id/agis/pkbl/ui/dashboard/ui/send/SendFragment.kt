package id.agis.pkbl.ui.dashboard.ui.send

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import id.agis.pkbl.R
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.ui.login.LoginActivity
import org.jetbrains.anko.support.v4.startActivity

class SendFragment : Fragment() {

    private lateinit var sendViewModel: SendViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        context!!.getSharedPreferences(Constant.USER,  Context.MODE_PRIVATE).edit().clear().apply()
        startActivity<LoginActivity>()
        activity?.finish()

        val root = inflater.inflate(R.layout.fragment_send, container, false)
        return root
    }
}