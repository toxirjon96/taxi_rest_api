package uz.playground.security.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.playground.security.algorithm.Algorithm;
import uz.playground.security.constant.RoleName;
import uz.playground.security.dto.CommentDto;
import uz.playground.security.dto.DriverCarDto;
import uz.playground.security.dto.PostDto;
import uz.playground.security.dto.SecretKeyDto;
import uz.playground.security.entity.*;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.helper.SecurityHelper;
import uz.playground.security.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ApiService {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    private final RoleRepository roleRepository;
    private final DriverCarRepository driverCarRepository;
    private final CarCharacteristicsRepository carCharacteristicsRepository;
    private final UserRepository userRepository;
    private final ResponseHelper responseHelper;
    private final SecurityHelper securityHelper;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private Algorithm algorithm = new Algorithm();
    public ApiService(RoleRepository roleRepository,
                      DriverCarRepository driverCarRepository,
                      CarCharacteristicsRepository carCharacteristicsRepository,
                      UserRepository userRepository, ResponseHelper responseHelper, SecurityHelper securityHelper, PostRepository postRepository, CommentRepository commentRepository, RegionRepository regionRepository, DistrictRepository districtRepository) {
        this.roleRepository = roleRepository;
        this.driverCarRepository = driverCarRepository;
        this.carCharacteristicsRepository = carCharacteristicsRepository;
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
        this.securityHelper = securityHelper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;

        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
    }
    public ResponseEntity<?> saveDriverCar (DriverCarDto request){
        if (request.getSecretKey() == null || request.getSecretKey().equals("") || !request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (SecurityHelper.getUser().getId() == null || !SecurityHelper.getUser().getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (!isDriver(RoleName.ROLE_DRIVER.name())){
            return responseHelper.invalidData();
        }
        if (request.getCarName() == null || request.getCarName().equals("") ||
                request.getCarColor() == null || request.getCarColor().equals("") ||
                request.getPersonCount() == null || request.getPersonCount() <= 0||
                request.getCarNumber() == null || request.getCarNumber().equals("")){
            return responseHelper.invalidData();
        }
        if (carCharacteristicsRepository.existsByCarNumber(request.getCarNumber())){
            return responseHelper.prepareResponse("Bunday nomerli avtomobil mavjud", "error");
        }
        List<DriverCar> driverCarList = driverCarRepository.findByUser_Id(securityHelper.getUser().getId());
        List<CarCharacteristics> carCharacteristicsList = new ArrayList<>();
        if (driverCarList != null && driverCarList.size() > 0){
            for (DriverCar driverCar : driverCarList){
                if (driverCar.getCarCharacteristics() != null && driverCar.getCarCharacteristics().getId() != null){
                    carCharacteristicsList.addAll(carCharacteristicsRepository.findByIdIn(List.of(driverCar.getCarCharacteristics().getId())));
                }
            }
        }
        for (CarCharacteristics ch : carCharacteristicsList){
            if ((request.getCarName() != null && request.getCarName().equals(ch.getCarName())) &&
                    (request.getCarColor() != null && request.getCarColor().equals(ch.getCarColor())) &&
                    (request.getPersonCount() != null && request.getPersonCount().equals(ch.getPersonCount())) &&
                    (request.getCarNumber() != null && request.getCarNumber().equals(ch.getCarNumber()))){
                return responseHelper.carExists();
            }
        }

        CarCharacteristics carCharacteristics = new CarCharacteristics(request.getCarName(), request.getCarColor(),
                request.getPersonCount(), request.getCarNumber());
        carCharacteristics = carCharacteristicsRepository.save(carCharacteristics);

        DriverCar driverCar = new DriverCar(userRepository.findById(securityHelper.getUser().getId()).get(), carCharacteristics);
        driverCar = driverCarRepository.save(driverCar);
        if (driverCar != null){
            return responseHelper.success();
        }
        return responseHelper.error();
    }
    public ResponseEntity<?> getAllCars(SecretKeyDto request){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        List<DriverCar> list = driverCarRepository.findByUser_Id(securityHelper.getUser().getId());
        if (list.isEmpty()){
            return responseHelper.listEmpty();
        }
        List<CarCharacteristics> carCharacteristicsList = new ArrayList<>();
        list.forEach(driverCar -> carCharacteristicsList.add(driverCar.getCarCharacteristics()));
        return responseHelper.prepareResponse(carCharacteristicsList, "success");
    }
    public ResponseEntity<?> updateDriverCar(DriverCarDto request, Long carId){
        if (request.getSecretKey() == null || request.getSecretKey().equals("") || !request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (SecurityHelper.getUser().getId() == null || !SecurityHelper.getUser().getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (!isDriver(RoleName.ROLE_DRIVER.name())){
            return responseHelper.invalidData();
        }
        if (carCharacteristicsRepository.findById(carId).isEmpty()){
            return responseHelper.noDataFound();
        }
        CarCharacteristics carCharacteristics = carCharacteristicsRepository.findById(carId).get();

        if (request.getCarNumber() != null &&
                !request.getCarNumber().equals("") &&
                !request.getCarNumber().equals(carCharacteristics.getCarNumber())){
            if (carCharacteristicsRepository.existsByCarNumber(request.getCarNumber())){
                return responseHelper.prepareResponse("Bunday nomerli avtomobil mavjud", "error");
            }
            carCharacteristics.setCarNumber(request.getCarNumber());
        }
        if (request.getCarColor() != null &&
                !request.getCarColor().equals("") &&
                !request.getCarColor().equals(carCharacteristics.getCarColor())){
            carCharacteristics.setCarColor(request.getCarColor());
        }
        if (request.getCarName() != null &&
                !request.getCarName().equals("") &&
                !request.getCarName().equals(carCharacteristics.getCarName())){
            carCharacteristics.setCarName(request.getCarName());
        }
        if (request.getPersonCount() != null &&
                !request.getPersonCount().equals(carCharacteristics.getPersonCount())){
            carCharacteristics.setPersonCount(request.getPersonCount());
        }
        carCharacteristicsRepository.save(carCharacteristics);
        return responseHelper.prepareResponse("Car with id " +
                carCharacteristics.getId() + " successfully updated", "success");
    }
    public ResponseEntity<?> deleteDriverCar(SecretKeyDto request, Long carId){
        if (request.getSecretKey() == null
                || request.getSecretKey().equals("")
                || !request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (SecurityHelper.getUser().getId() == null || !SecurityHelper.getUser().getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (!isDriver(RoleName.ROLE_DRIVER.name())){
            return responseHelper.invalidData();
        }
        Optional<CarCharacteristics> carCharacteristics = carCharacteristicsRepository.findById(carId);
        if (carCharacteristics.isEmpty()){
            return responseHelper.noDataFound();
        }
        try {
            driverCarRepository.deleteByCarCharacteristicsId(carCharacteristics.get().getId());
            carCharacteristicsRepository.delete(carCharacteristics.get());
            return responseHelper.prepareResponse("Car with id " + carCharacteristics.get().getId() +
                    " is successfully deleted in the system!", "success");
        }catch (Exception e){
            return responseHelper.prepareResponse(e.getMessage(), "error");
        }
    }
    public ResponseEntity<?> deletePost(SecretKeyDto request, Long postId){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();
        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()){
            return responseHelper.noDataFound();
        }
        Post post = postOptional.get();
        try {
            postRepository.delete(post);
            return responseHelper.prepareResponse("Post with id " + post.getId() +
                    " is successfully deleted in the system!", "success");
        }catch (Exception e){
            return responseHelper.prepareResponse(e.getMessage(), "error");
        }
    }
    public ResponseEntity<?> updatePost(PostDto request, Long postId){
        boolean isTruePersonCount = false;
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();
        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()){
            return responseHelper.noDataFound();
        }
        Post post = postOptional.get();
        if (request.getFromRegionId() != null && request.getFromRegionId() > 0){
            Region fromRegion = regionRepository.findByRegionId(request.getFromRegionId()).get();
            if (!fromRegion.getId().equals(post.getFromRegion().getId())){
                post.setFromRegion(fromRegion);
            }
        }
        if (request.getFromDistrictId() != null && request.getFromDistrictId() > 0){
            District fromDistrict = districtRepository.findByDistrictId(request.getFromDistrictId()).get();
            if (!fromDistrict.getId().equals(post.getFromDistrict().getId())){
                post.setFromDistrict(fromDistrict);
            }
        }

        if (request.getToRegionId() != null && request.getToRegionId() > 0){
            Region toRegion = regionRepository.findByRegionId(request.getToRegionId()).get();
            if (!toRegion.getId().equals(post.getToRegion().getId())){
                post.setToRegion(toRegion);
            }
        }
        if (request.getToDistrictId() != null && request.getToDistrictId() > 0){
            District toDistrict = districtRepository.findByDistrictId(request.getToDistrictId()).get();
            if (!toDistrict.getId().equals(post.getToDistrict().getId())){
                post.setToDistrict(toDistrict);
            }
        }
        if (isDriver("ROLE_DRIVER")){
            if (request.getCarId() != null && request.getCarId() > 0){
                CarCharacteristics carCharacteristics = carCharacteristicsRepository.findById(request.getCarId()).get();

                if (carCharacteristics.getPersonCount() >= request.getPersonCount()){
                    isTruePersonCount = true;
                }else {
                    return responseHelper.prepareResponse("Odam soni noto'g'ri kiritildi!", "error");
                }
                if (!carCharacteristics.getId().equals(post.getCarCharacteristics().getId())){
                    post.setCarCharacteristics(carCharacteristics);
                }
                if (!driverCarRepository.existsByCarCharacteristics_IdAndUser_Id(carCharacteristics.getId(), user.getId())){
                    return responseHelper.prepareResponse("Avtomobil sizga tegishli emas", "error");
                }
            }
        }
        if (request.getDateFrom() != null && !request.getDateFrom().equals("")){
            post.setDateFrom(algorithm.convertStringToDate(request.getDateFrom()));
        }
        if (request.getDateTo() != null && !request.getDateTo().equals("")){
            post.setDateTo(algorithm.convertStringToDate(request.getDateTo()));
        }
        if (request.getTimeFrom() != null && !request.getTimeFrom().equals("")){
            LocalTime timeF = algorithm.converTime(request.getTimeFrom());
            if (!post.getTimeFrom().equals(timeF)){
                post.setTimeFrom(timeF);
            }
        }
        if (request.getTimeTo() != null && !request.getTimeTo().equals("")){
            LocalTime timeT = algorithm.converTime(request.getTimeTo());
            if (!post.getTimeTo().equals(timeT)){
                post.setTimeTo(timeT);
            }
        }
        if (request.getPersonCount() != null && request.getPersonCount() >= 0){
            if (!post.getPersonCount().equals(request.getPersonCount())){
                post.setPersonCount(request.getPersonCount());
            }
        }
        if (request.getDescription() != null && !request.getDescription().equals("")){
            if (!post.getDescription().equals(request.getDescription())){
                post.setDescription(request.getDescription());
            }
        }
        if (request.getContactNumber() != null && !request.getContactNumber().equals("")){
            if (!post.getContactNumber().equals(request.getContactNumber())){
                post.setContactNumber(request.getContactNumber());
            }
        }
        if (request.getMaxPrice() != null && request.getMaxPrice() > 0){
            if (!post.getMaxPrice().equals(request.getMaxPrice())){
                post.setMaxPrice(request.getMaxPrice());
            }
        }
        if (request.getMinPrice() != null && request.getMinPrice() > 0){
            if (!post.getMinPrice().equals(request.getMinPrice())){
                post.setMinPrice(request.getMinPrice());
            }
        }
        post.setUpdatedAt(LocalDateTime.now());
        try {
            postRepository.save(post);
            return responseHelper.prepareResponse("Post with id " + post.getId() + " successfully updated in the system!", "success");
        }catch (Exception e){
            return responseHelper.prepareResponse(e.getMessage(), "error");
        }
    }
    public ResponseEntity<?> savePost(PostDto request){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();
        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        boolean isTruePersonCount = false;

        if ((request.getFromRegionId() == null || request.getFromRegionId() <= 0) ||
                (request.getToRegionId() == null || request.getToRegionId() <= 0) ||
                (request.getPersonCount() == null || request.getPersonCount() <= 0) ||
                (request.getContactNumber() == null || request.getContactNumber().equals("")) ||
                (request.getMinPrice() == null || request.getMinPrice() <= 0)){
            return responseHelper.prepareResponse("Ma'lumotlar to'liq kiritilmadi!", "error");
        }else {
            Region fromRegion = regionRepository.findByRegionId(request.getFromRegionId()).get();
            Region toRegion = regionRepository.findByRegionId(request.getToRegionId()).get();
            District fromDistrict = null;
            District toDistrict = null;
            if (request.getFromDistrictId() > 0) {
                fromDistrict = districtRepository.findByDistrictId(request.getFromDistrictId()).get();
            }
            if (request.getToDistrictId() > 0) {
                toDistrict = districtRepository.findByDistrictId(request.getToDistrictId()).get();
            }
            CarCharacteristics carCharacteristics = null;
            Role role = null;
            if (isDriver("ROLE_DRIVER")){
                role = roleRepository.findByName(RoleName.ROLE_DRIVER).get();
                if (request.getCarId() != null && request.getCarId() > 0){
                    carCharacteristics = carCharacteristicsRepository.findById(request.getCarId()).get();
                    if (!driverCarRepository.existsByCarCharacteristics_IdAndUser_Id(carCharacteristics.getId(), user.getId())){
                        return responseHelper.prepareResponse("Avtomobil sizga tegishli emas", "error");
                    }
                    if (carCharacteristics.getPersonCount() >= request.getPersonCount()){
                        isTruePersonCount = true;
                    }else {
                        return responseHelper.prepareResponse("Odam soni noto'g'ri kiritildi!", "error");
                    }
                }else{
                    return responseHelper.prepareResponse("Avtomobil kiritilmadi!", "error");
                }
            }else {
                role = roleRepository.findByName(RoleName.ROLE_USER).get();
            }
            LocalTime tF = algorithm.converTime(request.getTimeFrom());
            LocalTime tT = algorithm.converTime(request.getTimeTo());

            Post post = new Post(user, fromRegion, fromDistrict, toRegion, toDistrict,
                    role, carCharacteristics, algorithm.convertStringToDate(request.getDateFrom()),
                    algorithm.convertStringToDate(request.getDateTo()), tF, tT,
                    request.getPersonCount(), request.getDescription(), request.getContactNumber(),
                    request.getMinPrice(), request.getMaxPrice());
            try {
                post.setCreatedAt(LocalDateTime.now());
                postRepository.save(post);
                return responseHelper.prepareResponse("Post with id " + post.getId() + " successfully saved in the system!", "success");
            }catch (Exception e){
                return responseHelper.prepareResponse(e.getMessage(), "error");
            }
        }
    }
    public ResponseEntity<?> saveComment(CommentDto request, Long postId){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()){
            return responseHelper.noDataFound();
        }
        if (request.getDescription() != null && !request.getDescription().equals("")){
            Comments comments = new Comments(postOptional.get().getId(), request.getDescription(), LocalDateTime.now());
            try {
                commentRepository.save(comments);
                return responseHelper.prepareResponse("Comment with id " + comments.getId() + " successfully saved in the system!", "success");
            }catch (Exception e){
                return responseHelper.prepareResponse(e.getMessage(), "error");
            }
        }
        return responseHelper.invalidData();
    }
    public ResponseEntity<?> getAllPosts(SecretKeyDto request){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        List<Post> list = postRepository.findAll();

        if (list.isEmpty()){
            return responseHelper.listEmpty();
        }
        return responseHelper.prepareResponse(list, "success");
    }
    public ResponseEntity<?> getAllCommentsByPostId(SecretKeyDto request, Long postId){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        List<Comments> list = commentRepository.findByPostId(postId);

        if (list.isEmpty()){
            return responseHelper.listEmpty();
        }
        return responseHelper.prepareResponse(list, "success");
    }

    public boolean isDriver(String roleName){
        List<GrantedAuthority> authorities = new ArrayList<>(securityHelper.getUser().getAuthorities());
        for (GrantedAuthority auth : authorities){
            if (auth.getAuthority().equals(roleName)){
                return true;
            }
        }
        return false;
    }
}
