package ukr.roma.spring.TestApp.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name_surname")
    private String nameSurname;

    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @JsonManagedReference
    private List<PhoneInfo> phoneInfoList;

    public Person(){

    }

    public Person(String nameSurname, int age, List<PhoneInfo> phoneInfoList) {
        this.nameSurname = nameSurname;
        this.age = age;
        this.phoneInfoList = phoneInfoList;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<PhoneInfo> getPhoneInfoList() {
        return phoneInfoList;
    }

    public void setPhoneInfoList(List<PhoneInfo> phoneInfoList) {
        this.phoneInfoList = phoneInfoList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "nameSurname='" + nameSurname + '\'' +
                ", age=" + age +
                '}';
    }
}
