package com.neos.trackandroll.testBluetooth;

import com.neos.trackandroll.communication.distribution.in.ActionXXX;
import com.neos.trackandroll.communication.distribution.in.ICommandIn;
import com.neos.trackandroll.communication.protocole.ProcessIn;

import org.json.JSONObject;

import java.util.HashMap;

public class Distributor {

    /**
     * The process that could be called on the raspberry
     */
    private HashMap<String, ICommandIn> commands;

    /**
     * Main constructor -> create the different commands
     */
    public Distributor() {
        // Create the object commands which will have all the process
        this.commands = new HashMap<>();

        // the different command
        this.commands.put(ProcessIn.ACTION_XXX, new ActionXXX());

    }

    public void dispatch (String id_command, JSONObject params) {
        commands.get(id_command).execute(params);
    }
}
