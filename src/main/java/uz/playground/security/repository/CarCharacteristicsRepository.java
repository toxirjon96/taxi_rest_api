package uz.playground.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.playground.security.entity.CarCharacteristics;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarCharacteristicsRepository extends JpaRepository<CarCharacteristics, Long> {
    List<CarCharacteristics> findByIdIn(List<Long> idList);
    Optional<CarCharacteristics> findById(Long id);
    Boolean existsByCarNumber(String carNumber);
}
