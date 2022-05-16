package com.example.app_location_voiture;

public class User {
String UserName,AgenceName,tel,adresse,passwrd,email,id,profilePic;

    public User(String userName, String agenceName, String tel, String adresse, String passwrd, String email) {
        UserName = userName;
        AgenceName = agenceName;
        this.tel = tel;
        this.adresse = adresse;
        this.passwrd = passwrd;
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {

    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAgenceName() {
        return AgenceName;
    }

    public void setAgenceName(String agenceName) {
        AgenceName = agenceName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
