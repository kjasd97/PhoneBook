package ukr.roma.spring.TestApp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "Phone_info")
public class PhoneInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone_number")
    private int phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="person_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Person owner;

    public PhoneInfo(){

    }


    public PhoneInfo(int phoneNumber, Person owner) {
        this.phoneNumber = phoneNumber;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner){
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "PhoneInfo{" +
                "id=" + id +
                ", phoneNumber=" + phoneNumber +
                ", owner=" + owner +
                '}';
    }
}
