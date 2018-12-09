package com.baifern.mobile_practice

import android.app.Activity
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.view.*
import android.widget.BaseAdapter
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.row_main.view.*

class MainActivity : AppCompatActivity() {

    lateinit var userList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Main Page"

        // database
        userList = arrayListOf()
        val database = FirebaseDatabase.getInstance().getReference("User-CheckManual")

        // read from database
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

                    val userArrayAdapter = UserArrayAdapter(this@MainActivity, 0, userList!!)
                    ListView.setAdapter(userArrayAdapter)
                    ListView.setOnItemClickListener { parent, view, position, id ->
                        val user = userList[position]
                        displayDetail(user)
                    }
                }
            }
        })
    }

    private fun displayDetail(users: User){
        val intent = Intent(this,MapsActivity::class.java)
        intent.putExtra("username",users.username)
        intent.putExtra("latitude",users.latitude)
        intent.putExtra("longitude",users.longitude)
        startActivity(intent)
    }

    private class UserArrayAdapter(var context: Context, resource: Int, var objects: ArrayList<User>): BaseAdapter(){
        override fun getCount(): Int {
            return objects.size
        }

        override fun getItem(position: Int): Any {
            return objects[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val users = objects[position]
            val inflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = inflater.inflate(R.layout.row_main,null)
            view.usernameText.text = users.username

            return view
        }
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
