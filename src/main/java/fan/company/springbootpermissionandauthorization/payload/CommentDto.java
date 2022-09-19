package fan.company.springbootpermissionandauthorization.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    @NotNull
    private String text;

    @NotNull
    private Long postId;
}
