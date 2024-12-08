package utb.fai.RESTAPIServer;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // název tabulky, lze změnit dle potřeby
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public MyUser() {}

    public MyUser(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public boolean isUserDataValid() {
        if (email == null || !email.contains("@")) {
            return false;
        }
        if (phoneNumber == null || !phoneNumber.matches("\\+?\\d+")) {
            return false;
        }
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return true;
    }    

    // Getters and setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
