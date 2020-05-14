package com.bsuir.clinic.website.entity;

import com.bsuir.clinic.website.constants.JsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView({JsonViews.Public.class, JsonViews.AppointmentsInfo.class})
    private Integer id;

    @JsonView({JsonViews.Public.class, JsonViews.AppointmentsInfo.class})
    private String login;

    @JsonView(JsonViews.Public.class)
    private String password;

    @JsonView({JsonViews.Public.class, JsonViews.AppointmentsInfo.class})
    private String surname;

    @JsonView({JsonViews.Public.class, JsonViews.AppointmentsInfo.class})
    private String name;

    @JsonView({JsonViews.Public.class, JsonViews.AppointmentsInfo.class})
    private String phoneNumber;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @JsonView(JsonViews.Public.class)
    private Date birthDate;

    public User() {
    }

    public User(Integer id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return false;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getSurname(), getName(), getPhoneNumber(), getBirthDate());
    }
}
