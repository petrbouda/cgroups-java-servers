package pbouda.cgroups.mvc;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String city;
    private String country;
    private String phone;
    private String politicalOpinion;

    public Person() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPoliticalOpinion() {
        return politicalOpinion;
    }

    public void setPoliticalOpinion(String politicalOpinion) {
        this.politicalOpinion = politicalOpinion;
    }

    @Override
    public String toString() {
        return "Person{" +
               "id=" + id +
               ", firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               ", city='" + city + '\'' +
               ", country='" + country + '\'' +
               ", phone='" + phone + '\'' +
               ", politicalOpinion='" + politicalOpinion + '\'' +
               '}';
    }
}