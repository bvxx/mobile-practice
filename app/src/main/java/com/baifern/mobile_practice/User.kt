package com.baifern.mobile_practice

class User(val id: String, val email: String, val lat: Double, val lng: Double) {
    constructor() : this("","", 0.00, 0.00)
}
