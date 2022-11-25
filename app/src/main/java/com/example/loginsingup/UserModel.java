package com.example.loginsingup;

public class UserModel {
    String Location;
    String UserName;
    String PhoneNumber;
    String Email;

    public UserModel(String location, String userName, String phoneNumber, String email) {
        Location = location;
        UserName = userName;
        PhoneNumber = phoneNumber;
        Email = email;
    }

    public String getLocation() {
        return Location;
    }
    public String getUserName(){
        return  UserName;
    }
    public String getPhoneNumber(){
        return PhoneNumber;
    }
    public String getEmail(){
        return Email;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
}


