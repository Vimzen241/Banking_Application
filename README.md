# Banking_Application

#Instructions to run the application
In eclipse/spring tool suite run the application using run as spring boot app.

#Rest points
http://localhost:8080/create
json: {
	"firstname":"arjun",
	"lastname":"dass",
	"username":"arjun",
	"password":"1234"
}

http://loalhost:8080/refund
json: {
	"refund":"500",
	"id":1
}

http://localhost:8080/charge/
json: {
	"charge":"20000", "id":1
}

http://localhost:8080/balance/1


