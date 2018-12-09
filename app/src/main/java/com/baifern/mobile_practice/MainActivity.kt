package com.baifern.mobile_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private var mAuth: FirebaseAuth? = null
    lateinit var database: DatabaseReference
    lateinit var msgList: MutableList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "Main Page"

        // authen firebase
        mAuth = FirebaseAuth.getInstance()

        // read user data from database
        msgList = mutableListOf()
        database = FirebaseDatabase.getInstance().getReference("User")
        database.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
            }

            override fun onDataChange(p0: DataSnapshot?) {
                if (p0!!.exists()) {
                    msgList.clear()
                    for (i in p0.children) {
                        val message = i.getValue(User::class.java)
                        msgList.add(message!!)
                    }
                }
            }

        })

        // show all user on list view
        listView = findViewById<ListView>(R.id.ListView)
        val data = DataProvider.getData()
        var name: ArrayList<String> = arrayListOf()
        for (i in 0 until data.size) {
            name.add(data[i].email)
            Log.d("mrt",name.toString())
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, name)
            listView.adapter = adapter
        }

        // click on each list to move to their map
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val user = data.get(position)
            displayMap(user)
        }
    }

    private fun displayMap(user: User) {
        val intent = Intent(this,MapsActivity::class.java)
        intent.putExtra("Email", user.email)
        intent.putExtra("Lat", user.lat)
        Log.d("lat",user.lat.toString())
        intent.putExtra("Lng", user.lng)
        Log.d("lng",user.lng.toString())
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item!!.getItemId()
        if(id == R.id.my_activity) {
            // dialog message
            // Set a click listener for button widget
            // Initialize a new instance of
            val builder = AlertDialog.Builder(this@MainActivity)

            // Set the alert dialog title
            builder.setTitle("Confirm Exit")

            // Display a message on alert dialog
            builder.setMessage("Are you want to log out this application?")

            // Set a positive button and its click listener on alert dialog
            builder.setPositiveButton("YES"){ dialog, which ->
                // Do something when user press the positive button
                Toast.makeText(applicationContext,"Ok, you logged out.", Toast.LENGTH_SHORT).show()
                mAuth!!.signOut()
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }

            // Display a negative button on alert dialog
            builder.setNegativeButton("No"){ dialog,which ->
                Toast.makeText(applicationContext,"You still here.",Toast.LENGTH_SHORT).show()
            }

            // Display a neutral button on alert dialog
            builder.setNeutralButton("Cancel"){_,_ ->
                Toast.makeText(applicationContext,"You cancelled the dialog.",Toast.LENGTH_SHORT).show()
            }

            // Finally, make the alert dialog using builder
            val dialog: AlertDialog = builder.create()

            // Display the alert dialog on app interface
            dialog.show()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}