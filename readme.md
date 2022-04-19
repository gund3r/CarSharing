# CarSharing #
***
This project was developed with "JetBrains Academy" https://hyperskill.org/.

Car-sharing is becoming a more and more popular green alternative to owning a car. 
Here is a console-program that manages a car-sharing service allowing companies to rent 
out their cars and find customers.

In this project I used knowledge about the basics of SQL and H2 database. 
Also, some features of Java Collections framework.

### Running the application ###
To run program go to the root directory of project and type in your console:  
`make #build & run`

When you will build and run the program - will see main menu:
```
1. Log in as a manager
2. Log in as a customer
3. Create a customer
0. Exit
```
In case _Log in as a manager_ you will see _Manager menu_:
```
1. Company list
2. Create a company
0. Back
```
At first position placed list of registered companies. You can create a new company 
by select second position of menu if in the database doesn't exist your or any other company.

In case _Log in as a customer_ you will see list of registered customers.
If in the database doesn't exist any customers - you can create your user at the third position of _Main menu_:  
`3. Create a customer`  

After that you can choose your customer and continue work with the program. Next step will be a _Customer Menu_:
```
1. Rent a car
2. Return a rented car
3. My rented car
0. Back
```
Position's names speak for themselves: 
* At the first position - select carsharing company and rent one
from list of available cars.
* At the second position you can return a rented car.
* At the third position you will see a rented car.

To add new cars to companies - select _Log in as a manager_ then go to _1. Company list_ 
and select one of existing companies. Then you will see _Company menu_ like this:  
```
'Here is may be name of your company' company
1. Car list
2. Create a car
0. Back
```
And select the second position. That's all.

To stop program select `0. Back` at the _Main menu_. 
Or you can use `Ctrl + C` at any place in the program.

