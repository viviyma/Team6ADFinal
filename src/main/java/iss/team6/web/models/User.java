package iss.team6.web.models;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String password;
    private String email;
    private String occupation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teamId")
    private Team team;
    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
    private Collection<Activity> activities = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(String name, String password, String email, String occupation) {
    	super();
        this.name = name;
        this.password = password;
        this.email = email;
        this.occupation = occupation;
    }    
 
}
