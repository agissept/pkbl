package id.agis.pkbl.ui.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.agis.pkbl.constant.Constant
import id.agis.pkbl.data.PKBLRepository
import id.agis.pkbl.data.remote.RemoteRepository
import id.agis.pkbl.data.remote.retrofit.ApiClient
import id.agis.pkbl.data.remote.retrofit.ApiInterface
import id.agis.pkbl.model.User

class LoginViewModel(private val pkblRepository: PKBLRepository) : ViewModel() {
    private val apiInterface: ApiInterface = ApiClient.retrofit().create(ApiInterface::class.java)

    private var _user = MutableLiveData<User>()
    val user get() = _user

    fun postLogin(username: String, password: String): LiveData<User> {
        return RemoteRepository(apiInterface).postLogin(username, password)
    }

    fun saveToken(context: Context, id: Int, username: String, email: String, role: String) {
        val sharedPreferences = context.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(Constant.USER_ID, id)
        editor.putString(Constant.USER_NAME, username)
        editor.putString(Constant.USER_EMAIL, email)
        editor.putString(Constant.USER_ROLE, role)
        editor.apply()
    }

}