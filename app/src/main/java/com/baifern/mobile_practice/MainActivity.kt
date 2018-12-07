package com.baifern.mobile_practice

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.content.Intent

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        title = "Main Page"

        // show all user on list view
        listView = findViewById<ListView>(R.id.ListView)
        val usernameList = arrayOf("baifern","taetae")
        //val listItems = arrayOfNulls<String>(usernameList.size)
        //for (i in 0 until usernameList.size) {
        //val recipe = usernameList[i]
        //listItems[i] = recipe.title
        //}
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, usernameList)
        listView.adapter = adapter

        // click on each list to move to their map
        listView.setOnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(this,MapsActivity::class.java)
            intent.putExtra("username", usernameList[position])
            startActivity(intent)
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
