package edu.ucsd.cse110.walkwalkrevolution.proposal;

import java.util.List;

import edu.ucsd.cse110.walkwalkrevolution.ProposeScreenActivity;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.user.User;

public interface ProposalService {

    void addProposal(String routeId, String userId);
    void withdrawProposal();
    void getProposalRoute(String teamId, ProposeScreenActivity act);
   // Route getProposal(User user);
    //List<Route> getRoutes(User user);

}
