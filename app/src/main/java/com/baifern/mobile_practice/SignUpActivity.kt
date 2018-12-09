package com.baifern.mobile_practice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.ThreadLocalRandom

class SignUpActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().getReference("User-CheckManual")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "Sign Up"

        RandomLatLngButton.setOnClickListener {
            val latMin = -85.000000
            val latMax = 85.000000
            val lngMin = -179.999989
            val lngMax = 179.999989

            val latRandom = ThreadLocalRandom.current().nextDouble(latMin, latMax)
            val lngRandom = ThreadLocalRandom.current().nextDouble(lngMin, lngMax)

            LatitudeText.setText(latRandom.toString())
            LongitudeText.setText(lngRandom.toString())
        }

        AddNewUserButton.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val input_username = UsernameTextView.text.toString()
        if (input_username.isEmpty()) {
            UsernameTextView.error = "Please enter username"
            return
        }

        val input_password = PasswordTextView.text.toString()
        if (input_password.isEmpty()) {
            PasswordTextView.error = "Please enter password"
            return
        }

        val input_lat = LatitudeText.text.toString().toDouble()
        if (input_lat == null) {
            LatitudeText.error = "Please enter password"
            return
        }

        val input_long = LongitudeText.text.toString().toDouble()
        if (input_long == null) {
            LongitudeText.error = "Please enter password"
            return
        }

        val userId = database.push().key
        val userData = User(userId, input_username, input_password, input_lat, input_long)

        database.child(userId).setValue(userData)
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if (id == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
