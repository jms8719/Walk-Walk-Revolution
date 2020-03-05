package edu.ucsd.cse110.walkwalkrevolution.user.persistence;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.route.Routes;
import edu.ucsd.cse110.walkwalkrevolution.team.Team;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface UserService {

    void addUser(User user);
    void refresh();
    void getTeam(Team team, User user);
    void getTeamRoutes(Routes r, User user);

}
