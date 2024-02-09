package uz.playground.security.entity;
import uz.playground.security.constant.TableName;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = TableName.POST)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_post_sequence_db")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id_post"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_region", foreignKey = @ForeignKey(name = "fk_from_region"))
    private Region fromRegion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_district", foreignKey = @ForeignKey(name = "fk_from_district"))
    private District fromDistrict;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_region", foreignKey = @ForeignKey(name = "fk_to_region"))
    private Region toRegion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_district", foreignKey = @ForeignKey(name = "fk_to_district"))
    private District toDistrict;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_role_id"))
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_characteristic_id", foreignKey = @ForeignKey(name = "fk_car_characteristic_id"))
    private CarCharacteristics carCharacteristics;

    @Column(name = "date_from")
    private Date dateFrom;

    @Column(name = "date_to")
    private Date dateTo;

    @Column(name = "time_from")
    private LocalTime timeFrom;

    @Column(name = "time_to")
    private LocalTime timeTo;

    @Column(name = "person_count")
    private Integer personCount;

    @Column(name = "description", length = 400)
    private String description;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "min_price")
    private Double minPrice;

    @Column(name = "max_price")
    private Double maxPrice;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Post() {
    }

    public Post(User user, Region fromRegion,
                District fromDistrict, Region toRegion,
                District toDistrict, Role role,
                CarCharacteristics carCharacteristics,
                Date dateFrom, Date dateTo, LocalTime timeFrom,
                LocalTime timeTo, Integer personCount,
                String description, String contactNumber,
                Double minPrice, Double maxPrice) {
        this.user = user;
        this.fromRegion = fromRegion;
        this.fromDistrict = fromDistrict;
        this.toRegion = toRegion;
        this.toDistrict = toDistrict;
        this.role = role;
        this.carCharacteristics = carCharacteristics;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.personCount = personCount;
        this.description = description;
        this.contactNumber = contactNumber;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Region getFromRegion() {
        return fromRegion;
    }

    public void setFromRegion(Region fromRegion) {
        this.fromRegion = fromRegion;
    }

    public District getFromDistrict() {
        return fromDistrict;
    }

    public void setFromDistrict(District fromDistrict) {
        this.fromDistrict = fromDistrict;
    }

    public Region getToRegion() {
        return toRegion;
    }

    public void setToRegion(Region toRegion) {
        this.toRegion = toRegion;
    }

    public District getToDistrict() {
        return toDistrict;
    }

    public void setToDistrict(District toDistrict) {
        this.toDistrict = toDistrict;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public CarCharacteristics getCarCharacteristics() {
        return carCharacteristics;
    }

    public void setCarCharacteristics(CarCharacteristics carCharacteristics) {
        this.carCharacteristics = carCharacteristics;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
        this.timeTo = timeTo;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", user=" + user +
                ", fromRegion=" + fromRegion +
                ", fromDistrict=" + fromDistrict +
                ", toRegion=" + toRegion +
                ", toDistrict=" + toDistrict +
                ", role=" + role +
                ", carCharacteristics=" + carCharacteristics +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", timeFrom=" + timeFrom +
                ", timeTo=" + timeTo +
                ", personCount=" + personCount +
                ", description='" + description + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
