package com.project.reelRadar.controller;

import com.project.reelRadar.dto.DeleteRequestDTO;
import com.project.reelRadar.dto.UpdateRequestDTO;
import com.project.reelRadar.exception.UserNotFoundException;
import com.project.reelRadar.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Delete User",
            description = "Deletes a user based on the provided details in the request body. If the user is not found, a `UserNotFoundException` is thrown."
    )
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody DeleteRequestDTO deleteRequestDTO) throws UserNotFoundException {
        userService.delete(deleteRequestDTO);
        return ResponseEntity.ok().build();
    }

    /** **************** **/
    /** Test this method **/
    /** **************** **/
    @Operation(
            summary = "Update User",
            description = "Updates a user based on the provided details in the request body. If the user is not found, a `UserNotFoundException` is thrown."
    )
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody UpdateRequestDTO updateRequestDTO) throws UserNotFoundException {
    userService.update(updateRequestDTO);
    return ResponseEntity.ok().build();
    }
}