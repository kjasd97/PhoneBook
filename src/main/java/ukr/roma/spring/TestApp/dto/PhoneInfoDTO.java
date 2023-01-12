package ukr.roma.spring.TestApp.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class PhoneInfoDTO {

    @Min(value = 1, message = "Number should be greater than zero")
    private int phoneNumber;


    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
