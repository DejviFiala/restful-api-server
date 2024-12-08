package utb.fai.RESTAPIServer;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 1. Získání seznamu všech uživatelů
    @GetMapping("/users")
    public ResponseEntity<List<MyUser>> getAllUsers() {
        List<MyUser> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // 2. Získání jednoho uživatele dle ID
    @GetMapping("/getUser")
    public ResponseEntity<MyUser> getUserById(@RequestParam Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user.get());
    }

    // 3. Vytvoření nového uživatele
    @PostMapping("/createUser")
    public ResponseEntity<MyUser> createUser(@RequestBody MyUser newUser) {
        if (!newUser.isUserDataValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        MyUser savedUser = userRepository.save(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/editUser")
    public ResponseEntity<MyUser> editUser(@RequestParam Long id, @RequestBody MyUser updatedData) {
        Optional<MyUser> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    
        // Zde kontrola validity:
        if (!updatedData.isUserDataValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    
        MyUser existingUser = existingUserOptional.get();
        existingUser.setName(updatedData.getName());
        existingUser.setEmail(updatedData.getEmail());
        existingUser.setPhoneNumber(updatedData.getPhoneNumber());
        MyUser saved = userRepository.save(existingUser);
        return ResponseEntity.ok(saved);
    }    

    // 5. Smazání uživatele dle ID
    @DeleteMapping("/deleteUser")
    public ResponseEntity<Void> deleteUser(@RequestParam Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            // Uživateli lze vrátit 404 pokud není nalezen (dle logiky GET endpointu)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // Pokud dojde k chybě
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 6. Smazání všech uživatelů
    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAllUsers() {
        try {
            userRepository.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
