package com.test.aoner.fanow.test.util_flower.address_flower;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class AddressUtil_Niri_flower {

    private static class Inner {
        private static final AddressUtil_Niri_flower instance = new AddressUtil_Niri_flower();
    }

    private AddressUtil_Niri_flower(){}

    public static AddressUtil_Niri_flower getInstance(){
        return Inner.instance;
    }

    private final HashMap<String,HashMap<String, ArrayList<String>>> states = new HashMap<>();

    public void parse(JSONArray statesJA){
        try {
            for (int i=0;i<statesJA.length();i++){
                JSONObject statesJO = statesJA.getJSONObject(i);
                JSONArray citiesJA = statesJO.optJSONArray("children");
                HashMap<String,ArrayList<String>> cities = new HashMap<>();
                for (int j = 0; j< Objects.requireNonNull(citiesJA).length(); j++){
                    JSONObject citiesJO = citiesJA.getJSONObject(j);
                    JSONArray areasJA = citiesJO.optJSONArray("children");
                    ArrayList<String> areas = new ArrayList<>();
                    for (int k = 0; k< Objects.requireNonNull(areasJA).length(); k++){
                        JSONObject areasJO = areasJA.getJSONObject(k);
                        areas.add(areasJO.optString("name"));
                    }
                    cities.put(citiesJO.optString("name"),areas);
                }
                states.put(statesJO.optString("name"),cities);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getStates(){
        Set<String> statesSet = states.keySet();
        return statesSet.toArray(new String[statesSet.size()]);
    }

    public String[] getCities(String state){
        if (TextUtils.isEmpty(state)) return new String[0];
        Set<String> citiesSet = Objects.requireNonNull(states.get(state)).keySet();
        return citiesSet.toArray(new String[citiesSet.size()]);
    }

    public String[] getAreas(String state, String city){
        if (TextUtils.isEmpty(state)||TextUtils.isEmpty(city)) return new String[0];
        ArrayList<String> areas = Objects.requireNonNull(states.get(state)).get(city);
        return areas.toArray(new String[areas.size()]);
    }
}
