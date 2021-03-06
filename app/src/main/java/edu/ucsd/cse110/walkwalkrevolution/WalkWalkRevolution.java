package edu.ucsd.cse110.walkwalkrevolution;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessService;
import edu.ucsd.cse110.walkwalkrevolution.fitness.FitnessServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.fitness.StepSubject;
import edu.ucsd.cse110.walkwalkrevolution.fitness.Steps;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalService;
import edu.ucsd.cse110.walkwalkrevolution.proposal.ProposalServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.invitation.persistence.InvitationService;
import edu.ucsd.cse110.walkwalkrevolution.invitation.persistence.InvitationServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.BaseRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteSharedPreferenceDao;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.BaseUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserSharedPreferenceDao;

public class WalkWalkRevolution extends Application {
    private static FitnessServiceFactory fitnessServiceFactory;
    private static FitnessService fitnessService;

    private static Context context;

    private static BaseRouteDao routeDao;
    private static BaseUserDao userDao;

    private static RouteService routeService;
    private static RouteServiceFactory routeServiceFactory;
    private static ProposalService proposalService;
    private static ProposalServiceFactory proposalServiceFactory;
    private static UserService userService;
    private static UserServiceFactory userServiceFactory;
    private static InvitationService invitationService;
    private static InvitationServiceFactory invitationServiceFactory;

    private static Steps steps = new Steps();
    private static StepSubject stepTracker;

    private static User user;
    private static GoogleSignInAccount account;

    private static GoogleSigninClientFactory gscf;
    private static GoogleSignInClient gsc;

    private static boolean hasPermissions = false;

    private static long timeOffset = 0;
    private static long walkOffset = 0;

    @Override
    public void onCreate() {
        super.onCreate();

        routeServiceFactory = new RouteServiceFactory();
        userServiceFactory = new UserServiceFactory();
        proposalServiceFactory = new ProposalServiceFactory();
        invitationServiceFactory = new InvitationServiceFactory();
        WalkWalkRevolution.context = getApplicationContext();
        fitnessServiceFactory = new FitnessServiceFactory();
        routeDao = new RouteSharedPreferenceDao();
        userDao = new UserSharedPreferenceDao();
        user = userDao.getUser(UserSharedPreferenceDao.USER_ID);
        gscf = new GoogleSigninClientFactory();
        gsc = gscf.getGoogleSigninClient();
    }

    public static FitnessServiceFactory getFitnessServiceFactory() {
        return fitnessServiceFactory;
    }

    public static void setFitnessServiceFactory(FitnessServiceFactory fsf){
        WalkWalkRevolution.fitnessServiceFactory = fsf;
    }

    public static void setGoogleSignInClientFactory(GoogleSigninClientFactory gscf){
        WalkWalkRevolution.gscf = gscf;
    }

    public static GoogleSignInClient getGoogleSignInClient(){
        return WalkWalkRevolution.gsc;
    }

    public static Context getContext() {
        return context;
    }

    public static BaseRouteDao getRouteDao() {
        return routeDao;
    }

    public static void setRouteDao(BaseRouteDao rD) {
        routeDao = rD;
    }

    public static BaseUserDao getUserDao() {
        return userDao;
    }

    public static void setUserDao(BaseUserDao uD) {
        userDao = uD;
    }

    public static Steps getSteps(){
        return steps;
    }

    public static StepSubject getStepSubject(){
        return stepTracker;
    }

    public static void setStepSubject(StepSubject newST){
        WalkWalkRevolution.stepTracker = newST;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        WalkWalkRevolution.user = user;
    }

    public static void setFitnessService(FitnessService fitnessService){
        WalkWalkRevolution.fitnessService = fitnessService;
    }

    public static FitnessService getFitnessService(){
        return fitnessService;
    }

    public static void setHasPermissions(){
        hasPermissions = true;
    }
    public static boolean getHasPermissions(){
        return hasPermissions;
    }

    //Used for Mocking
    public static long getWalkOffset() {
        return walkOffset;
    }
    public static void setWalkOffset(long offset) {
        WalkWalkRevolution.walkOffset += offset;
    }
    public static long getTimeOffset() {
        return timeOffset;
    }
    public static void setTimeOffset(long offset) {
        WalkWalkRevolution.timeOffset += offset;
    }
    public static LocalDateTime getTime(){
        return toLDT(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() + timeOffset);
    }
    private static LocalDateTime toLDT(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }

    public static void setGoogleSignInAccount(GoogleSignInAccount account){
        WalkWalkRevolution.account = account;
    }

    public static GoogleSignInAccount getGoogleSignInAccount(){
        return account;
    }

    public static void setRouteServiceFactory(RouteServiceFactory rsf){
        WalkWalkRevolution.routeServiceFactory = rsf;
    }

    public static RouteServiceFactory getRouteServiceFactory(){
        return routeServiceFactory;
    }

    public static void createRouteService(){
        WalkWalkRevolution.routeService = WalkWalkRevolution.routeServiceFactory.createRouteService();
    }

    public static RouteService getRouteService(){
        return WalkWalkRevolution.routeService;
    }

    public static void setProposalServiceFactory(ProposalServiceFactory psf) {
        WalkWalkRevolution.proposalServiceFactory = psf;
    }

    public static void createProposalService(){
        WalkWalkRevolution.proposalService = WalkWalkRevolution.proposalServiceFactory.createProposalService();
    }

    public static ProposalService getProposalService(){
        return WalkWalkRevolution.proposalService;
    }


    public static void setUserServiceFactory(UserServiceFactory usf){
        WalkWalkRevolution.userServiceFactory = usf;
    }

    public static UserServiceFactory getUserServiceFactory(){
        return userServiceFactory;
    }

    public static void createUserService(){
        WalkWalkRevolution.userService = WalkWalkRevolution.userServiceFactory.createUserService();
    }

    public static UserService getUserService(){
        return WalkWalkRevolution.userService;
    }

    public static void setInvitationServiceFactory(InvitationServiceFactory isf){
        WalkWalkRevolution.invitationServiceFactory = isf;
    }

    public static InvitationServiceFactory getInvitationServiceFactory(){
        return invitationServiceFactory;
    }

    public static void createInvitationService() {
        WalkWalkRevolution.invitationService = invitationServiceFactory.createInvitationService();
    }

    public static InvitationService getInvitationService() {
        return invitationService;
    }

    public static void subscribeToNotificationsTopic(String topic) {
        FirebaseMessaging.getInstance().subscribeToTopic(topic)
                .addOnCompleteListener(task -> {
                            String msg = "Subscribed to notifications";
                            if (!task.isSuccessful()) {
                                msg = "Subscribe to notifications failed";
                            }
                            Log.d("Notification", msg);
                            Toast.makeText(WalkWalkRevolution.getContext(), msg, Toast.LENGTH_LONG).show();
                        }
                );
    }

}
