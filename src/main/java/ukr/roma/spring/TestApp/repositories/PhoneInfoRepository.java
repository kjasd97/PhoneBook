package ukr.roma.spring.TestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ukr.roma.spring.TestApp.models.PhoneInfo;

import java.util.List;

public interface PhoneInfoRepository extends JpaRepository<PhoneInfo, Integer> {
public  List <PhoneInfo> findByOwnerId (int id);
}
