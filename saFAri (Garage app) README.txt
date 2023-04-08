//saFAri (Garage app)//

 
//Login_Register_Activities_Start

So at first i have created a login_page (MainActivity.java and activity_main layout) which lets user login through their authenticated EmailId and password by entering their email and password in EditTextLayout fields and then clicking on Login button, if everything is correct then user will get redirected to (activity_dashboard and dashboard.class) activity.

I have used EditTextLayout instead of  normal(EditText) because they look good and have more feautres then traditional EditText and also EditTextLayout provides a password view/toggle functionality.

//I have used Firefox to Authenticate the User using email/password Auth.

If user is not registered then he have to click on Register button which is given below.

After click on Register button a new Activity(activity_user_register_page) will open up then user has to fill all the details that i asked,then after filling all these details user has to click on Register me button which is situated given below!

After all these previous steps user will get registered and then he will be redirected to Dashboard(activity_dashboard and dashboard.class) activity.


//Login_Register_Activities_end



//MAIN WORK FROM HERE



//Dashboard_layout_start


first a simple Text_view is showed which is "Dashboard" besides it a button is also provided by clicking on that button user can logout and he/she will be redirected to then 
login page.

Then comes a horizontal rule break(to divide then dashboard and later part)

Then a card view(I have used card view to give a emphasis on my data and also it looks good with padding and all.) which contains 4 elements:

A textView which displays "Add a new Car"

Then a spinner named as Spinner1 (Note: i have also used an prebuilt style to make it look good,and also by adding that style a line will appear on bottom border of the spinner.

This spinner 1 will contains all car_makes names (After calling the 1 Api it will get populate automatically ,Also just when you enter then dashboard_page the Api 1 is called).

then again a spinner named as Spinner2(also using an prebuilt android style).It contains all the car_models name which is built by their respective car_makes.

When users first time enters on dashboard_page api2 gets automatically called by taking the parameter of car_makes names id.....

BUT when user selects an item from spinner1 then the api 2 is called and the result will populate then spinner2.

Then below all these three A "ADD CAR" button is given:
             
              When user clickes on it:(Check in recyler_View portion)


//Below_part_layout_start:

contains a Scroll view (Vertical) and in that scroll view a linear layout(orientation: vertical) is placed and also in that linear view i have added card view, a Recyler view is added.  

//Recyler_view start:


Recyler view activity contains a card view, and on that card view 5 items are there (2 TextViews, first is make_name and second is model_name , 3rd item is an imageView
4th and 5th items are buttons which displays "Add Car Image" and "Delete car" respectively)

Now:

When user clicks on "ADD CAR" button:


                         A Recyler view is started(I have used Recycler view instead of list view BECAUSE RECYLER VIEW SAVES A LOT OF MEMORY BY SIMPLING BINDING THE NEWDATA WITH THE SAME VIEW AND THEN APPENDING THAT NEW VIEW BELLOW IT, it basically recyles then view put new data on it and then set in our Array) this will basically add up a card which with all those 5 items but then make_name and model_name is changed to that respective Strings that you have clicked on spinner1 and spinner2.


When you click on "ADD CAR IMAGE BUTTON" a prompt will appear asking user to either select camera or gallery , now user can either select a pic from galley or he can directly click it (For this im using IMAGEpicker LIBARAY) after selecting/capturing the image then image will get displayed on recylerview's cardview(3rd item).

When you click on "Delete Car" button then then card view will be gone will all of the data related to it.

//Recyler_view end


//Below_par_layout end


//Dashboard_layout_end



//Database_start:

I have used SQLITE database for this project because it is faster,better then mysql or nosql according to me and it is also much easier than to implement it.

Basically when user first time opens then application i do nothing no data storing but when user clicks on "Add car" then i initialize my database and store the data into database.then when user opens then app again now this time my database fetches the data and put in on recyler view, same functionalities goes for deleting the car.

And in "Add Car image" section i just used a query of update  and change then old uri(image) to new to show them their image.

//Database_end


//External libraries/dependecy i have used:

1) Retrofit2 for api fetching.

2) ImagePicker library for capturing and getting the image.

3)Firebase Auth dependency

 
