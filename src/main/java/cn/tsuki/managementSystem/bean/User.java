package cn.tsuki.managementSystem.bean;

/**
 * Created by tsuki on 2017/4/21.
 */
public class User {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private int age;
    private String address;
    private String createdTime;
    private String locked;

    public User() {
        super();
    }

    public User(int id, String username, String password, String fullname,
                int age, String address, String createdTime, String locked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.age = age;
        this.address = address;
        this.createdTime = createdTime;
        this.locked = locked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }
}
