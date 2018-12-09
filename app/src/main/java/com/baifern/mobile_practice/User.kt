package com.baifern.mobile_practice

class User (val id: String, val username: String,val password: String, val latitude: Double, val longitude: Double)
{
    constructor(): this("","","",0.00,0.00)
}