package uz.playground.security.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.playground.security.entity.Region;
import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long>{
    Optional<Region> findByRegionId(Long regionId);
}
