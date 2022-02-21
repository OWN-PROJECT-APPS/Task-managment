package com.example.task_managment.models

 class Task(var _taskId: Int ,var _userId: Int,var _title:String,var _isCompleted: Boolean) {

    private val taskId:Int = _taskId
    private val userId:Int = _userId
    private val title:String = _title
    private val isCompleted:Boolean = _isCompleted

    fun getTaskId():Int {

        return taskId
    }
     fun getUserId():Int {

         return userId
     }
    fun getTaskName():String {

        return this.title
    }
     fun getTaskAsInt():Int{
         return if(this.isCompleted){
           1
         }else{
          0
         }

     }
    fun getIsCompleted():String {

        return if(this.isCompleted){
            "Completed"
        }else{
            "Incompleted"
        }
    }

}