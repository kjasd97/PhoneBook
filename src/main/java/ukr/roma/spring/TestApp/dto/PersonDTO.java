package ukr.roma.spring.TestApp.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class PersonDTO {

    @NotEmpty(message = "Name should be not empty")
    @Size(min = 2, max = 50, message = "Name and Surname should be between 2 and 50 characters")
    private String nameSurname;

    @Min(value = 0, message = "Age should be grater than zero")
    private int age;



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

}
