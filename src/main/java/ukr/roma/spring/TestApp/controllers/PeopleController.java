package ukr.roma.spring.TestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ukr.roma.spring.TestApp.dto.PersonDTO;
import ukr.roma.spring.TestApp.models.Person;
import ukr.roma.spring.TestApp.services.PeopleService;
import ukr.roma.spring.TestApp.util.PersonErrorResponse;
import ukr.roma.spring.TestApp.util.PersonNotCreatedException;
import ukr.roma.spring.TestApp.util.PersonNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;


    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id){
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
        StringBuilder errorMsg = new StringBuilder();

        List <FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error: errors){
        errorMsg.append(error.getField()).append(" - ").
                append(error.getDefaultMessage()).append("; ");
            }
        throw new PersonNotCreatedException(errorMsg.toString());
        }
        peopleService.save(convertToPerson(personDTO));

        return  ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete (@PathVariable("id") int id){
        peopleService.delete(id);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }



    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException (PersonNotFoundException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                "Person with this id wasn't found", System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException (PersonNotCreatedException e){
        PersonErrorResponse personErrorResponse = new PersonErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(personErrorResponse, HttpStatus.BAD_REQUEST);
    }

    public Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO (Person person){
        return modelMapper.map(person, PersonDTO.class);
    }

}
