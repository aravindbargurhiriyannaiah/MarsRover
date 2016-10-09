package com.funkyganesha.marsRover.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abargurhiriyannaiah on 8/10/16.
 */
public class Input {
    private Coordinates gridCoordinates;
    private List<RoverInput> roverInputs;

    public Coordinates getGridCoordinates() {
        return gridCoordinates;
    }

    public void setGridCoordinates(Coordinates gridCoordinates) {
        this.gridCoordinates = gridCoordinates;
    }

    public List<RoverInput> getRoverInputs() {
        return roverInputs;
    }

    public void addRoverInput(RoverInput roverInput) {
        if (this.roverInputs == null) {
            roverInputs = new ArrayList<>();
        }
        roverInputs.add(roverInput);
    }
}
