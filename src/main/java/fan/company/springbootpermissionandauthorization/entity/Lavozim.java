package fan.company.springbootpermissionandauthorization.entity;

import fan.company.springbootpermissionandauthorization.entity.template.AbstractEntity;
import lombok.*;
import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lavozim extends AbstractEntity {

    @Column(nullable = false)
    private String name; //ADMIN, USER VA BOSHQALAR

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.LAZY)
    private List<Huquq> huquqList;

    @Column(columnDefinition = "text")
    private String description;  // Role nima vazifa bajarishi to'g'risida izoh


}
