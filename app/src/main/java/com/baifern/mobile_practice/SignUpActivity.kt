package com.baifern.mobile_practice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.concurrent.ThreadLocalRandom

class SignUpActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "Sign Up"

        //authen firebase
        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference("User")

        AddNewUserButton.setOnClickListener {
            val email = UsernameTextView.text.toString().trim{it <= ' '}
            val password = PasswordTextView.text.toString().trim { it <= ' ' }

            if(email.isEmpty()){
                return@setOnClickListener
            }
            if(password.isEmpty()){
                return@setOnClickListener
            }

            mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    if(password.length<6){

                    }
                    else{

                    }
                }
                else{
                    saveData()
                    startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    finish()
                }
            }
        }

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

    private fun saveData(){
        val email = UsernameTextView.text.toString()
        val password = PasswordTextView.text.toString()
        val lat = LatitudeText.text.toString()
        val lng = LongitudeText.text.toString()

        if (email.isEmpty()){
            UsernameTextView.error = "Please enter a message"
            return
        }
        else if (password.isEmpty()){
            PasswordTextView.error = "Please enter a message"
            return
        }

        val messageId = database.push().key
        val messageData = User(messageId, email, lat.toDouble(), lng.toDouble())
        database.child(messageId).setValue(messageData)
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
