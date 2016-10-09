package com.funkyganesha.marsRover.beans;

import com.funkyganesha.marsRover.enums.Orientation;

/**
 * Created by abargurhiriyannaiah on 8/10/16.
 */
public class RoverPosition {
    private Coordinates coordinates;
    private Orientation orientation;

    public RoverPosition(Coordinates coordinates, Orientation orientation) {
        this.coordinates = coordinates;
        this.orientation = orientation;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public String toString() {
        return coordinates + " "
                + orientation;
    }
}
