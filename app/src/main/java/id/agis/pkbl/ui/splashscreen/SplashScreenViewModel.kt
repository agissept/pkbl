package id.agis.pkbl.ui.splashscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import id.agis.pkbl.constant.Constant.Companion.LOGIN_STATUS
import id.agis.pkbl.constant.Constant.Companion.USER_ID
import id.agis.pkbl.constant.Constant.Companion.USER_ROLE

class SplashScreenViewModel: ViewModel() {
    fun getToken(context: Context): String?{
        val sharedPreferences = context.getSharedPreferences(LOGIN_STATUS, Context.MODE_PRIVATE)

        return sharedPreferences.getString(USER_ID, null)
    }

    fun getRole(context: Context): Int{
        val sharedPreferences = context.getSharedPreferences(LOGIN_STATUS, Context.MODE_PRIVATE)

        return sharedPreferences.getInt(USER_ROLE, 0)
    }
}