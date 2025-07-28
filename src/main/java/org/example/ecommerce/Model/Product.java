package org.example.ecommerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "PRODUCT MODEL(ID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "PRODUCT MODEL(ID contain digits only)")
    private String id;

    @NotEmpty(message = "PRODUCT MODEL(NAME cannot be empty)")
    private String name;

    @NotNull(message = "PRODUCT MODEL(PRICE cannot be null)")
    @PositiveOrZero(message = "PRODUCT MODEL(PRICE cannot be negative)")    //could be an item with offer(free)
    private double price;

    @NotEmpty(message = "PRODUCT MODEL(categoryId cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "PRODUCT MODEL(categoryId contain digits only)")
    private String categoryId;


    @NotEmpty(message = "discountCode cannot be empty, you can insert null if there is no value")
    private String discountCode;

    @NotNull(message = "discountPercentage cannot be null, you can insert 0 if there is no percentage discount")
    private int discountPercentage;

    private int numberOfBuyers;
}
