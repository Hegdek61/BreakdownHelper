package uk.ac.tees.aad.B1212361;

public class AppUsersDetails {

    String name;

    String mobile;

    public AppUsersDetails(String name, String mobile) {
        this.name = name;
        this.mobile = mobile;
    }

    public AppUsersDetails() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
