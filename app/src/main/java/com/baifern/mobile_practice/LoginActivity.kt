package com.baifern.mobile_practice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var userList: MutableList<User>
    private var input_username: String = ""
    private var input_password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        title = "EGCO428"

        // database
        userList = mutableListOf()
        val database = FirebaseDatabase.getInstance().getReference("User-CheckManual")

        SignInButton.setOnClickListener {

            // read database
            database.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    if (p0!!.exists()) {
                        userList.clear()
                        for (i in p0.children) {
                            val message = i.getValue(User::class.java)
                            userList.add(message!!)
                        }
                    }
                }
            })

            input_username = UsernameTextView.text.toString()
            input_password = PasswordTextView.text.toString()

            if(input_username.isEmpty()){
                Toast.makeText(this,"Please type a username",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (input_password.isEmpty()){
                Toast.makeText(this,"Please type a password",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            for(i in 0 until userList.size){
                if(userList[i].username.equals(input_username) && userList[i].password.equals(input_password)){
                    Toast.makeText(this,"Login success",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Login fail",Toast.LENGTH_SHORT).show()
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
