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
                                currentOrientation = getNewOrientation(currentOrientation, L);
                                break;
                            case R:
                                currentOrientation = getNewOrientation(currentOrientation, R);
                                break;
                            case M:
                                //Find out if a 'M' can be executed from a given coordinate - we do not want the rover falling off the grid. Stay put.
                                Coordinates newCoordinates = getNewCoordinates(currentCoordinates, currentOrientation);
                                if (Verifier.areValidCoordinates(newCoordinates)) {
                                    currentCoordinates = newCoordinates;
                                } else {
                                    System.out.println("The rover cannot move to the new coordinates as it will go off grid. " +
                                            "Stopping the program. Current coordinates = " + currentCoordinates);
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
        Orientation orientation = currentOrientation;
        switch (currentOrientation) {
            case N:
                switch (movement) {
                    case L:
                        orientation = W;
                        break;
                    case R:
                        orientation = E;
                        break;
                }
                break;
            case E:
                switch (movement) {
                    case L:
                        orientation = N;
                        break;
                    case R:
                        orientation = S;
                        break;
                }
                break;
            case W:
                switch (movement) {
                    case L:
                        orientation = S;
                        break;
                    case R:
                        orientation = N;
                        break;
                }
                break;
            case S:
                switch (movement) {
                    case L:
                        orientation = E;
                        break;
                    case R:
                        orientation = W;
                        break;
                }
                break;

        }
        return orientation;
    }
}
