package id.agis.pkbl.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.constant.Constant.Companion.LOGIN_STATUS
import id.agis.pkbl.constant.Constant.Companion.USER_ID
import id.agis.pkbl.constant.Constant.Companion.USER_NAME
import id.agis.pkbl.constant.Constant.Companion.USER_ROLE
import id.agis.pkbl.constant.Constant.Companion.USER_TOKEN
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.model.User

class LoginViewModel(private val pkblRepository: PKBLRepository) : ViewModel() {

    fun requestLogin(email: String, pass: String): LiveData<User> {
        return pkblRepository.loginRequest(email, pass)
    }

    fun saveToken(context: Context, id: Int, role: Int, token: String, username: String) {
        val sharedPreferences = context.getSharedPreferences(LOGIN_STATUS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(USER_ID, id)
        editor.putInt(USER_ROLE, role)
        editor.putString(USER_NAME, username)
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }


}