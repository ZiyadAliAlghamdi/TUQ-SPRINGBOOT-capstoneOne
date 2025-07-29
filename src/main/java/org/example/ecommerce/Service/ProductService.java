package org.example.ecommerce.Service;

import lombok.AllArgsConstructor;
import org.example.ecommerce.Model.Category;
import org.example.ecommerce.Model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    //virtual database for products
    ArrayList<Product> products = new ArrayList<>();

    //dependency injection
    private final CategoryService categoryService;


    //CRUD

    public ArrayList<Product> getAllProducts(){
        return products;
    }

    public Product getSingleProduct(String id){
        //searching for single product using streams
        return products.stream()
                .filter(product -> id.equalsIgnoreCase(product.getId()))
                .findFirst()
                .orElse(null);  //todo: handling in controller
    }


    public Boolean addProduct(Product product) {

        // check duplicate id
        for (Product productLoopVar : products) {
            if (productLoopVar.getId().equalsIgnoreCase(product.getId())) {
                return false; // if id is duplicated
            }
        }

        // check categoryID
        for (Category category : categoryService.getAllCategory()) {
            if (category.getId().equalsIgnoreCase(product.getCategoryId())) {
                products.add(product);
                return true; // successfully added
            }
        }

        return null; // invalid category
    }


//todo: checking categoryID
    public boolean updateProduct(String id, Product product){
        for(Product productLoopVar: products){
            if (productLoopVar.getId().equalsIgnoreCase(id)){
                products.set(products.indexOf(productLoopVar), product);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(String id){
        return products.removeIf(product -> product.getId().equalsIgnoreCase(id));
    }

    public Product getById(String id){
        return products.stream()
                .filter(p-> p.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    //END OF CRUD


    //Get single Product By Region//

    //assuming default price in USD like amazon
    public Product getProductByRegion(String id,String region){
        Product originalProduct = getSingleProduct(id);


        //creating an inner object to make sure we don't conflict with the original one
        Product innerProduct = new Product(
                originalProduct.getId(),
                originalProduct.getName(),
                originalProduct.getPrice(),
                originalProduct.getCategoryId(),
                null,    //make discount code null as default
                0,
                originalProduct.getNumberOfBuyers()
        );

        double originalPrice = originalProduct.getPrice();


        if (region == null || region.isEmpty()){
            originalProduct.setPrice(originalProduct.getPrice());
            return originalProduct;
        }

        switch (region){
            case "SA":  //dollar to saudi riyal
                innerProduct.setPrice(originalPrice *3.75);
                break;
            case "JP":
                innerProduct.setPrice(originalPrice*147.85);
                break;
            case "UK":
                innerProduct.setPrice(originalPrice*0.74);
                break;
            default:
                innerProduct.setPrice(originalPrice);
        }
        return innerProduct;
    }

    //Get All Products By Region//

    //assuming default price in USD like amazon
    public ArrayList<Product> getAllProductsByRegion(String region){
        ArrayList<Product> regionProducts = new ArrayList<>();

        for (Product p : products) {
            Product converted = new Product(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getCategoryId(),
                    p.getDiscountCode(),
                    p.getDiscountPercentage(),
                    p.getNumberOfBuyers()
            );


            double basePrice = p.getPrice();

            switch (region) {
                case "SA":
                    converted.setPrice(basePrice * 3.75);
                    break;
                case "JP":
                    converted.setPrice(basePrice * 147.85);
                    break;
                case "UK":
                    converted.setPrice(basePrice * 0.74);
                    break;
                default:
                    converted.setPrice(basePrice);
                    break;
            }
            regionProducts.add(converted);
        }

        return regionProducts;
    }

    public ArrayList<Product> getTop10SoldItems() {
        return products.stream()
                .sorted((p1,p2) -> Integer.compare(p2.getNumberOfBuyers(), p1.getNumberOfBuyers()))
                .limit(10)
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
