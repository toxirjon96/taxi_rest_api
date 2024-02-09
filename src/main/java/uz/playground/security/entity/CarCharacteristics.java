package uz.playground.security.entity;

import org.hibernate.validator.constraints.UniqueElements;
import uz.playground.security.constant.TableName;

import javax.persistence.*;

@Entity
@Table(name = TableName.CAR_CHARACTER)
public class CarCharacteristics {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_car_character_sequence_db")
    private Long id;

    @Column(name = "car_name")
    private String carName;

    @Column(name = "car_color")
    private String carColor;

    @Column(name = "person_count")
    private Integer personCount;

    @Column(name = "car_number", unique = true)
    private String carNumber;

    public CarCharacteristics() {
    }

    public CarCharacteristics(String carName, String carColor, Integer personCount, String carNumber) {
        this.carName = carName;
        this.carColor = carColor;
        this.personCount = personCount;
        this.carNumber = carNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    @Override
    public String toString() {
        return "CarCharacteristics{" +
                "id=" + id +
                ", carName='" + carName + '\'' +
                ", carColor='" + carColor + '\'' +
                ", personCount=" + personCount +
                ", carNumber='" + carNumber + '\'' +
                '}';
    }
}
