package fan.company.springbootpermissionandauthorization.payload;

import fan.company.springbootpermissionandauthorization.entity.template.AbstractEntity;
import lombok.*;


import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostDto extends AbstractEntity {

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String url;
}
