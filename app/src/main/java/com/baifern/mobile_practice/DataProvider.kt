package com.baifern.mobile_practice

import java.util.*

object DataProvider {
    private val data = ArrayList<User>()

    fun getData(): ArrayList<User> {
        return data
    }

    fun saveData(user_id: String, user_email: String, user_lat: Double, user_lng: Double){
        data.add(User(user_id, user_email, user_lat, user_lng))
    }

    init {
    }
}