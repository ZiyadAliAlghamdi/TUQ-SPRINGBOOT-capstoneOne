package org.example.ecommerce.Service;

import org.example.ecommerce.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    //virtual database for categories's
    ArrayList<Category> categories = new ArrayList<>();


    //CRUD
    
    public ArrayList<Category> getAllCategory(){
        return categories;
    }

    public Category getSingleCategory(String id){
        //searching for single categories using streams
        return categories.stream()
                .filter(category -> category.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);  //todo: handling in controller
    }


    public boolean addCategory(Category category){

        for (Category categoryLoopVar : categories){
            if (categoryLoopVar.getId().equalsIgnoreCase(category.getId())){
                return false;
            }
        }

        categories.add(category);
        return true;
    }


    public boolean updateCategory(String id, Category category){
        for(Category categoryLoopVar: categories){
            if (categoryLoopVar.getId().equalsIgnoreCase(id)){
                categories.set(categories.indexOf(categoryLoopVar), category);
                return true;
            }
        }
        return false;
    }

    public boolean deleteCategory(String id){
        return categories.removeIf(category -> category.getId().equalsIgnoreCase(id));
    }
    
    //END OF CRUD
    
    
    

}
