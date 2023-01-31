/* ***************************************************************
 **                                                              **
 ** Class Name:      Contact                                     **
 ** Purpose:         Creates a Contact Object                    **
 ** Method(s):       {constructer, get, toStr, toJson            **
 ** Property(ies):   {Name,Number,Street,City,State,Zip,Email    **
 **                        and Birthday}                         **
 **Author:           James Robinson                              **
 **Written:          1/26/2023                                   **
 **                                                              **
 *****************************************************************/
import org.json.JSONObject;

public class Contact {
    private String name; // Required for assignment
    private String number; // Required for assignment
    // Additional fields not required for assignment
    private String email;
    private String birthday;
    private String street;
    private String city;
    private String state;
    private String zip;

    public Contact (String name, String number){
        this.name = name;
        this.number = number;
    }

    public Contact (String name, String number, String email){
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public Contact (String name, String number, String email, String birthday){
        this.name = name;
        this.number = number;
        this.email = email;
        this.birthday = birthday;
    }

    public Contact(String name, String number, String email, String birthday, String street, String city, String state, String zip){
        this.name = name;
        this.number = number;
        this.email = email;
        this.birthday = birthday;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public Contact(){
        this.name = "";
        this.number = "";
        this.email = "";
        this.birthday = "";
        this.street = "";
        this.city = "";
        this.state = "";
        this.zip = "";
    }
    public Contact(JSONObject jsonObject){
        this.name = jsonObject.getString("Name");
        this.number = jsonObject.getString("Number");
        this.email = jsonObject.getString("Email");
        this.birthday = jsonObject.getString("Birthday");
        this.street = jsonObject.getString("Street");
        this.city = jsonObject.getString("City");
        this.state = jsonObject.getString("State");
        this.zip = jsonObject.getString("Zip");

    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }


    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zip='" + zip + '\'' +
                '}';
    }

    public String toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("number", number);
        json.put("email", email);
        json.put("birthday", birthday);
        json.put("street", street);
        json.put("city", city);
        json.put("state", state);
        json.put("zip", zip);
        return json.toString();
    }
}

