package org.example.ecommerce.Model;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {


    @NotEmpty(message = "USER MODEL(ID cannot be empty)")
    @Pattern(regexp = "^\\d+$", message = "USER MODEL(ID contain digits only)")
    private String id;

    @NotEmpty(message = "USER MODEL(USERNAME cannot be empty)")
    @Pattern(regexp = "^[A-Za-z]+$", message = "USER MODEL(USERNAME must contain chars only)")
    @Size(min = 5, message = "USER MODEL(USERNAME must be more than 5 length long)")
    private String username;

    @NotEmpty(message = "USER MODEL(PASSWORD cannot be empty)")
    @Size(min = 6, message = "USER MODEL(PASSWORD must be more than 6 length long)")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*?&#]{6,}$",message = "USER MODEL(password must be secure)")
    private String password;


    @NotEmpty(message = "USER MODEL(EMAIL cannot be null)")
    @Email(message = "USER MODEL(please check your EMAIL format)")
    private String email;


    @NotEmpty(message = "USER MODEL(role cannot be empty)")
    @Pattern(regexp = "^(Admin|Customer)$", message = "role must be: Admin|Customer")
    private String role;


    @NotNull(message = "USER MODEL(balance cannot be null)")
    @Positive(message = "USER MODEL(balance cannot be negative)")
    private double balance;

    private int loyaltyPoint;
}
