package fan.company.springbootpermissionandauthorization.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApiResult {

    private String message;

    private boolean success;

    private Object object;

    public ApiResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
