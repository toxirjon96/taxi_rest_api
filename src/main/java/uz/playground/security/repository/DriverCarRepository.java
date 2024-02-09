package uz.playground.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.playground.security.entity.DriverCar;

import java.util.List;

@Repository
public interface DriverCarRepository extends JpaRepository<DriverCar, Long> {
    List<DriverCar> findByUser_Id(Long userId);
    void deleteByCarCharacteristicsId(Long id);
    Boolean existsByCarCharacteristics_IdAndUser_Id(Long carId, Long userId);
}
