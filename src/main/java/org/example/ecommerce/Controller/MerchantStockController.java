package org.example.ecommerce.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.ecommerce.Api.ApiResponse;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Service.MerchantStockService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantStock")
@AllArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchantStocks(){
        if (merchantStockService.getAllMerchantStock().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("MerchantStocks list is empty"));
        }
        return ResponseEntity.ok(merchantStockService.getAllMerchantStock());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleMerchantStock(@PathVariable String id){
        if (merchantStockService.getSingleMerchantStock(id) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("merchantStock: "+id+" not found"));
        }
        return ResponseEntity.ok(merchantStockService.getSingleMerchantStock(id));
    }


    @GetMapping("/get/merchantId/{merchantId}")
    public ResponseEntity<?> getMerchantStockNyMerchantId(@PathVariable String merchantId){
        if (merchantStockService.getMerchantStockByMerchantId(merchantId) ==  null){
            return ResponseEntity.status(400).body(new ApiResponse("Stock not found"));
        }
        return ResponseEntity.ok(merchantStockService.getMerchantStockByMerchantId(merchantId));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        int merchantStockResult = merchantStockService.addMerchantStock(merchantStock);

        return switch (merchantStockResult) {
            case -1 -> ResponseEntity.status(400).body(new ApiResponse("duplicated MerchantStock id"));
            case 0 -> ResponseEntity.status(404).body(new ApiResponse("product not found"));
            case 1 -> ResponseEntity.ok(new ApiResponse("merchantStock added successfully"));
            default -> ResponseEntity.status(500).body(new ApiResponse("an error occurred"));
        };
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @Valid @RequestBody MerchantStock merchantStock, Errors errors){

        //checking the merchantStock id
        if (!merchantStockService.updateMerchantStock(id, merchantStock)){
            return ResponseEntity.status(404).body(new ApiResponse("merchantStock: "+id+" not found"));
        }


        //validate the merchantStock object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        merchantStockService.updateMerchantStock(id, merchantStock);
        return ResponseEntity.ok(new ApiResponse("merchantStock: "+ id +" have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        if (!merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(404).body(new ApiResponse("merchantStock: "+id+" not found"));
        }

        merchantStockService.deleteMerchantStock(id);
        return ResponseEntity.ok(new ApiResponse("merchantStock: "+ id +" have been deleted successfully"));
    }

    @PutMapping("/addStock")
    public ResponseEntity<?> addStock(@RequestParam String productId,@RequestParam String merchantId,@RequestParam int amount){
        if (!merchantStockService.addStock(productId, merchantId, amount)){
            return ResponseEntity.status(400).body(new ApiResponse("an error occors"));
        }
        return ResponseEntity.ok(new ApiResponse("stock added successfully"));
    }

}