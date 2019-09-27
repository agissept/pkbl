package id.agis.pkbl.ui.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import id.agis.pkbl.constant.Constant.Companion.LOGIN_STATUS
import id.agis.pkbl.constant.Constant.Companion.USER_ID
import id.agis.pkbl.constant.Constant.Companion.USER_ROLE

class DashboardViewModel: ViewModel() {

    fun deleteToken(context: Context){
        val sharedPreferences = context.getSharedPreferences(LOGIN_STATUS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(USER_ID, null)
        editor.putString(USER_ROLE, null)
        editor.apply()
    }
}