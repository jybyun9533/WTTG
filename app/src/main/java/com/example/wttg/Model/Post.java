package com.example.wttg.Model;

public class Post {
    private String postid;
    private String user;
    private String userid;
    private String facility;
    private String facid;
    private String admission;
    private String userContact;
    private String facContact;
    private String birth;


    public Post(String postid, String user, String facility, String admission, String userContact, String facContact, String birth, String userid, String facid) {
        this.postid = postid;
        this.user = user;
        this.userid = userid;
        this.facility = facility;
        this.facid = facid;
        this.admission = admission;
        this.userContact = userContact;
        this.facContact = facContact;
        this.birth = birth;

    }

    public Post() {
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFacid() {
        return facid;
    }

    public void setFacid(String facid) {
        this.facid = facid;
    }

    public String getListid() {
        return postid;
    }

    public void setListid(String listid) {
        this.postid = listid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getAdmission() {
        return admission;
    }

    public void setAdmission(String admission) {
        this.admission = admission;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getFacContact() {
        return facContact;
    }

    public void setFacContact(String facContact) {
        this.facContact = facContact;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }


}
