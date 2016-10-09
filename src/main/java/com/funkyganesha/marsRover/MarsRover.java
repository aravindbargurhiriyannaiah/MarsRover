package com.funkyganesha.marsRover;

import com.funkyganesha.marsRover.beans.Coordinates;
import com.funkyganesha.marsRover.beans.Input;
import com.funkyganesha.marsRover.beans.RoverInput;
import com.funkyganesha.marsRover.beans.RoverPosition;
import com.funkyganesha.marsRover.enums.Movement;
import com.funkyganesha.marsRover.enums.Orientation;
import com.funkyganesha.marsRover.util.Verifier;

import java.util.ArrayList;
import java.util.List;

import static com.funkyganesha.marsRover.enums.Movement.L;
import static com.funkyganesha.marsRover.enums.Movement.R;
import static com.funkyganesha.marsRover.enums.Orientation.*;

/**
 * Created by abargurhiriyannaiah on 9/10/16.
 */
public class MarsRover implements Navigator{

    @Override
    public List<RoverPosition> navigate(String input) {
        List<RoverPosition> result = null;
        Input systemInput = Verifier.buildInput(input);
        if (systemInput != null) {
            List<RoverInput> roverInputs = systemInput.getRoverInputs();
            Coordinates gridCoordinates = systemInput.getGridCoordinates();
            for (RoverInput roverInput : roverInputs) {
                RoverPosition roverposition = roverInput.getRoverposition();
                Coordinates currentCoordinates = roverposition.getCoordinates();
                Orientation currentOrientation = roverposition.getOrientation();

                boolean canContinue = true;
                List<Movement> movements = roverInput.getRoverMovements();
                for (Movement movement : movements) {
                    if (canContinue) {
                        switch (movement) {
                            case L:
                                //Turn Left from current orientation.
                                currentOrientation = getNewOrientation(currentOrientation, L);
                                break;
                            case R:
                                //Turn Right from current orientation.
                                currentOrientation = getNewOrientation(currentOrientation, R);
                                break;
                            case M:
                                //Find out if the rover can move one  block from the current coordinate
                                Coordinates newCoordinates = getNewCoordinates(currentCoordinates, currentOrientation);
                                if (Verifier.areValidCoordinates(newCoordinates, gridCoordinates)) {
                                    currentCoordinates = newCoordinates;
                                } else {
                                    System.out.println("Stopping the rover because the move will cause it to go off the grid." +
                                            "\ncurrentCoordinates = (" + currentCoordinates + ")" +
                                            "\ncurrentOrientation = " + currentOrientation +
                                            "\nnewCoordinates = (" + newCoordinates + ")");

                                    canContinue = false;
                                }
                                break;
                        }
                    }
                }
                if (result == null) {
                    result = new ArrayList<>();
                }
                result.add(new RoverPosition(currentCoordinates, currentOrientation));
            }
        }
        return result;
    }

    private Coordinates getNewCoordinates(Coordinates currentCoordinates, Orientation orientation) {
        Coordinates newCoordinates = null;
        switch (orientation) {
            case N:
                newCoordinates = new Coordinates(currentCoordinates.getX(), currentCoordinates.getY() + 1);
                break;
            case E:
                newCoordinates = new Coordinates(currentCoordinates.getX() + 1, currentCoordinates.getY());
                break;
            case W:
                newCoordinates = new Coordinates(currentCoordinates.getX() - 1, currentCoordinates.getY());
                break;
            case S:
                newCoordinates = new Coordinates(currentCoordinates.getX(), currentCoordinates.getY() - 1);
                break;
        }
        return newCoordinates;
    }

    private Orientation getNewOrientation(Orientation currentOrientation, Movement movement) {
        Orientation newOrientation = currentOrientation;
        switch (currentOrientation) {
            case N:
                switch (movement) {
                    case L:
                        newOrientation = W;
                        break;
                    case R:
                        newOrientation = E;
                        break;
                }
                break;
            case E:
                switch (movement) {
                    case L:
                        newOrientation = N;
                        break;
                    case R:
                        newOrientation = S;
                        break;
                }
                break;
            case W:
                switch (movement) {
                    case L:
                        newOrientation = S;
                        break;
                    case R:
                        newOrientation = N;
                        break;
                }
                break;
            case S:
                switch (movement) {
                    case L:
                        newOrientation = E;
                        break;
                    case R:
                        newOrientation = W;
                        break;
                }
                break;

        }
        return newOrientation;
    }
}
