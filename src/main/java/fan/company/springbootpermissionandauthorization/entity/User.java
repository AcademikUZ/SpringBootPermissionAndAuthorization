package fan.company.springbootpermissionandauthorization.entity;


import fan.company.springbootpermissionandauthorization.entity.template.AbstractEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity(name = "users")
public class User extends AbstractEntity implements UserDetails {

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Lavozim lavozim;

    private boolean accountNonExpired = true;

    private boolean accountNonLocked = true;

    private boolean credentialsNonExpired = true;

    private boolean enabled = true;

    public User(String fullName, String username, String password, Lavozim lavozim) {
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.lavozim = lavozim;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<Huquq> huquqList = this.lavozim.getHuquqList();

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        for (Huquq huquq : huquqList) {

            /**
             * Ikki hil usulda olish
             */
            // grantedAuthorityList.add((GrantedAuthority) () -> huquq.name());
            grantedAuthorityList.add(new SimpleGrantedAuthority(huquq.name()));
        }
        return grantedAuthorityList;
    }

}
