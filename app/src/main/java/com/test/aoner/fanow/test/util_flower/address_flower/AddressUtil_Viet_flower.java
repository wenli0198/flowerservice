package com.test.aoner.fanow.test.util_flower.address_flower;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class AddressUtil_Viet_flower {

    private static class Inner {
        private static final AddressUtil_Viet_flower instance = new AddressUtil_Viet_flower();
    }

    private AddressUtil_Viet_flower(){}

    public static AddressUtil_Viet_flower getInstance(){
        return Inner.instance;
    }

    private final HashMap<String,ArrayList<String>> states = new HashMap<>();

    public void parse(JSONArray statesJA){
        try {
            for (int i=0;i<statesJA.length();i++){
                JSONObject statesJO = statesJA.getJSONObject(i);
                JSONArray citiesJA = statesJO.optJSONArray("children");
                ArrayList<String> cities = new ArrayList<>();
                for (int j = 0; j< Objects.requireNonNull(citiesJA).length(); j++){
                    JSONObject citiesJO = citiesJA.getJSONObject(j);

                    cities.add(citiesJO.optString("name"));
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
        ArrayList<String> cities = Objects.requireNonNull(states.get(state));
        return cities.toArray(new String[cities.size()]);
    }

}
