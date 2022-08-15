package iss.team6.web.services;

import java.util.HashMap;

import iss.team6.web.helpers.UserStatistics;
import iss.team6.web.models.Team;
import iss.team6.web.models.User;

public interface TeamService {
    
    void createTeam(Team team);

    Team findTeamByName(String teamName);

    void updateTeam(Team team);

    void deleteTeam(Team team);

    void addMember(Team team, User user);

    void removeMember(User user);

    HashMap<String, UserStatistics> getTeamStatistics(Team team);

}
