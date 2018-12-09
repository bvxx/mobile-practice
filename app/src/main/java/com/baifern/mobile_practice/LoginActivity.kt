package com.baifern.mobile_practice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    lateinit var database: DatabaseReference
    lateinit var msgList: MutableList<User>

    private var email:String = ""
    private var password:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "EGCO428"

        // authen firebase
        mAuth = FirebaseAuth.getInstance()

        // read user data from database
        msgList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()){
                    msgList.clear()
                    for(i in p0.children){
                        val message = i.getValue(User::class.java)
                        msgList.add(message!!)
                    }
                }
            }

        })

        SignInButton.setOnClickListener {
            email = UsernameTextView.text.toString().trim { it <= ' ' }
            password = PasswordTextView.text.toString().trim { it <= ' ' }

            if (email.isEmpty()) {
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                return@setOnClickListener
            }

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Toast.makeText(this,"Log in fail", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in 0 until msgList.size) {
                        DataProvider.saveData(msgList[i].id,msgList[i].email,msgList[i].lat,msgList[i].lng)
                        Log.d("bts",msgList[i].email)
                    }
                    Toast.makeText(this,"Log in success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            }
        }

        CancelButton.setOnClickListener {
            onRestart()
        }

        SignUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onRestart() {
        super.onRestart()
        UsernameTextView.text.clear()
        PasswordTextView.text.clear()
    }
}
