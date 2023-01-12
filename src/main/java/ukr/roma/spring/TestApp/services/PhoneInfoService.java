package ukr.roma.spring.TestApp.services;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ukr.roma.spring.TestApp.models.Person;
import ukr.roma.spring.TestApp.models.PhoneInfo;
import ukr.roma.spring.TestApp.repositories.PeopleRepository;
import ukr.roma.spring.TestApp.repositories.PhoneInfoRepository;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class PhoneInfoService {

    private final PhoneInfoRepository phoneInfoRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public PhoneInfoService(PhoneInfoRepository phoneInfoRepository, PeopleRepository peopleRepository) {
        this.phoneInfoRepository = phoneInfoRepository;
        this.peopleRepository = peopleRepository;
    }


    public List<PhoneInfo> findAllPhones(){
        return phoneInfoRepository.findAll();
    }

    public List<PhoneInfo> findByOwnerId (int id){
        return phoneInfoRepository.findByOwnerId(id);
    }

    @Transactional
    public void savePhone(PhoneInfo phoneInfo, int id){
      Optional <Person> person = Optional.ofNullable(peopleRepository.findById(id).orElse(null));
      phoneInfo.setOwner(person.get());
      phoneInfoRepository.save(phoneInfo);
    }

    @Transactional
    public void deletePhone(int id){
        phoneInfoRepository.deleteById(id);
    }
}
