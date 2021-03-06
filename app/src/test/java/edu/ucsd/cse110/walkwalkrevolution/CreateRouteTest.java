package edu.ucsd.cse110.walkwalkrevolution;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.MockRouteDao;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteService;
import edu.ucsd.cse110.walkwalkrevolution.route.persistence.RouteServiceFactory;
import edu.ucsd.cse110.walkwalkrevolution.user.User;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.MockUserDao;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserService;
import edu.ucsd.cse110.walkwalkrevolution.user.persistence.UserServiceFactory;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.robolectric.shadows.ShadowLooper;


@RunWith(AndroidJUnit4.class)
public class CreateRouteTest {

    RouteService routeService;
    RouteServiceFactory routeServiceFactory;
    UserService userService;
    UserServiceFactory userServiceFactory;

    @Before
    public void setup(){
        routeService = Mockito.mock(RouteService.class);
        routeServiceFactory = Mockito.mock(RouteServiceFactory.class);
        userService = Mockito.mock(UserService.class);
        userServiceFactory = Mockito.mock(UserServiceFactory.class);

        when(userServiceFactory.createUserService())
                .thenReturn(userService);

        when(routeServiceFactory.createRouteService())
                .thenReturn(routeService);

        WalkWalkRevolution.setRouteServiceFactory(routeServiceFactory);
        WalkWalkRevolution.setUserServiceFactory(userServiceFactory);

        WalkWalkRevolution.createUserService();
        WalkWalkRevolution.createRouteService();

        WalkWalkRevolution.setRouteDao(new MockRouteDao());
        WalkWalkRevolution.setUserDao(new MockUserDao());
        WalkWalkRevolution.setUser(new User(1, 528*12, "", ""));
    }

    @Test
    public void routeWithTitleSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);

                routeTitle.setText("Route 1");

                save.performClick();

                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

                Route route = WalkWalkRevolution.getRouteDao().getRoute(1);
                assertNotNull(route);
                assertEquals(1, route.getId());
                assertEquals("Route 1", route.getTitle());

            });
        }
    }

    @Test
    public void routeWithNoTitleSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);
                save.performClick();
                assertEquals("Route Title is Required", routeTitle.getError().toString());
            });
        }
    }

    @Test
    public void routeWithTitleCancel(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button cancel = (Button) activity.findViewById(R.id.cancel_button);

                routeTitle.setText("Route 1");

                cancel.performClick();
                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
                assertEquals(true, activity.isFinishing());
            });
        }
    }

    @Test
    public void routeWithNoTitleCancel(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button cancel = (Button) activity.findViewById(R.id.cancel_button);
                cancel.performClick();
                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();
                assertEquals(true, activity.isFinishing());
            });
        }
    }

    @Test
    public void routeWithTitleAndTagSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);
                LinearLayout linearLayout = activity.findViewById(R.id.route_tags);
                RadioGroup tags = (RadioGroup) linearLayout.getChildAt(2);
                assertEquals(5, linearLayout.getChildCount());
                RadioButton selectRadio = (RadioButton) tags.getChildAt(0);
                assertEquals(2, tags.getChildCount());
                assertEquals("streets", selectRadio.getText());

                selectRadio.performClick();

                routeTitle.setText("Route 1");

                save.performClick();

                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

                Route route = WalkWalkRevolution.getRouteDao().getRoute(1);
                assertNotNull(route);
                assertEquals(1, route.getId());
                assertEquals("Route 1", route.getTitle());
                assertEquals("streets", route.getDescriptionTags());

            });
        }
    }

    @Test
    public void routeWithTitleAndTagsSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);
                LinearLayout linearLayout = activity.findViewById(R.id.route_tags);
                RadioGroup tags = (RadioGroup) linearLayout.getChildAt(2);
                assertEquals(5, linearLayout.getChildCount());
                RadioButton selectRadio = (RadioButton) tags.getChildAt(0);
                assertEquals(2, tags.getChildCount());
                assertEquals("streets", selectRadio.getText());

                selectRadio.performClick();

                tags = (RadioGroup) linearLayout.getChildAt(4);
                selectRadio = (RadioButton) tags.getChildAt(2);
                selectRadio.performClick();

                routeTitle.setText("Route 1");

                save.performClick();

                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

                Route route = WalkWalkRevolution.getRouteDao().getRoute(1);
                assertNotNull(route);
                assertEquals(1, route.getId());
                assertEquals("Route 1", route.getTitle());
                assertEquals("streets,difficult", route.getDescriptionTags());

            });
        }
    }

    @Test
    public void routeWithTitleAndTagSwitchSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);
                LinearLayout linearLayout = activity.findViewById(R.id.route_tags);
                RadioGroup tags = (RadioGroup) linearLayout.getChildAt(2);
                assertEquals(5, linearLayout.getChildCount());
                RadioButton selectRadio = (RadioButton) tags.getChildAt(0);
                assertEquals(2, tags.getChildCount());
                assertEquals("streets", selectRadio.getText());

                selectRadio.performClick();

                selectRadio = (RadioButton) tags.getChildAt(1);
                selectRadio.performClick();

                routeTitle.setText("Route 1");

                save.performClick();

                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

                Route route = WalkWalkRevolution.getRouteDao().getRoute(1);
                assertNotNull(route);
                assertEquals(1, route.getId());
                assertEquals("Route 1", route.getTitle());
                assertEquals("trail", route.getDescriptionTags());

            });
        }
    }

    @Test
    public void routeWithTitleNoTagSave(){
        try(ActivityScenario<CreateRouteActivity> scenario = ActivityScenario.launch(CreateRouteActivity.class)){
            scenario.onActivity(activity -> {
                TextView routeTitle = activity.findViewById(R.id.route_title);
                Button save = (Button) activity.findViewById(R.id.save_button);

                routeTitle.setText("Route 1");

                save.performClick();

                ShadowLooper.runUiThreadTasksIncludingDelayedTasks();

                Route route = WalkWalkRevolution.getRouteDao().getRoute(1);
                assertNotNull(route);
                assertEquals(1, route.getId());
                assertEquals("Route 1", route.getTitle());
                assertEquals("", route.getDescriptionTags());

            });
        }
    }

}
