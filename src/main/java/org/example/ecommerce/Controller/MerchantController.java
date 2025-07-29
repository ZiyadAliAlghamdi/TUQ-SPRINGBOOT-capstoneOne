package org.example.ecommerce.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.ecommerce.Api.ApiResponse;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@AllArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchants(){
        if (merchantService.getAllMerchant().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("Merchants list is empty"));
        }
        return ResponseEntity.ok(merchantService.getAllMerchant());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleMerchant(@PathVariable String id){
        if (merchantService.getSingleMerchant(id) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("merchant: "+id+" not found"));
        }
        return ResponseEntity.ok(merchantService.getSingleMerchant(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors){


        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }


        if (!merchantService.addMerchant(merchant)){
            return ResponseEntity.status(400).body(new ApiResponse("duplicated merchant id"));
        }

        return ResponseEntity.ok(new ApiResponse("merchant added successfully"));
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @Valid @RequestBody Merchant merchant, Errors errors){

        //checking the merchant id
        if (!merchantService.updateMerchant(id, merchant)){
            return ResponseEntity.status(404).body(new ApiResponse("merchant: "+id+" not found"));
        }


        //validate the merchant object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        merchantService.updateMerchant(id, merchant);
        return ResponseEntity.ok(new ApiResponse("merchant: "+ id +" have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        if (!merchantService.deleteMerchant(id)){
            return ResponseEntity.status(404).body(new ApiResponse("merchant: "+id+" not found"));
        }

        merchantService.deleteMerchant(id);
        return ResponseEntity.ok(new ApiResponse("merchant: "+ id +" have been deleted successfully"));
    }

    @PutMapping("/assignDiscount/{id}")
    public ResponseEntity<?> assignDiscount(@PathVariable String id, @RequestParam String promoCode, @RequestParam int percentage){
        boolean assign = merchantService.assignDiscount(id,promoCode,percentage);

        if (!assign){
            return ResponseEntity.status(400).body(new ApiResponse("an error occurs in assigning discount"));
        }
        return ResponseEntity.ok(new ApiResponse("Discount assigned"));
    }


    //B2B (merchant buy from other)
    @PutMapping("/b2b")
    public ResponseEntity<?> b2b(@RequestParam String firstMerchantId, @RequestParam String secondMerchantId, @RequestParam int stockAmount){
        String b2bResult =merchantService.b2b(firstMerchantId,secondMerchantId,stockAmount);
        if (b2bResult.equalsIgnoreCase("b2b process completed")){
            return ResponseEntity.ok(b2bResult);
        }
        return ResponseEntity.status(400).body(b2bResult);
    }

}
