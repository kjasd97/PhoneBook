package ukr.roma.spring.TestApp.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ukr.roma.spring.TestApp.dto.PersonDTO;
import ukr.roma.spring.TestApp.models.Person;
import ukr.roma.spring.TestApp.repositories.PeopleRepository;
import ukr.roma.spring.TestApp.util.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;


    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElseThrow(PersonNotFoundException::new);
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

}
