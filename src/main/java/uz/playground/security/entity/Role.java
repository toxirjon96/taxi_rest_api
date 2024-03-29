package uz.playground.security.entity;
import org.hibernate.annotations.NaturalId;
import uz.playground.security.constant.RoleName;
import uz.playground.security.constant.TableName;
import uz.playground.security.entity.audit.UserAudit;
import javax.persistence.*;
@Entity
@Table(name = TableName.ROLE)
public class Role extends UserAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_role_sequence_db")
    private Long id;
    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;
    public Role(RoleName name) {
        this.name = name;
    }
    public Role(){
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public RoleName getName() {
        return name;
    }
    public void setName(RoleName name) {
        this.name = name;
    }
}
