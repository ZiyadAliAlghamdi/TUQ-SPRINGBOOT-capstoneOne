package org.example.ecommerce.Service;

import lombok.AllArgsConstructor;
import org.example.ecommerce.Model.Merchant;
import org.example.ecommerce.Model.MerchantStock;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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


    public boolean assignDiscount(String id,String code, int percentage){
        Product product = productService.getSingleProduct(id);

        if (product == null){
            return false;
        }

        product.setDiscountCode(code);
        product.setDiscountPercentage(percentage);
        return true;
    }



    public String b2b(String firstMerchantId, String secondMerchantId, int stockAmount){ //first merchant sell to second
        MerchantStock m1 = merchantStockService.getMerchantStockByMerchantId(firstMerchantId);

        if (m1.getStock() < stockAmount){
            return "first merchant stock is low";
        }

        if (stockAmount > 100){
            return "stock amount must be at least 100";
        }

        MerchantStock m2 = merchantStockService.getMerchantStockByMerchantId(secondMerchantId);


        m2.setStock(m2.getStock()+stockAmount);
        m1.setStock(m1.getStock()-stockAmount);
        return "b2b process completed";
    }


}
