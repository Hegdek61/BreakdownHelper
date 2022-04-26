package uk.ac.tees.aad.B1212361;

public class EmergencyMessage {

    String message;
    double latitude;
    double longitude;

    public EmergencyMessage(String message, double latitude, double longitude) {
        this.message = message;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public EmergencyMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
