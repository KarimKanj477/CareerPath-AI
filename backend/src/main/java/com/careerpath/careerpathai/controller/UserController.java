package com.careerpath.careerpathai.controller;


import com.careerpath.careerpathai.dto.ApiResponse;
import com.careerpath.careerpathai.dto.UserRequestDTO;
import com.careerpath.careerpathai.dto.UserResponseDTO;
import com.careerpath.careerpathai.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.careerpath.careerpathai.dto.UpdateUserRoleRequestDTO;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){

        this.userService=userService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {

        List<UserResponseDTO> users = userService.getAllUsers();

        ApiResponse<List<UserResponseDTO>> response =
                new ApiResponse<>(true, "Users retrieved successfully.", users);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")

    public ResponseEntity<ApiResponse<UserResponseDTO>>getUserById(@PathVariable Integer id){

        UserResponseDTO userResponse=userService.getUserById(id);
        ApiResponse<UserResponseDTO>response=new ApiResponse<>(true,"User retrieved successfully",userResponse);
        return ResponseEntity.ok(response);

    }
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>>createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){

        UserResponseDTO createdUser=userService.createUser(userRequestDTO);
        ApiResponse<UserResponseDTO> response= new ApiResponse<>(true,"User created successfully",createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@PathVariable Integer id,
            @Valid @RequestBody UserRequestDTO requestDTO) {

        UserResponseDTO updatedUser = userService.updateUser(id, requestDTO);

        ApiResponse<UserResponseDTO> response =
                new ApiResponse<>(true, "User updated successfully.",
                updatedUser);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("/{id}/role")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUserRole(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateUserRoleRequestDTO requestDTO) {

        UserResponseDTO updatedUser =
                userService.updateUserRole(
                        id,
                        requestDTO.getRoleId()
                );

        ApiResponse<UserResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "User role updated successfully.",
                        updatedUser
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")

    public  ResponseEntity<ApiResponse<Object>>deleteUser(@PathVariable Integer id){

        userService.deleteUser(id);
        ApiResponse<Object> response=
                new ApiResponse<>(true,"User deleted successfully",null);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByEmail(@PathVariable String email) {

        UserResponseDTO userResponse = userService.getUserByEmail(email);
        ApiResponse<UserResponseDTO> response =
                new ApiResponse<>(true, "User retrieved successfully.", userResponse);

        return ResponseEntity.ok(response);
    }
















}
