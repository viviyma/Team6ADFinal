package iss.team6.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import iss.team6.web.models.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer>{

    Team findByTeamName(String teamName);
}
