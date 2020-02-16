package edu.ucsd.cse110.walkwalkrevolution.activity;

import java.util.ArrayList;
import java.util.Map;

public class Walk extends Activity{

    public static final String STEP_COUNT = "STEP_COUNT";
    public static final String DURATION = "DURATION";
    public static final String MILES = "MILES";
    public static final String DESC_TAGS = "DESCRIPTION_TAGS";

    public static final String TAGS = "TAGS";

    public Walk(){
        super();
        setDetail(STEP_COUNT, "0");
        setDetail(DURATION, "0");
        setDetail(MILES, "0");
    }

    public Walk(String descTags) {
        super();
        setDetail(STEP_COUNT, "0");
        setDetail(DURATION, "0");
        setDetail(MILES, "0");
        setDetail(DESC_TAGS, descTags);
    }

    public Walk(Map<String, String> walk){
        super(walk);
    }

}
