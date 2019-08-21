package models;

/**
 * Created by prasadsawant on 11/18/16.
 */

public class GeneralInformation {

    private String sunrise;
    private String sunset;
    private String city;

    public GeneralInformation(String sunrise, String sunset, String city) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

}
