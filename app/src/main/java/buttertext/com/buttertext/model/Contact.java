package buttertext.com.buttertext.model;

/**
 * Created by Gabriel on 1/8/2016.
 */
public class Contact {

    int id;
    String name;
    String number;
    String email;

    // Constructors
    public Contact(){}

    public Contact(String name, String number, String email){
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public Contact(int id, String name, String number, String email){
        this.id = id;
        this.name = name;
        this.number = number;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
