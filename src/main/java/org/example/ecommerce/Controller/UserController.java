package org.example.ecommerce.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.ecommerce.Api.ApiResponse;
import org.example.ecommerce.Model.User;
import org.example.ecommerce.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity<?>getAllUsers(){
        if (userService.getAllUser().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("Users list is empty"));
        }
        return ResponseEntity.ok(userService.getAllUser());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleUser(@PathVariable String id){
        if (userService.getSingleUser(id) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("user: "+id+" not found"));
        }
        return ResponseEntity.ok(userService.getSingleUser(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors){

        //checking for object error
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        //if id duplicated
        if (!userService.addUser(user)){
            return ResponseEntity.status(400).body(new ApiResponse("user id already used"));
        }


        return ResponseEntity.ok(new ApiResponse("user added successfully"));
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User user, Errors errors){

        //checking the user id
        if (!userService.updateUser(id, user)){
            return ResponseEntity.status(404).body(new ApiResponse("user: "+id+" not found"));
        }


        //validate the user object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        userService.updateUser(id, user);
        return ResponseEntity.ok(new ApiResponse("user: "+ id +" have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        if (!userService.deleteUser(id)){
            return ResponseEntity.status(404).body(new ApiResponse("user: "+id+" not found"));
        }

        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("user: "+ id +" have been deleted successfully"));
    }


    @PutMapping("/buy")
    public ResponseEntity<?> buyProduct(@RequestParam String userId, @RequestParam String productId, @RequestParam String merchantId) {
        String result = userService.buyItem(userId, productId, merchantId);
        if (result.equalsIgnoreCase("Purchase successful"))
            return ResponseEntity.ok(result);
        else
            return ResponseEntity.badRequest().body(result);
    }




    @PutMapping("/applyDiscount")
    public ResponseEntity<?> applyDiscount(@RequestParam String productId, @RequestParam String promoCode){
        boolean applyStatus = userService.applyDiscount(productId, promoCode);

        if (!applyStatus){
            return ResponseEntity.status(400).body(new ApiResponse("error in discount applying"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("discount applied"));
    }

    //loyalty points
    @PutMapping("/applyLoyaltyDiscount")
    public ResponseEntity<?> applyLoyaltyDiscount(@RequestParam String userId, @RequestParam String productId) {
        String result = userService.applyLoyaltyDiscount(userId, productId);
        if (result.equals("Loyalty discount applied successfully")) {
            return ResponseEntity.ok(new ApiResponse(result));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse(result));
        }
    }
}
