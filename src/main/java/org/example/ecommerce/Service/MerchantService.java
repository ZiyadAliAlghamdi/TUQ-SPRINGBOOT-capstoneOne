package org.example.ecommerce.Service;

import lombok.AllArgsConstructor;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MerchantService {

    //virtual database for merchants's
    ArrayList<Merchant> merchants = new ArrayList<>();

    private final ProductService productService;
    private final MerchantStockService merchantStockService;



    //CRUD
    
    public ArrayList<Merchant> getAllMerchant(){
        return merchants;
    }

    public Merchant getSingleMerchant(String id){
        //searching for single merchants using streams
        return merchants.stream()
                .filter(merchant -> merchant.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);  //todo: handling in controller
    }


    public boolean addMerchant(Merchant merchant){
        for (Merchant merchantLoopVar : merchants){
            if (merchantLoopVar.getId().equalsIgnoreCase(merchant.getId())){
                return false;
            }
        }

        merchants.add(merchant);
        return true;
    }

    //todo: replace it with steam if its possible
    public boolean updateMerchant(String id, Merchant merchant){
        for(Merchant merchantLoopVar: merchants){
            if (merchantLoopVar.getId().equalsIgnoreCase(id)){
                merchants.set(merchants.indexOf(merchantLoopVar), merchant);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchant(String id){
        return merchants.removeIf(merchant -> merchant.getId().equalsIgnoreCase(id));
    }
    
    //END OF CRUD


    public String generateDiscountCode(){
        String chars = "qwertyuiopasdfghjklzxcvbnm1234567890";
        Random rand = new Random();
        StringBuilder discountCode = new StringBuilder();

        int codeLength = 8;
        for (int i = 0; i < codeLength; i++) {
            int index = rand.nextInt(chars.length());
            discountCode.append(chars.charAt(index));
        }
        return String.valueOf(discountCode);
    }


    public boolean assignDiscount(String id, int percentage){
        Product product = productService.getSingleProduct(id);

        for(MerchantStock merchantStock:merchantStockService.merchantStocks){
            if (merchantStock.getProductID().equalsIgnoreCase(id)){
                product.setDiscountCode(generateDiscountCode());
                product.setDiscountPercentage(percentage);
                return true;
            }
        }

        return false;
    }



    public String b2b(String sellerMerchantId, String buyerMerchantId, String productId, int stockAmount){ //first merchant sell to second


        MerchantStock sellerMerchant = merchantStockService.getMerchantStockByProductIdAndMerchantId(sellerMerchantId,productId);

        if (sellerMerchant == null){
            return "product id is not correct";
        }



        if (sellerMerchant.getStock() < stockAmount){
            return "first merchant stock is low";
        }


        if (stockAmount < 100){
            return "stock amount must be at least 100";
        }



        MerchantStock buyerMerchant = merchantStockService.getMerchantStockByProductIdAndMerchantId(buyerMerchantId,productId);

        if (buyerMerchant == null) {
            buyerMerchant = new MerchantStock(UUID.randomUUID().toString(), productId, buyerMerchantId, 0);
            merchantStockService.merchantStocks.add(buyerMerchant);
        }

        buyerMerchant.setStock(buyerMerchant.getStock()+stockAmount);
        sellerMerchant.setStock(sellerMerchant.getStock()-stockAmount);
        return "b2b process completed";
    }


}
