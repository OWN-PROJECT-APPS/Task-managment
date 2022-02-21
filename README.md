## Task Management
 
 ** Task management it is just a small test app  include two screen one to view all the users and the second one to view the tasks of each user. 
 
 ** In this app i used recycler view to improve the using of the system memory
 
 ** For The Http request i Prefered  to use  Volly there is another Library to do that as Retrofit
 
 ** Also i prefer to use cardview plugin to disply the content as a card
 
 ** To useing the app on offline mode i integrated SQLLite to store the users and the tasks and when you try to surfing the app whitout network accessing in that moment i will used the stored data from local database
 
 ### Architecture of the app 
 
 ** Models.   : Is a directory hold two classes one for user and other for task
 ** Adapter   : This is a folder contain the adpters that whe used with the recycler view
 ** Services  : In that folder we put the services files as the database connection with SQLLite
 
 **  Last thing we have two activities to handle the UI and all the behavior of the client that surf the app
 
 
