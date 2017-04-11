package com.teamtreehouse.contactmgr.model;

import javax.persistence.*;

@Entity
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mId;

    @Column
    private String mFirstName;

    @Column
    private String mLastName;

    @Column
    private String mEmail;

    @Column
    private Long mPhone;

    public Contact() {
    }

    public Contact(ContactBuilder contactBuilder) {

        mFirstName = contactBuilder.mFirstName;
        mLastName = contactBuilder.mLastName;
        mEmail = contactBuilder.mEmail;
        mPhone = contactBuilder.mPhone;

    }

    @Override
    public String toString() {
        return "Contact{" +
                "mId=" + mId +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPhone=" + mPhone +
                '}';
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Long getPhone() {
        return mPhone;
    }

    public void setPhone(Long phone) {
        mPhone = phone;
    }

    public static class ContactBuilder  {

        private String mFirstName;
        private String mLastName;
        private String mEmail;
        private Long mPhone;

        public ContactBuilder(String firstName, String lastName) {
            mFirstName = firstName;
            mLastName = lastName;
        }

        public ContactBuilder withEmail(String email){
            mEmail = email;
            return this;
        }

        public ContactBuilder withPhone(Long phone){
            mPhone = phone;
            return this;
        }

        public Contact build(){


            return new Contact(this);
        }


    }

}
