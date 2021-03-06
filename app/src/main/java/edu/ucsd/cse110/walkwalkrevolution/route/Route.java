package edu.ucsd.cse110.walkwalkrevolution.route;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.activity.Activity;
import edu.ucsd.cse110.walkwalkrevolution.activity.EmptyActivity;
import edu.ucsd.cse110.walkwalkrevolution.activity.Walk;


@JsonPropertyOrder({"id", "firestore", "userId", "title", "location", "notes", "activity"})
public class Route {

    public static final String USER_ID = "userId";
    public static final String TITLE = "title";
    public static final String LOCATION = "location";
    public static final String NOTES = "notes";
    public static final String DESCRIPTION_TAGS = "descriptionTags";
    public static final String ROUTE = "route";
    public static final String DEFAULT_ID = "not stored in cloud";
    public static final String RESPONSES = "responses";

    private long id;
    @JsonIgnore
    private String userIn;
    private String userId;
    private String title;
    private String location;
    private String descriptionTags;
    private String firestoreId;
    private Activity activity;
    private String notes;
    private Map<String, String> responses;

    public static class Builder {
        String title;
        String location;
        String notes;
        String description;
        String userId;
        String firestoreId;
        Activity activity;
        Map<String, String> responses;

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setLocation(String location){
            this.location = location;
            return this;
        }

        public Builder setNotes(String notes){
            this.notes = notes;
            return this;
        }

        public Builder setDescription(String description){
            this.description = description;
            return this;
        }

        public Builder setUserId(String userId){
            this.userId = userId;
            return this;
        }


        public Builder setActivity(Activity activity){
            this.activity = activity;
            return this;
        }


        public Builder setFirestoreId(String fid){
            this.firestoreId = fid;
            return this;
        }

        public Builder setResponses(Map<String, String> responses) {
            this.responses = responses;
            return this;
        }


        public Route build(){
            Route route = new Route(title, activity != null ? activity : new EmptyActivity());
            if(location != null) route.setLocation(location);
            if(notes != null) route.setNotes(notes);
            if(description != null) route.setDescriptionTags(description);
            if(userId != null) route.setUserId(userId);
            if(firestoreId != null) route.setFirestoreId(firestoreId);
            return route;
        }
    }

    public Route(@JsonProperty("id") long id, @JsonProperty("firestoreId") String firestoreId,
                 @JsonProperty("userId") String userId,
                 @JsonProperty("title") String title,
                 @JsonProperty("location") String location,
                 @JsonProperty("descriptionTags") String descriptionTags,
                 @JsonProperty("notes") String notes,
                 @JsonProperty("activity") Activity activity,
                 @JsonProperty("responses") Map<String, String> responses){
        this.id = id;
        this.firestoreId = firestoreId;
        this.userId = userId;
        this.title = title;
        this.location = location;
        this.descriptionTags = descriptionTags;
        this.notes = notes;
        this.activity = activity;
        this.responses = responses;
    }

    public Route(Map<String, Object> map) {
        this.title = (String) map.get(TITLE);
        this.descriptionTags = (String) map.get(DESCRIPTION_TAGS);
        this.location = (String) map.get(LOCATION);
        this.notes = (String) map.get(NOTES);
        this.responses = (Map<String, String>) map.get(RESPONSES);
        this.activity = null;
    }

    //Used for testing
    public Route(long id, String title, Activity activity){
        this.id = id;
        this.title = title;
        this.activity = activity;
        this.responses = new HashMap<>();
    }

    public Route(String title, Activity activity){
        this.id = WalkWalkRevolution.getRouteDao().getNextId();
        this.title = title;
        this.activity = activity;
        this.responses = new HashMap<>();
    }

    public long getId(){
        return id;
    }

    private void setId(){
        this.id = id;
    }

    public Activity getActivity(){
        return activity;
    }

    public void setActivity(Activity newAct) {
        this.activity = newAct;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getLocation() {
        return location == null ? "": location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public String getDescriptionTags() {
        return descriptionTags == null ? "": descriptionTags;
    }

    public void setDescriptionTags(String descriptionTags) {
        this.descriptionTags = descriptionTags;
    }

    public String getNotes(){
        return notes == null ? "": notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


    public String getFirestoreId(){
        return this.firestoreId;
    }

    public void setFirestoreId(String fid){
        this.firestoreId = fid;
    }

    public Map<String, String> getResponses() { return this.responses; }

    public void setResponses(Map<String, String> responses) { this.responses = responses; }

    public void setUserIn(String initials) {
        this.userIn = initials;
    }

    public String getUserIn() {
        return userIn;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();
        map.put(TITLE, title);
        map.put(USER_ID, userId);
        try {
            map.put(ROUTE, RouteUtils.serialize(this));
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
        return map;
    }

    public enum Response {
        ACCEPT,
        DECLINE_TIME,
        DECLINE_BAD_ROUTE;
    }
}