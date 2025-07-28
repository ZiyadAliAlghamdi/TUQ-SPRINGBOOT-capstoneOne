package org.example.ecommerce.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "MerchantStock MODEL(ID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "MerchantStock MODEL(ID contain digits only)")
    private String id;

    @NotEmpty(message = "MerchantStock MODEL(productID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "MerchantStock MODEL(productID contain digits only)")
    private String productID;


    @NotEmpty(message = "MerchantStock MODEL(merchantID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "MerchantStock MODEL(merchantID contain digits only)")
    private String merchantID;

    @NotNull(message = "stock cannot be empty")
    @Min(value = 10, message = "stock have to be 10 or more")
    private int stock;
}
