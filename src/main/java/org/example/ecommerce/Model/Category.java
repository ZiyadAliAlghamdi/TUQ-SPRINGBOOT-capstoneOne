package org.example.ecommerce.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Category {

    @NotEmpty(message = "CATEGORY MODEL(ID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "CATEGORY MODEL(ID contain digits only)")
    private String id;

    @NotEmpty(message = "CATEGORY MODEL(NAME cannot be empty)")
    @Size(min = 3, message = "CATEGORY MODEL(NAME have to be more than 3 length long)")
    private String name;
}
