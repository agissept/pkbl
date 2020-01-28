package id.agis.pkbl.ui.splashscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import id.agis.pkbl.constant.Constant

class SplashScreenViewModel: ViewModel() {
    fun getUsername(context: Context): String?{
        val sharedPreferences = context.getSharedPreferences(Constant.USER, Context.MODE_PRIVATE)

        return sharedPreferences.getString(Constant.USER_USERNAME, null)
    }

}