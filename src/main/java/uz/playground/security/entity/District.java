package uz.playground.security.entity;
import uz.playground.security.constant.TableName;
import javax.persistence.*;

@Entity
@Table(name = TableName.DISTRICT)
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_distict_sequence_db")
    private Long id;

    @Column(name = "district_id")
    private Long districtId;

    @Column(name = "name_cyrillic")
    private String nameCyrillic;

    @Column(name = "name_latin")
    private String nameLatin;

    @Column(name = "name_russian")
    private String nameRussian;

    @Column(name = "region_id")
    private Long regionId;

    public District() {
    }

    public District(Long districtId, String nameCyrillic, String nameLatin, String nameRussian, Long regionId) {
        this.districtId = districtId;
        this.nameCyrillic = nameCyrillic;
        this.nameLatin = nameLatin;
        this.nameRussian = nameRussian;
        this.regionId = regionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public String getNameCyrillic() {
        return nameCyrillic;
    }

    public void setNameCyrillic(String nameCyrillic) {
        this.nameCyrillic = nameCyrillic;
    }

    public String getNameLatin() {
        return nameLatin;
    }

    public void setNameLatin(String nameLatin) {
        this.nameLatin = nameLatin;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", districtId=" + districtId +
                ", nameCyrillic='" + nameCyrillic + '\'' +
                ", nameLatin='" + nameLatin + '\'' +
                ", nameRussian='" + nameRussian + '\'' +
                ", regionId=" + regionId +
                '}';
    }
}
