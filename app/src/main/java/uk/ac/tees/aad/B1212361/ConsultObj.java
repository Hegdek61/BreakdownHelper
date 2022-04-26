package uk.ac.tees.aad.B1212361;

public class ConsultObj {

    String dept;

    String message;

    public ConsultObj(String dept, String message) {
        this.dept = dept;
        this.message = message;
    }

    public ConsultObj() {
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
