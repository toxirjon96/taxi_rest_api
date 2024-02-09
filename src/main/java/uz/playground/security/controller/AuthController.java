package uz.playground.security.controller;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.playground.security.dto.*;
import uz.playground.security.service.ApiService;
import uz.playground.security.service.AuthService;

@RestController
@RequestMapping(value = "/taksibor", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;
    private final ApiService apiService;
    public AuthController(AuthService authService, ApiService apiService) {
        this.authService = authService;
        this.apiService = apiService;
    }

    @PostMapping("/auth0/login")
    public ResponseEntity<?> login(@RequestBody LoginDto request){
        return authService.loginUser(request);
    }

    @PostMapping("/auth0/register")
    public ResponseEntity<?> register(@RequestBody SignupDto request){
        return authService.registerUser(request);
    }

    @PostMapping("/post/getAllPost")
    public ResponseEntity<?> getAllPost(@RequestBody SecretKeyDto request){
        return apiService.getAllPosts(request);
    }

    @PutMapping("/comment/saveComment/{postId}")
    public ResponseEntity<?> getAllPost(@RequestBody CommentDto request, @PathVariable Long postId){
        return apiService.saveComment(request, postId);
    }
    @PostMapping("/comment/getAllCommentsByPostId/{postId}")
    public ResponseEntity<?> getAllCommentsByPostId(@RequestBody SecretKeyDto request, @PathVariable Long postId){
        return apiService.getAllCommentsByPostId(request, postId);
    }
    @PostMapping("/region/getAllRegion")
    public ResponseEntity<?> getAllRegion(@RequestBody SecretKeyDto request){
        return authService.getAllRegion(request);
    }
    @PostMapping("/district/getAllDistrictByRegionId")
    public ResponseEntity<?> getAllDistrictByRegionId(@RequestBody DistrictRequestDto request){
        return authService.getAllDistrictByRegionId(request);
    }
}