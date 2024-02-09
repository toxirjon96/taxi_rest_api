package uz.playground.security.entity;

import uz.playground.security.constant.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.DRIVER_CAR)
public class DriverCar {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_driver_car_sequence_db")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_user_id_driver_car"))
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_character_id", foreignKey = @ForeignKey(name = "fk_car_character_id_driver_car"))
    private CarCharacteristics carCharacteristics;

    public DriverCar() {
    }

    public DriverCar(User user, CarCharacteristics carCharacteristics) {
        this.user = user;
        this.carCharacteristics = carCharacteristics;
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

    public CarCharacteristics getCarCharacteristics() {
        return carCharacteristics;
    }

    public void setCarCharacteristics(CarCharacteristics carCharacteristics) {
        this.carCharacteristics = carCharacteristics;
    }

    @Override
    public String toString() {
        return "DriverCar{" +
                "id=" + id +
                ", user=" + user +
                ", carCharacteristics=" + carCharacteristics +
                '}';
    }
}
