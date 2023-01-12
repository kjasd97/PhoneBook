package ukr.roma.spring.TestApp.controllers;


import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ukr.roma.spring.TestApp.dto.PhoneInfoDTO;
import ukr.roma.spring.TestApp.models.PhoneInfo;
import ukr.roma.spring.TestApp.services.PhoneInfoService;
import ukr.roma.spring.TestApp.util.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/phone")
public class PhoneInfoController {

    private final PhoneInfoService phoneInfoService;
    private final ModelMapper modelMapper;

    @Autowired
    public PhoneInfoController(PhoneInfoService phoneInfoService, ModelMapper modelMapper) {
        this.phoneInfoService = phoneInfoService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PhoneInfoDTO> getPhones (){
        return phoneInfoService.findAllPhones().
                stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public List<PhoneInfoDTO> findPhoneByOwnerId(@PathVariable("id") int id){
        return phoneInfoService.findByOwnerId(id).
                stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @PostMapping("/{id}")
    public ResponseEntity<HttpStatus> createPhone(@PathVariable("id") int id, @RequestBody
    @Valid PhoneInfoDTO phoneInfoDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List <FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error: errors){
                errorMsg.append(error.getField()).append(" - ").
                        append(error.getDefaultMessage()).append("; ");
            }
            throw new PhoneInfoNotCreatedException(errorMsg.toString());
        }
        phoneInfoService.savePhone(convertToPhoneInfo(phoneInfoDTO), id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable ("id") int id){
        phoneInfoService.deletePhone(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

    private PhoneInfoDTO convertToDTO (PhoneInfo phoneInfo){
        return modelMapper.map(phoneInfo, PhoneInfoDTO.class);
    }

    private PhoneInfo convertToPhoneInfo(PhoneInfoDTO phoneInfoDTO){
        return modelMapper.map(phoneInfoDTO, PhoneInfo.class);
    }

    @ExceptionHandler
    private ResponseEntity<PhoneInfoErrorResponse> handleException (PhoneInfoNotFoundException e){
     PhoneInfoErrorResponse phoneInfoErrorResponse = new PhoneInfoErrorResponse(
                "Phone with this person_id wasn't found", System.currentTimeMillis());
        return new ResponseEntity<>(phoneInfoErrorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<PhoneInfoErrorResponse> handleException (PersonNotCreatedException e){
       PhoneInfoErrorResponse phoneInfoErrorResponse = new PhoneInfoErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(phoneInfoErrorResponse, HttpStatus.BAD_REQUEST);
    }

}
