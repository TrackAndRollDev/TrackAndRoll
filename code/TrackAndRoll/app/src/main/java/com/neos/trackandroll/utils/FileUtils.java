package com.neos.trackandroll.utils;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.neos.trackandroll.model.DataStore;
import com.neos.trackandroll.model.AppConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FileUtils {

    private static final String JSON_NAME_FILE = "TrackAndRollConfiguration.json";
    private static final String NAME_DIRECTORY = "TrackAndRollData";

    public static void saveAppConfiguration(Context context){
        saveInEnvironmentData(context);
        saveInCustomRepo();
    }

    private static void saveInEnvironmentData(Context context){
        exportJsonFile(new File((context.getFileStreamPath(JSON_NAME_FILE).getPath())));
    }

    private static void saveInCustomRepo(){
        exportJsonFile(new File(FileUtils.getRootFile(), JSON_NAME_FILE));
    }

    /**
     * Process called to get the root file
     * @return : the root file
     */
    static File getRootFile(){
        File root = new File(Environment.getExternalStorageDirectory(), NAME_DIRECTORY);
        if (!root.exists()) {
            root.mkdirs();
        }
        return root;
    }

    private static void exportJsonFile(File configFile){
        Gson gson = new Gson();

        // Java object to JSON, and assign to a String
        String jsonInString = gson.toJson(DataStore.getInstance().getAppConfiguration());

        try {
            FileWriter writer = new FileWriter(configFile);

            writer.append(jsonInString);
            writer.flush();
            writer.close();
        } catch (Exception e) {
        }
    }

    /**
     * Process called to load the current json configuration file
     */
    public static void loadAppConfiguration(Context context){
        File configFile = new File((context.getFileStreamPath(JSON_NAME_FILE).getPath()));
        manageAppConfigurationFileJSON(configFile);
    }

    /**
     * Manage the json file of the app configuration 
     * @param configFile : the config file
     */
    private static void manageAppConfigurationFileJSON(File configFile){
        Gson gson = new Gson();
        try {
            DataStore.getInstance().setAppConfiguration(gson.fromJson(new FileReader(configFile), AppConfiguration.class));
        } catch(Exception e){
            DataStore.getInstance().setAppConfiguration(new AppConfiguration());
            LogUtils.e(LogUtils.DEBUG_TAG,"FileNotFoundException => ",e);
        }
    }

}
