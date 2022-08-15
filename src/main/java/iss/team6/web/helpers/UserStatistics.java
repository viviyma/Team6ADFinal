package iss.team6.web.helpers;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserStatistics {
    
    private String userName;
    private Integer points;
    private Integer metalTypeCount;
    private Integer glassTypeCount;
    private Integer paperTypeCount;
    private Integer plasticTypeCount;

}
