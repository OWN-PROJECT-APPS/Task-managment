package com.example.task_managment.models

 class User(var _userId:Int, var _name: String, var _username: String, var _email: String) {
    // Member Variables
    private val userId : Int = _userId
    private val name: String = _name
    private var username: String = _username
    private var email:String = _email

    fun getUserId():Int {
            return this.userId
    }

   fun getName():String{
        return this.name
    }
    fun getUsername():String{
        return this.username
    }
    fun getEmail():String{
        return this.email
    }

}