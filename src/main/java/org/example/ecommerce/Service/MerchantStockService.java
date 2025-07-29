package org.example.ecommerce.Service;

import lombok.AllArgsConstructor;
import org.example.ecommerce.Model.MerchantStock;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class MerchantStockService {

    //virtual database for merchantStocks's
    ArrayList<MerchantStock> merchantStocks = new ArrayList<>();

    //dependency injection
    private final ProductService productService;

    //CRUD
    
    public ArrayList<MerchantStock> getAllMerchantStock(){
        return merchantStocks;
    }

    public MerchantStock getSingleMerchantStock(String id){
        //searching for single merchantStocks using streams
        return merchantStocks.stream()
                .filter(merchantStock -> merchantStock.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);  //todo: handling in controller
    }


    public int addMerchantStock(MerchantStock merchantStock) {
        //check duplicate id
        for (MerchantStock ms : merchantStocks) {
            if (ms.getId().equalsIgnoreCase(merchantStock.getId())) {
                return -1; // duplicated
            }
        }

        //check product exists
        if (productService.getSingleProduct(merchantStock.getProductID()) == null) {
            return 0; // product not found
        }


        merchantStocks.add(merchantStock);
        return 1; // success
    }



    public Boolean updateMerchantStock(String id, MerchantStock merchantStock){


        for(MerchantStock merchantStockLoopVar: merchantStocks){
            if (merchantStockLoopVar.getId().equalsIgnoreCase(id)){
                merchantStocks.set(merchantStocks.indexOf(merchantStockLoopVar), merchantStock);
                return true;
            }
        }
        return false;
    }

    public boolean deleteMerchantStock(String id){
        return merchantStocks.removeIf(merchantStock -> merchantStock.getId().equalsIgnoreCase(id));
    }
    
    //END OF CRUD

    public MerchantStock findByProductAndMerchant(String productId, String merchantId) {
        return merchantStocks.stream()
                .filter(ms -> ms.getProductID().equalsIgnoreCase(productId) && ms.getMerchantID().equalsIgnoreCase(merchantId))
                .findFirst()
                .orElse(null);
    }

    public boolean addStock(String productId, String merchantId, int amount) {
        MerchantStock stock = findByProductAndMerchant(productId, merchantId);

        if (stock != null) {
            stock.setStock(stock.getStock() + amount);
            return true;
        }

        return false;
    }


    public MerchantStock getMerchantStockByProductIdAndMerchantId(String merchantId, String productId){
        return merchantStocks.stream()
                .filter(ms -> ms.getMerchantID().equalsIgnoreCase(merchantId) && ms.getProductID().equalsIgnoreCase(productId))
                .findFirst()
                .orElse(null);
    }

}
