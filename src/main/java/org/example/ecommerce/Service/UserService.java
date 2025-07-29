package org.example.ecommerce.Service;

import lombok.AllArgsConstructor;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.example.ecommerce.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserService {

    //virtual database for users's
    ArrayList<User> users = new ArrayList<>();

    private final ProductService productService;
    private final MerchantStockService merchantStockService;


    //CRUD
    
    public ArrayList<User> getAllUser(){
        return users;
    }

    public User getSingleUser(String id){
        //searching for single users using streams
        return users.stream()
                .filter(user -> user.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }


    public boolean addUser(User user){
        //for each to check duplicated id's
        for (User userLoopVar: users){
            if (userLoopVar.getId().equalsIgnoreCase(user.getId())){
                return false;
            }
        }

        users.add(user);
        return true;
    }


    public boolean updateUser(String id, User user){
        for(User userLoopVar: users){
            if (userLoopVar.getId().equalsIgnoreCase(id)){
                users.set(users.indexOf(userLoopVar), user);
                return true;
            }
        }
        return false;
    }

    public boolean deleteUser(String id){
        return users.removeIf(user -> user.getId().equalsIgnoreCase(id));
    }
    
    //END OF CRUD
    


    public String buyItem(String userId, String productId, String merchantId){

        User user = users.stream()
                .filter(u -> u.getId().equalsIgnoreCase(userId))
                .findFirst()
                .orElse(null);

        Product product = productService.getById(productId);

        MerchantStock stock = merchantStockService.findByProductAndMerchant(productId,merchantId);

        //checking id's
        if (user == null){
            return "USER ID invalid";
        }

        if (product == null){
            return "product ID invalid";
        }

        if (stock == null){
            return "stock ID invalid";
        }


        //checking stock
        if (stock.getStock() <= 0){
            return "out of stock";
        }

        //checking balance
        if (user.getBalance() < product.getPrice()){
            return "balance not enough";
        }

        //changing the stock
        stock.setStock(stock.getStock() - 1);

        //changing the balance
        user.setBalance(user.getBalance() - product.getPrice());

        //updating the number of buyers
        product.setNumberOfBuyers(product.getNumberOfBuyers() + 1);

        //add loyaltyPoints
        user.setLoyaltyPoint(user.getLoyaltyPoint()+1);

        return "Purchase successful";
    }

    //apply discount
    public boolean applyDiscount(String productId, String promoCode){
     Product product = productService.getSingleProduct(productId);
     int discount = (int) (((double) product.getDiscountPercentage() /100)*product.getPrice());

     if (product.getDiscountCode().equalsIgnoreCase(promoCode)){
         product.setPrice(product.getPrice()-discount);
         return true;
     }
     return false;
    }

    public String applyLoyaltyDiscount(String userId, String productId) {
        User user = getSingleUser(userId);
        Product product = productService.getById(productId);

        if (user == null) {
            return "User not found";
        }
        if (product == null) {
            return "Product not found";
        }

        int loyaltyPoints = user.getLoyaltyPoint();
        if (loyaltyPoints < 10) {
            return "Not enough loyalty points to apply discount";
        }

        //10 points = 10 units discount
        double discount = loyaltyPoints;
        double discountedPrice = product.getPrice() - discount;
        if (discountedPrice < 0) {
            discountedPrice = 0;
        }


        user.setLoyaltyPoint(0);


        return "Loyalty discount applied successfully. Final price: " + discountedPrice;
    }


}
