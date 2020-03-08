package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.Routes;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface RouteService {

    void addRoute(Route route);
    void updateRoute(Route route);
    void getRoutes(Routes rList, User user);

}
