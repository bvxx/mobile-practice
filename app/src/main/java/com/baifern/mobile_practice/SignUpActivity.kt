package com.baifern.mobile_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.ThreadLocalRandom

class SignUpActivity : AppCompatActivity() {

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if(id == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
