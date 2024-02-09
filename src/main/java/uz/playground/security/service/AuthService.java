package uz.playground.security.service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.playground.security.algorithm.Algorithm;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.RoleName;
import uz.playground.security.dto.*;
import uz.playground.security.entity.*;
import uz.playground.security.helper.ResponseHelper;
import uz.playground.security.helper.SecurityHelper;
import uz.playground.security.repository.*;
import uz.playground.security.security.JwtProvider;
import uz.playground.security.security.UserPrincipal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

import static uz.playground.security.dto.ResponseData.response;
@Service
@Transactional
public class AuthService {
    @Value("${app.jwtSecret}")
    private String jwtSecret;
    private Algorithm algorithm = new Algorithm();
    private final UserRepository userRepository;
    private final ResponseHelper responseHelper;
    private final SecurityHelper securityHelper;
    private final PasswordEncoder passwordEncoder;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final CustomUserDetailService userDetailsService;
    private final DriverCarRepository driverCarRepository;
    private final CarCharacteristicsRepository carCharacteristicsRepository;
    public AuthService(UserRepository userRepository,
                       ResponseHelper responseHelper,
                       SecurityHelper securityHelper, @Qualifier("passwordEncoder") PasswordEncoder passwordEncoder,
                       RegionRepository regionRepository, DistrictRepository districtRepository, RoleRepository roleRepository,
                       AuthenticationManager authenticationManager,
                       JwtProvider jwtProvider, CustomUserDetailService userDetailsService, DriverCarRepository driverCarRepository, CarCharacteristicsRepository carCharacteristicsRepository) {
        this.userRepository = userRepository;
        this.responseHelper = responseHelper;
        this.securityHelper = securityHelper;
        this.passwordEncoder = passwordEncoder;
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
        this.driverCarRepository = driverCarRepository;
        this.carCharacteristicsRepository = carCharacteristicsRepository;
    }
    public ResponseEntity<?> loginUser(LoginDto request){
        boolean userCheckRole = false;
        Integer roleId = 0;
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userPrincipal.getAuthorities().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        });

        for (int i = 0; i < authorities.size(); i++){
            if (authorities.get(i).getAuthority().equals(request.getRoleName())){
                roleId = i;
                userCheckRole = true;
                break;
            }
        }
        if (!userPrincipal.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (!userCheckRole){
            return responseHelper.invalidData();
        }
        LoginResponse response = new LoginResponse(jwtProvider.generateToken(authentication), userPrincipal.getStatus(), authorities.get(roleId).getAuthority());
        return responseHelper.prepareResponse(response, "success");
    }
    public ResponseEntity<?> deleteUser(SecretKeyDto request, Long userId){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        Optional<User> user = userRepository.findById(userId);
        if (isDriver(RoleName.ROLE_ADMIN.name(), user.get())){
            return responseHelper.error();
        }
        if (user.isEmpty()){
            return responseHelper.noDataFound();
        }
        if (!securityHelper.getUser().getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (isDriver("ROLE_DRIVER", user.get())){
            List<DriverCar> driverCarList = driverCarRepository.findByUser_Id(user.get().getId());
            if (driverCarList != null &&
                    driverCarList.size() > 0) {
                driverCarList.forEach(driverCar -> {
                    driverCarRepository.delete(driverCar);
                    carCharacteristicsRepository.delete(driverCar.getCarCharacteristics());
                });
            }
        }
        userRepository.delete(user.get());
        return responseHelper.prepareResponse("You have successfully deleted user with " + userId, "success");
    }
    public ResponseEntity<?> updateUser(UpdateUserByAdminDto request, Long userId){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.findById(userId).isEmpty()){
            return responseHelper.noDataFound();
        }
        User user = userRepository.findById(userId).get();
        if (!securityHelper.getUser().getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (request.getSurname() != null && !request.getSurname().equals("")){
            user.setSurname(request.getSurname());
        }
        if (request.getName() != null && !request.getName().equals("")){
            user.setName(request.getName());
        }
        if (request.getPatronym() != null && !request.getPatronym().equals("")){
            user.setPatronym(request.getPatronym());
        }
        if (request.getStatus() != null && !request.getStatus().equals("")){
            user.setStatus(request.getStatus());
        }
        if (algorithm.convertStringToDate(request.getDateOfBirth()) != null){
            user.setDateOfBirth(algorithm.convertStringToDate(request.getDateOfBirth()));
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals("")){
            if (!user.getPhoneNumber().equals(request.getPhoneNumber())){
                if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
                    return responseHelper.numberExists();
                }
                user.setPhoneNumber(request.getPhoneNumber());
            }
        }
        if (request.getEmail() != null && !request.getEmail().equals("")){
            if (!user.getEmail().equals(request.getEmail())){
                if (userRepository.existsByEmail(request.getEmail())){
                    return responseHelper.emailExists();
                }
                user.setEmail(request.getEmail());
            }
        }
        if (request.getPassword() != null && !request.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getLang() != null && !request.getLang().equals("")){
            user.setLang(Lang.valueOf(request.getLang()));
        }
        Set<Role> roles = new HashSet<>(user.getRoles());
        Optional<Role> userRole = null;
        if (request.getRoleName().equals("ROLE_USER")){
            userRole = roleRepository.findByName(RoleName.ROLE_USER);
        }else if (request.getRoleName().equals("ROLE_ANONYMOUS")){
            userRole = roleRepository.findByName(RoleName.ROLE_ANONYMOUS);
        }else if (request.getRoleName().equals("ROLE_DRIVER")){
            userRole = roleRepository.findByName(RoleName.ROLE_DRIVER);
        }
        if (userRole.isEmpty()){
            return responseHelper.noDataFound();
        }
        roles.add(userRole.get());
        user.setRoles(roles);
        byte[] image = null;
        if (request.getImage() != null && !request.getImage().equals("")){
            image = Base64.getDecoder().decode(request.getImage().getBytes(StandardCharsets.UTF_8));
            user.setImage(image);
        }
        userRepository.save(user);
        return responseHelper.prepareResponse(String.format("User with %s id have successfully updated in the system", user.getId()), "success");
    }
    public ResponseEntity<?> updateUser(SignupDto request){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.findById(securityHelper.getUser().getId()).isEmpty()){
            return responseHelper.noDataFound();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();

        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (request.getSurname() != null && !request.getSurname().equals("")){
            user.setSurname(request.getSurname());
        }
        if (request.getName() != null && !request.getName().equals("")){
            user.setName(request.getName());
        }
        if (request.getPatronym() != null && !request.getPatronym().equals("")){
            user.setPatronym(request.getPatronym());
        }
        if (algorithm.convertStringToDate(request.getDateOfBirth()) != null){
            user.setDateOfBirth(algorithm.convertStringToDate(request.getDateOfBirth()));
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals("")){
            if (!user.getPhoneNumber().equals(request.getPhoneNumber())){
                if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
                    return responseHelper.numberExists();
                }
                user.setPhoneNumber(request.getPhoneNumber());
            }
        }
        if (request.getEmail() != null && !request.getEmail().equals("")){
            if (!user.getEmail().equals(request.getEmail())){
                if (userRepository.existsByEmail(request.getEmail())){
                    return responseHelper.emailExists();
                }
                user.setEmail(request.getEmail());
            }
        }
        if (request.getPassword() != null && !request.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getLang() != null && !request.getLang().equals("")){
            user.setLang(Lang.valueOf(request.getLang()));
        }
        Optional<Role> userRole = null;
        if (request.getRoleName().equals("ROLE_USER")){
            userRole = roleRepository.findByName(RoleName.ROLE_USER);
        }else if (request.getRoleName().equals("ROLE_ANONYMOUS")){
            userRole = roleRepository.findByName(RoleName.ROLE_ANONYMOUS);
        }else if (request.getRoleName().equals("ROLE_DRIVER")){
            userRole = roleRepository.findByName(RoleName.ROLE_DRIVER);
        }
        if (userRole.isEmpty()){
            return responseHelper.noDataFound();
        }
        Set<Role> userRoles = new HashSet<>(user.getRoles());
        userRoles.add(userRole.get());
        user.setRoles(userRoles);
        byte[] image = null;
        if (request.getImage() != null && !request.getImage().equals("")){
            image = Base64.getDecoder().decode(request.getImage().getBytes(StandardCharsets.UTF_8));
            user.setImage(image);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserById(user.getId());
        List<GrantedAuthority> updatedAuthority = new ArrayList<>(userDetails.getAuthorities());
        updatedAuthority.add(new SimpleGrantedAuthority(userRole.get().getName().name()));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), updatedAuthority);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return responseHelper.prepareResponse("User with " + user.getId() + " id have successfully updated in the system", "success");
    }
    public ResponseEntity<?> updateAdmin(SignupDto request){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.findById(securityHelper.getUser().getId()).isEmpty()){
            return responseHelper.noDataFound();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();

        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }

        if (request.getSurname() != null && !request.getSurname().equals("")){
            user.setSurname(request.getSurname());
        }
        if (request.getName() != null && !request.getName().equals("")){
            user.setName(request.getName());
        }
        if (request.getPatronym() != null && !request.getPatronym().equals("")){
            user.setPatronym(request.getPatronym());
        }
        if (algorithm.convertStringToDate(request.getDateOfBirth()) != null){
            user.setDateOfBirth(algorithm.convertStringToDate(request.getDateOfBirth()));
        }
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().equals("")){
            if (!user.getPhoneNumber().equals(request.getPhoneNumber())){
                if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
                    return responseHelper.numberExists();
                }
                user.setPhoneNumber(request.getPhoneNumber());
            }
        }
        if (request.getEmail() != null && !request.getEmail().equals("")){
            if (!user.getEmail().equals(request.getEmail())){
                if (userRepository.existsByEmail(request.getEmail())){
                    return responseHelper.emailExists();
                }
                user.setEmail(request.getEmail());
            }
        }
        if (request.getPassword() != null && !request.getPassword().equals("")){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getLang() != null && !request.getLang().equals("")){
            user.setLang(Lang.valueOf(request.getLang()));
        }
        Optional<Role> userRole = null;
        if (request.getRoleName().equals("ROLE_USER")){
            userRole = roleRepository.findByName(RoleName.ROLE_USER);
        }else if (request.getRoleName().equals("ROLE_ANONYMOUS")){
            userRole = roleRepository.findByName(RoleName.ROLE_ANONYMOUS);
        }else if (request.getRoleName().equals("ROLE_DRIVER")){
            userRole = roleRepository.findByName(RoleName.ROLE_DRIVER);
        }else if (request.getRoleName().equals("ROLE_ADMIN")){
            userRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
        }
        if (userRole.isEmpty()){
            return responseHelper.noDataFound();
        }
        Set<Role> userRoles = new HashSet<>(user.getRoles());
        userRoles.add(userRole.get());
        user.setRoles(userRoles);
        byte[] image = null;
        if (request.getImage() != null && !request.getImage().equals("")){
            image = Base64.getDecoder().decode(request.getImage().getBytes(StandardCharsets.UTF_8));
            user.setImage(image);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserById(user.getId());
        List<GrantedAuthority> updatedAuthority = new ArrayList<>(userDetails.getAuthorities());
        updatedAuthority.add(new SimpleGrantedAuthority(userRole.get().getName().name()));
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), updatedAuthority);
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return responseHelper.prepareResponse("User with " + user.getId() + " id have successfully updated in the system", "success");
    }
    public ResponseEntity<?> deleteRole(DeleteRoleDto request){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.findById(securityHelper.getUser().getId()).isEmpty()){
            return responseHelper.noDataFound();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        if (request.getRoleName() == null ||
                request.getRoleName().equals("") ||
                request.getRoleName().equals("ROLE_ADMIN")){
            return responseHelper.invalidData();
        }
        if (request.getRoleName().equals("ROLE_DRIVER")){
            List<DriverCar> driverCarList = driverCarRepository.findByUser_Id(securityHelper.getUser().getId());
            if (driverCarList != null &&
                    driverCarList.size() > 0) {
                driverCarList.forEach(driverCar -> {
                    driverCarRepository.delete(driverCar);
                    carCharacteristicsRepository.delete(driverCar.getCarCharacteristics());
                });
            }
        }
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority(request.getRoleName()))){
            return responseHelper.prepareResponse("Bunday role sizda mavjud emas!", "error");
        }
        if (user.getRoles().size() == 1){
            return responseHelper.prepareResponse("Siz bu rolni o'chira olmaysiz!", "error");
        }
        user.setRoles(user.getRoles()
                .stream().filter(role -> !role.getName().name().equals(request.getRoleName()))
                .collect(Collectors.toSet()));

        if (user.getRoles().size() == 0){
            userRepository.delete(user);
            return responseHelper.prepareResponse("Siz tizimdan butunlay o'chib ketdingiz", "success");
        }

        UserDetails userDetails = userDetailsService.loadUserById(user.getId());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails,
                authentication.getCredentials(),
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return responseHelper.prepareResponse(request.getRoleName() + " have successfully deleted in the system", "success");
    }
    public ResponseEntity<?> deleteUser(SecretKeyDto request){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.findById(securityHelper.getUser().getId()).isEmpty()){
            return responseHelper.noDataFound();
        }
        User user = userRepository.findById(securityHelper.getUser().getId()).get();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!user.getStatus().equals("A")){
            return responseHelper.prohibitedUser();
        }
        userRepository.delete(user);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                authentication.getCredentials(),
                authentication.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return responseHelper.prepareResponse("Siz tizimdan butunlay o'chib ketdingiz", "success");
    }
    public ResponseEntity<?> deleteRoleByAdmin(DeleteRoleDto request, Long userId){
        if (!request.getSecretKey().equals(jwtSecret)){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.findById(userId).isEmpty()){
            return responseHelper.noDataFound();
        }
        User user = userRepository.findById(userId).get();

        if (request.getRoleName() == null ||
                request.getRoleName().equals("") ||
                request.getRoleName().equals("ROLE_ADMIN")){
            return responseHelper.invalidData();
        }
        if (request.getRoleName().equals("ROLE_DRIVER")){
            List<DriverCar> driverCarList = driverCarRepository.findByUser_Id(securityHelper.getUser().getId());
            if (driverCarList != null &&
                    driverCarList.size() > 0) {
                driverCarList.forEach(driverCar -> {
                    driverCarRepository.delete(driverCar);
                    carCharacteristicsRepository.delete(driverCar.getCarCharacteristics());
                });
            }
        }
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());
        if (!authorities.contains(new SimpleGrantedAuthority(request.getRoleName()))){
            return responseHelper.invalidData();
        }
        user.setRoles(user.getRoles()
                .stream().filter(role -> !role.getName().name().equals(request.getRoleName()))
                .collect(Collectors.toSet()));
        if (user.getRoles().size() == 0){
            userRepository.delete(user);
            return responseHelper.prepareResponse("User " + user.getId() + " " +
                    request.getRoleName() + " successfully deleted", "success");
        }
        try {
            userRepository.save(user);
            return responseHelper.prepareResponse("User " + user.getId() + " " +
                    request.getRoleName() + " successfully deleted", "success");
        }catch (Exception e){
            return responseHelper.prepareResponse(e.getMessage(), "error");
        }

    }
    public ResponseEntity<?> registerUser(SignupDto request){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            return responseHelper.numberExists();
        }
        if ((request.getPassword() == null || request.getPassword().equals(""))){
            return responseHelper.invalidData();
        }
        String email = null;
        if ((request.getEmail() != null && !request.getEmail().equals("")) &&
                userRepository.existsByEmail(request.getEmail())){
            return responseHelper.emailExists();
        }else {
            email = request.getEmail();
        }
        byte[] image = null;
        if (request.getImage() != null && !request.getImage().equals("")){
            image = Base64.getDecoder().decode(request.getImage().getBytes(StandardCharsets.UTF_8));
        }
        Lang lang = null;
        try {
            lang = Lang.valueOf(request.getLang());
        }catch (Exception e){
            lang = Lang.UZ;
        }
        User user = new User(request.getSurname(), request.getName(),
                request.getPatronym(), algorithm.convertStringToDate(request.getDateOfBirth()),
               "A", request.getPhoneNumber(),image, email,
                request.getPassword(),lang);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> userRole = null;
        if (request.getRoleName().equals("ROLE_USER")){
            userRole = roleRepository.findByName(RoleName.ROLE_USER);
        }else if (request.getRoleName().equals("ROLE_ADMIN")){
            userRole = roleRepository.findByName(RoleName.ROLE_ADMIN);
        }else if (request.getRoleName().equals("ROLE_ANONYMOUS")){
            userRole = roleRepository.findByName(RoleName.ROLE_ANONYMOUS);
        }else if (request.getRoleName().equals("ROLE_DRIVER")){
            userRole = roleRepository.findByName(RoleName.ROLE_DRIVER);
        }
        if (userRole.isEmpty()){
            return responseHelper.noDataFound();
        }
        user.setRoles(Collections.singleton(userRole.get()));
        userRepository.save(user);
        return response(String.format("User with %s id have successfully registered in the system", user.getId()));
    }
    public ResponseEntity<?> getAllUsers(SecretKeyDto request) {
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        List<User> list = userRepository.findAll();

        if (list.isEmpty()){
            return responseHelper.listEmpty();
        }
        return responseHelper.prepareResponse(list, "success");
    }
    public ResponseEntity<?> getAllRegion(SecretKeyDto request){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        List<Region> list = regionRepository.findAll();
        if (list.isEmpty()){
            return responseHelper.listEmpty();
        }
        return responseHelper.prepareResponse(list, "success");
    }
    public ResponseEntity<?> getAllDistrictByRegionId(DistrictRequestDto request){
        if (!jwtSecret.equals(request.getSecretKey())){
            return responseHelper.incorrectSecretKey();
        }
        if (request.getRegionId() == null || request.getRegionId() <= 0){
            return responseHelper.invalidData();
        }
        List<District> list = districtRepository.findByRegionId(request.getRegionId());
        if (list.isEmpty()){
            return responseHelper.listEmpty();
        }
        return responseHelper.prepareResponse(list, "success");
    }
    public boolean isDriver(String roleName, User user){
        List<GrantedAuthority> authorities = new ArrayList<>(
                user.getRoles().stream().map(role ->
                        new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList())
        );
        for (GrantedAuthority auth : authorities){
            if (auth.getAuthority().equals(roleName)){
                return true;
            }
        }
        return false;
    }

}
