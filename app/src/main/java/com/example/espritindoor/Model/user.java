package com.example.espritindoor.Model;

import java.util.ArrayList;

public class user {

    private String  _id ;
    private String userName ;
    private String email ;
    private String password ;
    private String firstName ;
    private String lastName ;
    private String idaUuid ;
    private String Token ;
    private int Image ;
    private Boolean etat ;
    private int Phone ;
    private String imageProfil ;
    private String  background;
     private int Nbr ;
    private ArrayList<String> Demande ;
    private ArrayList<String> events ;
    private ArrayList<String> friend ;
    private String born ;
    private String Lives ;
    private String From ;

    /*

    public ArrayList<String> getDemande() {
        return Demande;
    }

    public void setDemande(ArrayList<String> demande) {
        Demande = demande;
    }

    public int getNbr() {
        return Nbr;
    }

    public void setNbr(int nbr) {
        Nbr = nbr;
    }

    public String getImageProfil() {
        return imageProfil;
    }

    public void setImageProfil(String imageProfil) {
        this.imageProfil = imageProfil;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    private String imageProfil;
    private String background ;


    public void setImage(String[] image) {
        this.image = image;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        this.Image = image;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public user(String _id) {
        this._id = _id;
    }

    public user(String _id, String username, String email, String password, String name, String nom, String prenom, String token, int image, Boolean etat, int phone, String[] image1, int nbr, String imageProfil, String background) {
        this._id = _id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nom = nom;
        this.prenom = prenom;
        Token = token;
        Image = image;
        this.etat = etat;
        this.phone = phone;
        this.image = image1;
        Nbr = nbr;
        this.imageProfil = imageProfil;
        this.background = background;
    }

    public user(String username, String email, String password, String name, String nom, String prenom, int image, String token, Boolean etat, int phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nom = nom;
        this.prenom = prenom;
        this.Image = image;
        Token = token;
        this.etat = etat;
        this.phone = phone;
    }

    public user(String email, String password, String nom) {
        this.email = email;
        this.password = password;
        this.username = nom;
    }
    public user(String username,int image ) {

        this.username = username;
        this.Image = image ;
    }

    public user() {

    }
    */

    public user(String userName, int image) {
        this.userName = userName;
        Image = image;
    }

    public user(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public user(String _id) {
        this._id = _id;
    }

    public user(String _id, String userName, String email, String password, String firstName, String lastName, String idaUuid, String token, int image, Boolean etat, int phone, String imageProfil, String background, int nbr, ArrayList<String> demande, ArrayList<String> events, ArrayList<String> friend) {
        this._id = _id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idaUuid = idaUuid;
        Token = token;
        Image = image;
        this.etat = etat;
        Phone = phone;
        this.imageProfil = imageProfil;
        this.background = background;
        Nbr = nbr;
        Demande = demande;
        this.events = events;
        this.friend = friend;

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdaUuid() {
        return idaUuid;
    }

    public void setIdaUuid(String idaUuid) {
        this.idaUuid = idaUuid;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getImageProfil() {
        return imageProfil;
    }

    public void setImageProfil(String imageProfil) {
        this.imageProfil = imageProfil;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getNbr() {
        return Nbr;
    }

    public void setNbr(int nbr) {
        Nbr = nbr;
    }

    public ArrayList<String> getDemande() {
        return Demande;
    }

    public void setDemande(ArrayList<String> demande) {
        Demande = demande;
    }

    public ArrayList<String> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<String> events) {
        this.events = events;
    }

    public ArrayList<String> getFriend() {
        return friend;
    }

    public void setFriend(ArrayList<String> friend) {
        this.friend = friend;
    }


    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getLives() {
        return Lives;
    }

    public void setLives(String lives) {
        Lives = lives;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public user(String _id, String userName, String email, String password, String firstName, String lastName, String idaUuid, String token, int image, Boolean etat, int phone, String imageProfil, String background, int nbr, ArrayList<String> demande, ArrayList<String> events, ArrayList<String> friend, String born, String lives, String from) {
        this._id = _id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.idaUuid = idaUuid;
        Token = token;
        Image = image;
        this.etat = etat;
        Phone = phone;
        this.imageProfil = imageProfil;
        this.background = background;
        Nbr = nbr;
        Demande = demande;
        this.events = events;
        this.friend = friend;
        this.born = born;
        Lives = lives;
        From = from;
    }
}
