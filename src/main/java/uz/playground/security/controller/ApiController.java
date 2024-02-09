package uz.playground.security.controller;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.playground.security.dto.*;
import uz.playground.security.service.ApiService;
import uz.playground.security.service.AuthService;

@RestController
@RequestMapping(value = "/taksibor/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ApiController {
    private final ApiService apiService;
    private final AuthService authService;

    public ApiController(ApiService apiService, AuthService authService) {
        this.apiService = apiService;
        this.authService = authService;
    }
    @PostMapping("/saveDriverCar")
    public ResponseEntity<?> saveDriverCar(@RequestBody DriverCarDto request){
        return apiService.saveDriverCar(request);
    }
    @PostMapping("/savePost")
    public ResponseEntity<?> savePost(@RequestBody PostDto request){
        return apiService.savePost(request);
    }
    @PutMapping("/updatePost/{postId}")
    public ResponseEntity<?> updatePost(@RequestBody PostDto request, @PathVariable Long postId){
        return apiService.updatePost(request, postId);
    }
    @PutMapping("/updateDriverCar/{carId}")
    public ResponseEntity<?> saveDriverCar(@RequestBody DriverCarDto request, @PathVariable Long carId){
        return apiService.updateDriverCar(request, carId);
    }
    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody SecretKeyDto request){
        return authService.deleteUser(request);
    }
    @DeleteMapping("/deleteDriverCar/{carId}")
    public ResponseEntity<?> deleteDriverCar(@RequestBody SecretKeyDto request, @PathVariable Long carId){
        return apiService.deleteDriverCar(request, carId);
    }
    @DeleteMapping("/deletePost/{postId}")
    public ResponseEntity<?> deletePost(@RequestBody SecretKeyDto request, @PathVariable Long postId){
        return apiService.deletePost(request, postId);
    }
    @PostMapping("/getAllCars")
    public ResponseEntity<?> getAllCars(@RequestBody SecretKeyDto request){
        return apiService.getAllCars(request);
    }
    @PutMapping("/admin/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UpdateUserByAdminDto request){
        return authService.updateUser(request, userId);
    }
    @DeleteMapping("/admin/deleteUser/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, @RequestBody SecretKeyDto request){
        return authService.deleteUser(request, userId);
    }
    @DeleteMapping("/admin/deletePost/{postId}")
    public ResponseEntity<?> deletePostByAdmin(@RequestBody SecretKeyDto request, @PathVariable Long postId){
        return apiService.deletePost(request, postId);
    }
    @DeleteMapping("/admin/deleteUserRole/{userId}")
    public ResponseEntity<?> deleteUserRole(@PathVariable Long userId, @RequestBody DeleteRoleDto request){
        return authService.deleteRoleByAdmin(request, userId);
    }
    @PostMapping("/admin/getAllUser")
    public ResponseEntity<?> getAllUser(@RequestBody SecretKeyDto request){
        return authService.getAllUsers(request);
    }
    @PutMapping("/admin/updateAdmin")
    public ResponseEntity<?> updateAdmin(@RequestBody SignupDto request){
        return authService.updateAdmin(request);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody SignupDto request){
        return authService.updateUser(request);
    }

    @DeleteMapping("/deleteRole")
    public ResponseEntity<?> deleteRole(@RequestBody DeleteRoleDto request){
        return authService.deleteRole(request);
    }
}
