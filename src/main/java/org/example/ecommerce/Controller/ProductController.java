package org.example.ecommerce.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.ecommerce.Api.ApiResponse;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;



    @GetMapping("/get")
    public ResponseEntity<?> getAllProducts(){
        if (productService.getAllProducts().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("Products list is empty"));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleProduct(@PathVariable String id){
        if (productService.getSingleProduct(id) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("product: "+id+" not found"));
        }
        return ResponseEntity.ok(productService.getSingleProduct(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors){

        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        Boolean result = productService.addProduct(product);

        if (result == null) {   //result will return null if there is no categoryId
            return ResponseEntity.status(404).body(new ApiResponse("invalid category id"));
        }

        if (!result){   //result will return false if the id duplicated
            return ResponseEntity.status(400).body(new ApiResponse("duplicated product id"));
        }


        return ResponseEntity.ok(new ApiResponse("product added successfully"));
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @Valid @RequestBody Product product, Errors errors){

        //checking the product id
        if (!productService.updateProduct(id, product)){
            return ResponseEntity.status(404).body(new ApiResponse("product: "+id+" not found"));
        }


        //validate the product object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        productService.updateProduct(id, product);
        return ResponseEntity.ok(new ApiResponse("product: "+ id +" have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if (!productService.deleteProduct(id)){
            return ResponseEntity.status(404).body(new ApiResponse("product: "+id+" not found"));
        }

        productService.deleteProduct(id);
        return ResponseEntity.ok(new ApiResponse("product: "+ id +" have been deleted successfully"));
    }


    //getByRegion endpoints

    @GetMapping("/get/singleProductByRegion/{id}/{region}")
    public ResponseEntity<?> getProductByRegion(@PathVariable String id, @PathVariable String region){
        if (productService.getProductByRegion(id,region) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("product(By region): "+id+" not found"));
        }
        return ResponseEntity.ok(productService.getProductByRegion(id, region));
    }

    @GetMapping("/get/allProductsByRegion/{region}")
    public ResponseEntity<?> getAllProductsByRegion(@PathVariable String region){
        if (productService.getAllProductsByRegion(region).isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("Products list (By region) is empty"));
        }
        return ResponseEntity.ok(productService.getAllProductsByRegion(region));
    }

    @GetMapping("/get/top10Sold")
    public ResponseEntity<?> getTop10SoldItems(){
        if (productService.getTop10SoldItems().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("No top 10 sold items found"));
        }
        return ResponseEntity.ok(productService.getTop10SoldItems());
    }

}
