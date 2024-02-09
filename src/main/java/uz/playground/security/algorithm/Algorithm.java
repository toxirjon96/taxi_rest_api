package uz.playground.security.algorithm;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.playground.security.dto.DistrictDto;
import uz.playground.security.dto.RegionDto;
import uz.playground.security.exception.GlobalExceptionHandler;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Algorithm {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public Date convertStringToDate(String date){
        try {
            return Date.valueOf(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }catch (Exception e){
            logger.debug(e.getMessage());
            return null;
        }
    }
    public Long getIntegerFromDoubleString(String number){
        try {
            double n = Double.parseDouble(number);
            return (long) n;
        }catch (Exception e){
            logger.debug(e.getMessage());
            return null;
        }
    }
    public LocalTime converTime(String time){
        try {
            LocalTime localTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
            return localTime;
        }catch (Exception e){
            logger.debug(e.getMessage());
            return null;
        }
    }
    public RegionDto[] readRegionFromJsonFile(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            InputStream in = getClass().getResourceAsStream("/json/SP_REGION.json");
            RegionDto [] obj = objectMapper.readValue(in, RegionDto[].class);
            return obj;
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }
    public DistrictDto[] readDistrictFromJsonFile(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            InputStream in = getClass().getResourceAsStream("/json/SP_DISTRICT.json");
            DistrictDto[] obj = objectMapper.readValue(in, DistrictDto[].class);
            return obj;
        } catch (IOException e) {
            logger.debug(e.getMessage());
            return null;
        }
    }
}
