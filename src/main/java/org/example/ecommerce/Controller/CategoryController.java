package org.example.ecommerce.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.example.ecommerce.Api.ApiResponse;
import org.example.ecommerce.Model.Category;
import org.example.ecommerce.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllCategory(){
        if (categoryService.getAllCategory().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("Categorys list is empty"));
        }
        return ResponseEntity.ok(categoryService.getAllCategory());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleCategory(@PathVariable String id){
        if (categoryService.getSingleCategory(id) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("category: "+id+" not found"));
        }
        return ResponseEntity.ok(categoryService.getSingleCategory(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors errors){


        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        if (!categoryService.addCategory(category)){
            return ResponseEntity.status(400).body(new ApiResponse("duplicated category id"));
        }


        return ResponseEntity.ok(new ApiResponse("category added successfully"));
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable String id, @Valid @RequestBody Category category, Errors errors){

        //checking the category id
        if (!categoryService.updateCategory(id, category)){
            return ResponseEntity.status(404).body(new ApiResponse("category: "+id+" not found"));
        }


        //validate the category object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        categoryService.updateCategory(id, category);
        return ResponseEntity.ok(new ApiResponse("category: "+ id +" have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable String id){
        if (!categoryService.deleteCategory(id)){
            return ResponseEntity.status(404).body(new ApiResponse("category: "+id+" not found"));
        }

        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("category: "+ id +" have been deleted successfully"));
    }

}
