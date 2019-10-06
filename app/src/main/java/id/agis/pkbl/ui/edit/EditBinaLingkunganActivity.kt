package id.agis.pkbl.ui.edit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.agis.pkbl.R

class EditBinaLingkunganActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_bina_lingkungan)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
