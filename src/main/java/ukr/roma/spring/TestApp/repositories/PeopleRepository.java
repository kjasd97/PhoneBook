package ukr.roma.spring.TestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ukr.roma.spring.TestApp.models.Person;
import ukr.roma.spring.TestApp.models.PhoneInfo;

import java.util.List;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
