package com.funkyganesha.marsRover.beans;

import com.funkyganesha.marsRover.enums.Movement;

import java.util.List;

/**
 * Created by abargurhiriyannaiah on 8/10/16.
 */
public class RoverInput {
    private RoverPosition roverposition;

    private List<Movement> roverMovements;

    public RoverInput(RoverPosition roverposition, List<Movement> roverMovements) {
        this.roverposition = roverposition;
        this.roverMovements = roverMovements;
    }

    public RoverPosition getRoverposition() {
        return roverposition;
    }

    public List<Movement> getRoverMovements() {
        return roverMovements;
    }
}
