package pbouda.cgroups.flux;

public class Person {

    private final long id;
    private final String firstname;
    private final String lastname;
    private final String city;
    private final String country;
    private final String phone;
    private final String politicalOpinion;

    public Person(
            long id,
            String firstname,
            String lastname,
            String city,
            String country,
            String phone,
            String politicalOpinion) {

        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.politicalOpinion = politicalOpinion;
    }

    public long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getPoliticalOpinion() {
        return politicalOpinion;
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
