package com.neos.trackandroll.utils;

import android.content.Context;

import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.player.Player;
import com.neos.trackandroll.model.sensor.TrackAndRollSensor;

public class MockUtils {

    public static void mockFilterList(){
        DataStore.getInstance().getAppConfiguration().getSessionNameList().add("diazo");
        DataStore.getInstance().getAppConfiguration().getSessionNameList().add("dzdzeda");
        DataStore.getInstance().getAppConfiguration().getSessionNameList().add("azadz");
        DataStore.getInstance().getAppConfiguration().getSessionNameList().add("azadzezffz");
    }

    public static void mockSensorList(){
        if(DataStore.getInstance().getAppConfiguration().getTrackAndRollSensorList().size()<=0){
            for(int i=1;i<6;i++){
                TrackAndRollSensor sensor = new TrackAndRollSensor(i,"hello-"+i);
                DataStore.getInstance().getAppConfiguration().getTrackAndRollSensorList().add(sensor);
            }
        }
    }

    public static void mockPlayerList(){
        if(DataStore.getInstance().getAppConfiguration().getPlayerMap().size()<=0){
            for(int i=1;i<25;i++){
                Player newPlayer = new Player("mock"+i,"MOCK"+i,i*2,"mocker",i,"");
                DataStore.getInstance().getAppConfiguration().getPlayerMap().put(newPlayer.getPlayerKey(),newPlayer);
            }
        }
    }
}
