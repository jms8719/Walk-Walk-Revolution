package edu.ucsd.cse110.walkwalkrevolution.route.persistence;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import edu.ucsd.cse110.walkwalkrevolution.WalkWalkRevolution;
import edu.ucsd.cse110.walkwalkrevolution.route.Route;
import edu.ucsd.cse110.walkwalkrevolution.route.RouteUtils;

import static android.content.Context.MODE_PRIVATE;

public class RouteSharedPreferenceDao implements BaseRouteDao {

    private static final String SP_ROUTE = "ROUTE";
    private static final String NEXT_ID_KEY = "NEXT_ID";
    private static final String T_ROUTE = "T_ROUTE";
    private static final String F_ROUTE = "F_ROUTE";

    @Override
    public void addRoute(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String jsonString;

        try {
            jsonString = RouteUtils.serialize(route);
            Log.d("RouteSP", jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route");
        }

        editor.putString(Long.toString(route.getId()), jsonString);

        editor.apply();
    }

    @Override
    public void addTeamRoute(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(T_ROUTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        String jsonString;

        try {
            jsonString = RouteUtils.serialize(route);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route");
        }

        editor.putString(route.getFirestoreId(), jsonString);

        editor.apply();
    }

    @Override
    public void addFavorite(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(F_ROUTE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(route.getFirestoreId(), "");

        editor.apply();
    }

    @Override
    public Route getRoute(long routeId) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);

        String jsonString = sp.getString(Long.toString(routeId), "");

        if(jsonString.equals("")) return null;

        try {
            return RouteUtils.deserialize(jsonString);
        } catch (Exception e) {
            throw new RuntimeException("Invalid Route JsonString");
        }
    }

    @Override
    public boolean isFavorite(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(F_ROUTE, MODE_PRIVATE);
        return sp.contains(route.getFirestoreId());
    }

    @Override
    public void removeFavorite(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(F_ROUTE, MODE_PRIVATE);
        if(isFavorite(route)){
            SharedPreferences.Editor editor = sp.edit();
            editor.remove(route.getFirestoreId());
            editor.apply();
        }
    }

    @Override
    public Map<String, ?> getAllRoutes() {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);
        Map<String, String> routes = new HashMap<>();
        for(Map.Entry<String, ?> entry: sp.getAll().entrySet()){
            if(entry.getKey().toString().equals(NEXT_ID_KEY)) continue;
            routes.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return routes;
    }

    @Override
    public Map<String, ?> getTeamRoutes() {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(T_ROUTE, MODE_PRIVATE);
        Map<String, String> routes = new HashMap<>();
        for(Map.Entry<String, ?> entry: sp.getAll().entrySet()){
            routes.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return routes;
    }

    @Override
    public boolean walkedTeamRoute(Route route) {
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(T_ROUTE, MODE_PRIVATE);
        return sp.contains(route.getFirestoreId());
    }

    @Override
    public long getNextId(){
        Context context = WalkWalkRevolution.getContext();
        SharedPreferences sp = context.getSharedPreferences(SP_ROUTE, MODE_PRIVATE);

        long nextId = sp.getLong(NEXT_ID_KEY, 1);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(NEXT_ID_KEY, nextId+1);

        editor.apply();

        return nextId;
    }
}
