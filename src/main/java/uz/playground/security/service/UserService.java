package uz.playground.security.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.playground.security.algorithm.Algorithm;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.RoleName;
import uz.playground.security.dto.DistrictDto;
import uz.playground.security.dto.RegionDto;
import uz.playground.security.entity.District;
import uz.playground.security.entity.Region;
import uz.playground.security.entity.Role;
import uz.playground.security.entity.User;
import uz.playground.security.repository.DistrictRepository;
import uz.playground.security.repository.RegionRepository;
import uz.playground.security.repository.RoleRepository;
import uz.playground.security.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private Algorithm algorithm = new Algorithm();
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, DistrictRepository districtRepository, RegionRepository regionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.districtRepository = districtRepository;
        this.regionRepository = regionRepository;
    }
    @PostConstruct
    public void init(){
        if (userRepository.findAll().size() == 0){
            User user = new User("Oribjonov","Toxirjon", "Tolibjon o'g'li",
                    algorithm.convertStringToDate("1996-05-13"),
                    "A", "+998946133644",null, "TATU.Toxirjon@gmail.com", "!Admin123@", Lang.EN);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
            user.setRoles(Collections.singleton(userRole.get()));
            userRepository.save(user);
        }
        saveRegionAndDistrict();
    }
    public void saveRegionAndDistrict(){
        RegionDto [] regionDtoArray = algorithm.readRegionFromJsonFile();
        DistrictDto [] districtDtoArray = algorithm.readDistrictFromJsonFile();

        if (regionDtoArray != null){
            if (regionRepository.findAll().size() == 0){
                for (RegionDto regionDto: regionDtoArray){
                    Region r = new Region();
                    r.setRegionId(algorithm.getIntegerFromDoubleString(regionDto.getSP_ID()));
                    r.setNameCyrillic(regionDto.getSP_NAME00());
                    r.setNameRussian(regionDto.getSP_NAME01());
                    r.setNameLatin(regionDto.getSP_NAME04());
                    regionRepository.save(r);
                }
            }
        }
        if (districtDtoArray != null){
            if (districtRepository.findAll().size() == 0){
                for (DistrictDto districtDto: districtDtoArray){
                    District d = new District();
                    d.setDistrictId(algorithm.getIntegerFromDoubleString(districtDto.getSP_ID()));
                    d.setNameCyrillic(districtDto.getSP_NAME00());
                    d.setNameRussian(districtDto.getSP_NAME01());
                    d.setNameLatin(districtDto.getSP_NAME04());
                    d.setRegionId(algorithm.getIntegerFromDoubleString(districtDto.getSP_REGION()));
                    districtRepository.save(d);
                }
            }
        }
    }
}
