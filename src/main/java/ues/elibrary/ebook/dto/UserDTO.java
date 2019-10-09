package ues.elibrary.ebook.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import ues.elibrary.ebook.entity.Role;
import ues.elibrary.ebook.entity.User;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private Role role;

    public UserDTO() {

    }

    public UserDTO(Long id, String firstname, String lastname, String username, String password, Role role) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public UserDTO(User user) {
        this(user.getId(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(),
                user.getRole());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}