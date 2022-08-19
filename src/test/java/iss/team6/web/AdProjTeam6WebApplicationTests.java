package iss.team6.web;

import java.sql.Date;
import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import iss.team6.web.helpers.TrashType;
import iss.team6.web.helpers.UserStatistics;
import iss.team6.web.models.Activity;
import iss.team6.web.models.Team;
import iss.team6.web.models.User;
import iss.team6.web.services.ActivityService;
import iss.team6.web.services.TeamService;
import iss.team6.web.services.UserService;

//@SpringBootTest
class AdProjTeam6WebApplicationTests {

	@Autowired
	UserService uService;

	@Autowired
	ActivityService aService;

	@Autowired
	TeamService tService;


	//Basic User CRUD Testing-----------------------------------------------------------------------------------------
	@Test
	@Order(1)
	public void testCreateUser() {
		User u = new User("test", "icle", "test@icle.two", "smut");
		uService.createUser(u);
		Assertions.assertThat(uService.findUserByUserName("test")).isNotNull();
	}

	@Test
	@Order(2)
	public void testUpdateUser() {
		User v = new User("Ve", "geta");
		uService.createUser(v);
		v.setEmail("dragon@ballZ.com");
		uService.updateUser(v);
		Assertions.assertThat(uService.findUserByUserName("Ve").getEmail()).isNotEmpty();
	}

	@Test
	@Order(3)
	public void testDeleteUser() {
		User f = new User("Fy", "namo");
		uService.createUser(f);
		Activity b = new Activity("Added picture of trash", 10, TrashType.METAL, uService.findUserByUserName("Fy"));
		aService.createActivity(b);
		uService.deleteUser(f);
		Assertions.assertThat(uService.findUserByUserName(f.getName())).isNull();
	}

	@Test
	public void testFailedAuthentication() {
		User g = new User("Gy", "namo");
		uService.createUser(g);
		Assertions.assertThat(uService.authenticate("Gy", "namic")).isFalse();
	}

	@Test
	public void testApprovedAuthentication() {
		User w = new User("Dy", "namo");
		uService.createUser(w);
		Assertions.assertThat(uService.authenticate("Dy", "namo")).isTrue();
	}



	//Basic Activity CRUD Testing-----------------------------------------------------------------------------------------
	@Test
	public void testCreateActivity() {
		Activity a = new Activity();
		aService.createActivity(a);
		Assertions.assertThat(a.getActivityId()).isNotNull();
	}

	@Test
	public void testCreateActivityVersionTwo() {
		User aV2User = new User("Paper", "Machete");
		uService.createUser(aV2User);
		Activity aV2 = new Activity("Added new picture!", 10, TrashType.PAPER, uService.findUserByUserName("Paper"));
		aService.createActivity(aV2);
		Assertions.assertThat(aService.findActivitiesByUser(uService.findUserByUserName("Paper"))).isNotEmpty();
	}

	@Test
	public void testFindActivityByUsernameAndDate() {
		User bUser = new User("war", "greymon");
		uService.createUser(bUser);
		Activity b = new Activity("Added picture of trash", 10, TrashType.METAL, uService.findUserByUserName("war"));
		aService.createActivity(b);
		Activity c = new Activity("Added another picture of trash", 10, TrashType.PAPER, uService.findUserByUserName("war"));
		c.setDateTime(null);
		aService.createActivity(c);
		System.out.println(Date.valueOf(LocalDate.now()));
		Assertions.assertThat(aService.findActivitiesByUserAndDate(uService.findUserByUserName("war"), Date.valueOf(LocalDate.now())).size()).isEqualTo(1);
	}

	@Test
	public void testUpdateActivity() {
		User bUser = new User("metal", "greymon");
		uService.createUser(bUser);
		Activity b = new Activity("Added picture of trash", 10, TrashType.METAL, uService.findUserByUserName("metal"));
		aService.createActivity(b);
		b.setPoints(20);
		aService.updateActivity(b);
		Activity bChanged = aService.findActivitiesByUser(bUser).get(0);
		Assertions.assertThat(bChanged.getPoints()).isEqualTo(20);
	}

	@Test
	public void testDeleteActivity() {
		User cUser = new User("Delete", "This");
		uService.createUser(cUser);
		Activity c = new Activity("This should be deleted", 100, TrashType.GLASS, uService.findUserByUserName("Delete"));
		aService.createActivity(c);
		aService.deleteActivity(c);
		Assertions.assertThat(aService.findActivitiesByUser(cUser)).isEmpty();
	}


	@Test
	public void testRetrievingUserStatistics() {
		User t = new User("Yui", "Hirasawa");
		uService.createUser(t);
		Activity b = new Activity("Added picture of trash", 10, TrashType.METAL, uService.findUserByUserName("Yui"));
		aService.createActivity(b);
		Activity c = new Activity("Added a good picture!", 100, TrashType.GLASS, uService.findUserByUserName("Yui"));
		aService.createActivity(c);
		Activity d = new Activity("A decent picture", 15, TrashType.GLASS, uService.findUserByUserName("Yui"));
		aService.createActivity(d);
		Activity e = new Activity("This should be deleted", 100, TrashType.GLASS, null);
		aService.createActivity(e);

		UserStatistics userStats = uService.getUserStatistics(uService.findUserByUserName("Yui"));
		Assertions.assertThat(userStats.getUserName()).isEqualTo("Yui");
		Assertions.assertThat(userStats.getMetalTypeCount()).isEqualTo(1);
		Assertions.assertThat(userStats.getGlassTypeCount()).isEqualTo(2);
		Assertions.assertThat(userStats.getPoints()).isEqualTo(125);
	}

	//Basic Team CRUD Testing-----------------------------------------------------------------------------------------
	@Test
	public void testCreateTeam() {
		Team team = new Team();
		team.setTeamName("SA54Team6");
		tService.createTeam(team);
		Assertions.assertThat(tService.findTeamByName("SA54Team6")).isNotNull();
	}

	@Test
	public void testCreateTeamWithMembers() {
		User t = new User("Yui", "Hirasawa");
		User e = new User("Mio", "Akiyama");
		User a = new User("Ritsu", "Tainaka");
		User m = new User("Tsumugi", "Kotobuki");
		User s = new User("Azusa", "Nakano");
		uService.createUser(t);
		uService.createUser(e);
		uService.createUser(a);
		uService.createUser(m);
		uService.createUser(s);

		Team kOn = new Team("k-on");
		tService.createTeam(kOn);

		tService.addMember(kOn, t);
		tService.addMember(kOn, e);
		tService.addMember(kOn, a);
		tService.addMember(kOn, m);
		tService.addMember(kOn, s);

		for (User u : tService.findTeamByName("k-on").getMembers()) {
			System.out.println(u.getName());
		}
		Assertions.assertThat(tService.findTeamByName("k-on").getMembers().size()).isEqualTo(5);

	}

	@Test
	public void testDeleteTeam() {
		User t = new User("Yui", "Hirasawa");
		User e = new User("Mio", "Akiyama");
		User a = new User("Ritsu", "Tainaka");
		User m = new User("Tsumugi", "Kotobuki");
		User s = new User("Azusa", "Nakano");
		uService.createUser(t);
		uService.createUser(e);
		uService.createUser(a);
		uService.createUser(m);
		uService.createUser(s);

		Team kOn = new Team("k-on");
		tService.createTeam(kOn);

		tService.addMember(kOn, t);
		tService.addMember(kOn, e);
		tService.addMember(kOn, a);
		tService.addMember(kOn, m);
		tService.addMember(kOn, s);

		tService.deleteTeam(tService.findTeamByName("k-on"));
		Assertions.assertThat(tService.findTeamByName("k-on")).isNull();
	}

}
