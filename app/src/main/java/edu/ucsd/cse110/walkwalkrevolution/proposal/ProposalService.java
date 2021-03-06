package edu.ucsd.cse110.walkwalkrevolution.proposal;

import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.ProposeScreenActivity;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface ProposalService {

    void scheduleWalk(Route route, String teamId, String userId, String date);
    void addProposal(Route route, String teamId, String userId, Date date);
    void withdrawProposal(String teamId, ProposeScreenActivity act);
    //void editProposal(Route route, String teamId, String userId, boolean scheduled);
    void editProposal(Route route, String teamId, String userId, String date, ProposeScreenActivity act);
    //void withdrawProposal(String teamId, );
    void getProposalRoute(String teamId, ProposeScreenActivity act);
    String getResponse(String teamId, String userEmail);
   // Route getProposal(User user);
    //List<Route> getRoutes(User user);

}
