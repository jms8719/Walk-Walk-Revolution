package edu.ucsd.cse110.walkwalkrevolution;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.ActivityUtils;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public class RoutesDetailActivity extends AppCompatActivity {

    public static final String ROUTE = "edu.ucsd.cse110.walkwalkrevolution.ROUTE";
    public static final String ROUTE_ID = "edu.ucsd.cse110.walkwalkrevolution.ROUTE_ID";

    private TextView title;
    private TextView steps;
    private TextView miles;
    private TextView duration;
    private TextView date;
    private TextView location;

    private TextView tag1;
    private TextView tag2;
    private TextView tag3;
    private TextView tag4;
    private TextView tag5;

    private Button start;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_detail);

        Intent intent = getIntent();
        long id;
        if(intent.hasExtra(RoutesAdapter.EXTRA_TEXT)) {
            id = intent.getLongExtra(RoutesAdapter.EXTRA_TEXT, 0);
        } else {
            id = intent.getLongExtra(WalkActivity.TEST, 0);
        }

        Route route = WalkWalkRevolution.getRouteDao().getRoute(id);

        title = (TextView) findViewById(R.id.title);
        steps = (TextView) findViewById(R.id.numOfSteps);
        miles = (TextView) findViewById(R.id.numOfMiles);
        duration = (TextView) findViewById(R.id.numOfDur);
        date = (TextView) findViewById(R.id.numOfDay);
        location = (TextView) findViewById(R.id.location_text);
        start = (Button) findViewById(R.id.start_preroute);

        tag1 = (TextView) findViewById(R.id.tag1);
        tag2 = (TextView) findViewById(R.id.tag2);
        tag3 = (TextView) findViewById(R.id.tag3);
        tag4 = (TextView) findViewById(R.id.tag4);
        tag5 = (TextView) findViewById(R.id.tag5);

        title.setText(route.getTitle());
        steps.setText(route.getActivity().getDetail(Walk.STEP_COUNT));
        miles.setText(route.getActivity().getDetail(Walk.MILES));
        duration.setText(route.getActivity().getDetail(Walk.DURATION));
        location.setText(route.getLocation());
        date.setText(ActivityUtils.timeToMonthDay(
                ActivityUtils.stringToTime(route.getActivity().getDetail(Activity.DATE))));
        setTags(route.getDescriptionTags());

        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                Intent intent = new Intent(v.getContext(), WalkActivity.class);
                intent.putExtra(ROUTE, 1);
                intent.putExtra(ROUTE_ID, id);
                intent.putExtra("route_title", route.getTitle());
                finish();
                v.getContext().startActivity(intent);
            }
        });

    }


    public void setTags(String string) {

        String[] tags = string.split(",");
        int length = tags.length;

        if(length > 0)
        {
            tag1.setText(tags[0]);
            length = length - 1;
            if(length > 0)
            {
                tag2.setText(tags[1]);
                length = length - 1;
                if(length > 0)
                {
                    tag3.setText(tags[2]);
                    length = length - 1;
                    if(length > 0)
                    {
                        tag4.setText(tags[3]);
                        length = length - 1;
                        if(length > 0)
                        {
                            tag5.setText(tags[4]);
                        }
                    }
                }
            }
        }

    }


}