package com.baifern.mobile_practice

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class UserAdapter(val mContext: Context, val layoutResId: Int, val userList: List<User>) : ArrayAdapter<User>(mContext, layoutResId, userList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflator: LayoutInflater = LayoutInflater.from(mContext)
        val view: View = layoutInflator.inflate(layoutResId, null)

        val usernameTextView = view.findViewById<TextView>(R.id.usernameText)
        val users = userList[position]
        usernameTextView.text = users.username

        return view
    }
}