Restful - Hotel Booking API's

Auth - CreateToken (POST)
https://restful-booker.herokuapp.com/auth
Creates a new auth token to use for access to the PUT and DELETE /booking


curl -X POST \

 https://restful-booker.herokuapp.com/auth \

 -H 'Content-Type: application/json' \

 -d '{

   "username" : "admin",

   "password" : "password123"

}'


Booking - CreateBooking (POST)
Creates a new booking in the API
POST
https://restful-booker.herokuapp.com/booking


Payload:-
{

   "firstname" : "Jim",

   "lastname" : "Brown",

   "totalprice" : 111,

   "depositpaid" : true,

   "bookingdates" : {

       "checkin" : "2018-01-01",

       "checkout" : "2019-01-01"

   },

   "additionalneeds" : "Breakfast"

}



Booking - UpdateBooking (PUT)
Updates a current booking
https://restful-booker.herokuapp.com/booking/:id

Booking - GetBooking (GET)
Returns a specific booking based upon the booking id provided
https://restful-booker.herokuapp.com/booking/:id






Use case
Step 1:  Create a new Booking and validate its response code and Store the bookingid  (POST API)
Step 2:  Get the user details using bookingid and validate (GET API)
Step 3: With a valid Auth Token(POST), update any user's details for a new bookingid (PUT)
Step 4: Get the updated user details using bookingid and validate (GET API)

