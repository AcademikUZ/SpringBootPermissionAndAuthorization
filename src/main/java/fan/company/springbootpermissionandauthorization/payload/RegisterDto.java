package fan.company.springbootpermissionandauthorization.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterDto {

    @NotNull(message = "FullName bo'sh bo'lmasin!")
    private String fullName;

    @NotNull(message = "Username bo'sh bo'lmasin!")
    private String username;

    @NotNull(message = "Password bo'sh bo'lmasin!")
    private String password;

    @NotNull(message = "PrePassword bo'sh bo'lmasin!")
    private String prePassword;

}
