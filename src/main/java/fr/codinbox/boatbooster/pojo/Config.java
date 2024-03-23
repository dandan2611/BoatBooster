package fr.codinbox.boatbooster.pojo;

import java.util.Map;

public class Config {

    private Map<String, Double> bumpers;
    public Config() {
    }

    public Map<String, Double> getBumpers() {
        return bumpers;
    }

    public void setBumpers(Map<String, Double> bumpers) {
        this.bumpers = bumpers;
    }

}
