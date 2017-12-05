package com.neos.trackandroll.utils;

import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.constant.Constant;
import com.neos.trackandroll.model.player.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Set;

public class DataUtils {

    public static ArrayList<String> convertPlayerListToStringPlayerList(ArrayList<Player> playerList) {
        ArrayList<String> stringPlayerList = new ArrayList<>();
        for (int i = 0; i < playerList.size(); i++) {
            stringPlayerList.add(playerList.get(i).getLastName() + "\n" + playerList.get(i).getFirstName());
        }
        stringPlayerList.add(0, Constant.SENSOR_NON_ASSIGNED_NAME);
        return stringPlayerList;
    }

    public static ArrayList<String> convertSetSessionToStringList(Set<String> sessionSet) {
        ArrayList<String> sessionList = new ArrayList<>();
        for (int i = 0; i < sessionSet.size(); i++) {
            sessionList.add((String) sessionSet.toArray()[i]);
        }
        Collections.sort(sessionList, new Comparator<String>() {
            @Override
            public int compare(String text1, String text2) {
                return text1.compareToIgnoreCase(text2);
            }
        });
        return sessionList;
    }


    public static ArrayList<Player> convertMapToPlayerList(HashMap<String, Player> playerMap) {
        ArrayList<Player> playerList = new ArrayList<>();
        for (String key : playerMap.keySet()) {
            playerList.add(playerMap.get(key));
        }
        Collections.sort(playerList, new Comparator<Player>() {

            @Override
            public int compare(Player player1, Player player2) {
                return player1.getPlayerKey().compareTo(player2.getPlayerKey());
            }
        });
        DataStore.getInstance().setPlayerList(playerList);
        return playerList;
    }

    public static ArrayList<Player> sortPlayerList(ArrayList<Player> playerList) {
        Collections.sort(playerList, new Comparator<Player>() {

            @Override
            public int compare(Player player1, Player player2) {
                return player1.getPlayerKey().compareTo(player2.getPlayerKey());
            }
        });
        return playerList;
    }

    public static int getSelectedPlayerPosition(ArrayList<Player> playerList, String playerKey) {
        int selectedPlayerPosition = 0;
        if (playerKey.equals(Constant.SENSOR_NON_ASSIGNED_NAME)) {
            selectedPlayerPosition = 0;
        } else {
            for (int i = 0; i < playerList.size() && selectedPlayerPosition == 0; i++) {
                if (playerList.get(i).getPlayerKey().equals(playerKey)) {
                    selectedPlayerPosition = i + 1;
                }
            }
        }
        return selectedPlayerPosition;
    }

    public static String getDefaultSessionName(Calendar endInstant) {
        return "Session_" + DateUtils.convertTimeDefaultSessionTime(endInstant);
    }
}
