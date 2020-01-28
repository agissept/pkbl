package id.agis.pkbl.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.data.RemoteRepository
import id.agis.pkbl.model.User

class LoginViewModel : ViewModel() {
    private var _user = MutableLiveData<User>()
    val user get() = _user

    fun postLogin(username: String, password: String): LiveData<User> {
        return RemoteRepository().postLogin(username, password)
    }

    fun saveToken(
        context: Context,
        user: User
    ) {
        val sharedPreferences = context.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(Constant.USER_ID, user.id)
        editor.putString(Constant.USER_NAME, user.nama)
        editor.putString(Constant.USER_USERNAME, user.username)
        editor.putString(Constant.USER_EMAIL, user.email)
        editor.putString(Constant.USER_ROLE, user.role)
        editor.putString(Constant.USER_IMAGE, user.image)
        editor.apply()
    }

}