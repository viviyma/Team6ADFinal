package iss.team6.web.models;

import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import iss.team6.web.helpers.TrashType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer activityId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;
    private String description;
    private Integer points;
    @Enumerated(EnumType.STRING)
    private TrashType trashType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    public Activity(String description, Integer points, TrashType trashType, User user) {
        this.dateTime = Date.valueOf(LocalDate.now());
        this.description = description;
        this.points = points;
        this.trashType = trashType;
        this.user = user;
    }

}
