package org.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {

    @NotEmpty(message = "MERCHANT MODEL(ID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "MERCHANT MODEL(ID contain digits only)")
    private String id;

    @NotEmpty(message = "MERCHANT MODEL(NAME cannot be empty)")
    @Size(min = 3, message = "MERCHANT MODEL(NAME have to be more than 3 length long)")
    private String name;
}
