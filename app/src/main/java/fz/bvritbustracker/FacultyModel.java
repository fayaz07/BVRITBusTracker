package fz.bvritbustracker;

public class FacultyModel {
    String name,dept,email,password,phone,gender,uid;

    public FacultyModel() {
    }

    public FacultyModel(String name, String dept, String email, String password, String phone, String gender, String uid) {
        this.name = name;
        this.dept = dept;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
