package uz.playground.security.entity;
import org.hibernate.annotations.NaturalId;
import uz.playground.security.constant.Lang;
import uz.playground.security.constant.TableName;
import uz.playground.security.entity.audit.DateAudit;
import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name = TableName.USER)
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_user_sequence_db")
    private Long id;
    @Column(name = "surname", length = 20)
    private String surname;
    @Column(name = "name", length = 20)
    private String name;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "patronym", length = 20)
    private String patronym;
    @Column(name = "status", length = 3)
    private String status;
    @NaturalId
    @Column(name = "phoneNumber", length = 14, unique = true)
    private String phoneNumber;
    @Column(name = "image")
    private byte [] image;
    @Column(name = "email", length = 30)
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_roles_db",
            joinColumns = @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id_user")),
            inverseJoinColumns = @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_id_user")))
    private Set<Role> roles = new HashSet<>();
    public User(){
    }

    public User(String surname, String name,
                 String patronym, Date dateOfBirth,
                String status, String phoneNumber,
                byte[] image, String email, String password,
                Lang lang) {
        this.surname = surname;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.patronym = patronym;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.email = email;
        this.password = password;
        this.lang = lang;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPatronym() {
        return patronym;
    }

    public void setPatronym(String patronym) {
        this.patronym = patronym;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte [] image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(Lang lang) {
        this.lang = lang;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", patronym='" + patronym + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", blob=" + image +
                ", email='" + email + '\'' +
                '}';
    }
}