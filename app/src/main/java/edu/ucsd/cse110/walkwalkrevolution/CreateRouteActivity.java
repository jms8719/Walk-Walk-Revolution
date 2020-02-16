package edu.ucsd.cse110.walkwalkrevolution;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.DescriptionTags.DescriptionGroup;
import edu.ucsd.cse110.walkwalkrevolution.DescriptionTags.DescriptionTagsListAdapter;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;

public class CreateRouteActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private DescriptionGroup descGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        linearLayout = findViewById(R.id.route_tags);
        descGroup = new DescriptionGroup(linearLayout);
        descGroup.createRadioGroup();

        TextView routeTitle = findViewById(R.id.route_title);
        Button saveRoute = (Button) findViewById(R.id.save_button);
        Button cancelRoute = (Button) findViewById(R.id.cancel_button);
        TextView startLocation = findViewById(R.id.start_location);

        Bundle extras = getIntent().getExtras();


        saveRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tags = descGroup.getSelectedTags();
                if(TextUtils.isEmpty(routeTitle.getText())){
                    routeTitle.setError("Route Title is Required");
                } else {
                    Activity activity;
                    Bundle extras = getIntent().getExtras();
                    if(extras != null){
                        Map<String, String> data = new HashMap<String, String>(){{
                            put(Walk.STEP_COUNT, extras.getString(Walk.STEP_COUNT));
                            put(Walk.MILES, extras.getString(Walk.MILES));
                            put(Walk.DURATION, extras.getString(Walk.DURATION));
                        }};
                        activity = new Walk(data);
                        activity.setDate();
                    } else {
                        activity = new Walk();
                    }
                    // Initialize route and fill in fields.
                    Route route = new Route(routeTitle.getText().toString(), activity);

                    if(TextUtils.isEmpty(startLocation.getText())) {
                        route.setLocation(startLocation.getText().toString());
                    }
                    route.setDescriptionTags(tags);
                    Log.d("desc-tags", "tags " + tags);
                    WalkWalkRevolution.getRouteDao().addRoute(route);
                    finish();
                }
            }
        });

        cancelRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
