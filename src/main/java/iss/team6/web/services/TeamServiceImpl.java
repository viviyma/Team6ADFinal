package iss.team6.web.services;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import iss.team6.web.helpers.UserStatistics;
import iss.team6.web.models.Team;
import iss.team6.web.models.User;
import iss.team6.web.repositories.TeamRepository;
import iss.team6.web.repositories.UserRepository;

@Service
public class TeamServiceImpl implements TeamService{

    @Resource
    TeamRepository tRepo;

    @Resource
    UserRepository uRepo;

    @Resource
    UserService uService;

    @Override
    public void createTeam(Team team) {
        tRepo.saveAndFlush(team);
    }

    @Override
    public Team findTeamByName(String teamName) {
        return tRepo.findByTeamName(teamName);
    }

    @Override
    public void updateTeam(Team team) {
        tRepo.saveAndFlush(team);
    }

    @Override
    public void deleteTeam(Team team) {
        for (User u : team.getMembers()) {
            u.setTeam(null);
            uRepo.saveAndFlush(u);
        }
        tRepo.delete(team);
    }

    @Override
    public void addMember(Team team, User user) {
        user.setTeam(team);
        uRepo.saveAndFlush(user);
    }

    @Override
    public void removeMember(User user) {
        user.setTeam(null);
        uRepo.saveAndFlush(user);
    }

    @Override
    public HashMap<String, UserStatistics> getTeamStatistics(Team team) {
        // TODO JUnit Test this method
        HashMap<String, UserStatistics> teamStats = new HashMap<>();
        for (User u : team.getMembers()) {
            teamStats.put(u.getName(), uService.getUserStatistics(u));
        }
        return teamStats;
    }
    
}
