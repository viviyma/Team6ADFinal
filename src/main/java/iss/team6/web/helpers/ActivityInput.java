package iss.team6.web.helpers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActivityInput {
    private String description;
    private Integer points;
    private TrashType trashType;
}
