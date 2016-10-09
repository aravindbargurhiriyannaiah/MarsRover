package com.funkyganesha.marsRover.util;

import com.funkyganesha.marsRover.beans.Coordinates;
import com.funkyganesha.marsRover.beans.Input;
import com.funkyganesha.marsRover.beans.RoverInput;
import com.funkyganesha.marsRover.beans.RoverPosition;
import com.funkyganesha.marsRover.enums.Movement;
import com.funkyganesha.marsRover.enums.Orientation;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abargurhiriyannaiah on 8/10/16.
 */
public class Verifier {

    public static boolean areValidCoordinates(Coordinates coordinates) {
        return coordinates != null && coordinates.getX() >= 0 && coordinates.getY() >= 0;
    }

    public static boolean areValidCoordinates(Coordinates coordinates, Coordinates gridCoordinates) {
        //The current coordinates should not go beyond the grid.
        return coordinates != null
                && gridCoordinates != null
                && areValidCoordinates(coordinates)
                && coordinates.getX() <= gridCoordinates.getX()
                && coordinates.getY() <= gridCoordinates.getY();
    }

    public static boolean areValidMovementInstructions(String instructions) {
        //Ensure that the movement is either of L - Left, R - Right or M - Move
        boolean result = false;
        if (!Strings.isNullOrEmpty(instructions)) {
            instructions = instructions.toUpperCase();
            char[] chars = instructions.toCharArray();
            for (char aChar : chars) {
                try {
                    Movement.valueOf(String.valueOf(aChar));
                } catch (Exception e) {
                    result = false;
                    break;
                }
                result = true;
            }
        }
        return result;
    }

    public static boolean isValidOrientation(String orientation) {
        //The orientation should be one of 'N' - North, 'E' - East, 'W' West, 'S' - South

        boolean result = false;
        if (!Strings.isNullOrEmpty(orientation)) {
            orientation = orientation.toUpperCase();
            try {
                Orientation.valueOf(orientation);
                result = true;
            } catch (Exception ignore) {
            }
        }
        return result;
    }


    public static boolean isValidInput(String input) {
        boolean result = false;
        if (!Strings.isNullOrEmpty(input)) {
            String[] inputs = input.split(System.getProperty("line.separator"));
            if (inputs.length >= 3 && inputs.length % 2 != 0) {
                //There should be at least 3 lines in the input and the number of lines in the input should be odd.

                //The first line specifies grid coordinates. Validate it.
                Coordinates gridCoordinates = null;
                if (!Strings.isNullOrEmpty(inputs[0])) {
                    gridCoordinates = buildCoordinates(inputs[0]);
                    result = areValidCoordinates(gridCoordinates);
                }
                int index = 1;
                do {
                    result = isValidInputStanza(result, inputs, index, gridCoordinates);

                    // start working on the next stanza
                    index = index + 2;
                } while (index < inputs.length);
            }
        }
        return result;
    }

    public static Input buildInput(String inputInstructions) {
        Input input = null;
        boolean result = isValidInput(inputInstructions);
        if (result) {
            input = new Input();
            String[] instructions = inputInstructions.split(System.getProperty("line.separator"));

            //The first line specifies grid coordinates.
            input.setGridCoordinates(buildCoordinates(instructions[0]));

            int index = 1;
            do {
                input.addRoverInput(buildRoverInput(instructions, index));
                // start working on the next stanza
                index = index + 2;
            } while (index < instructions.length);
        }
        return input;
    }

    private static boolean isValidInputStanza(boolean result, String[] inputs, int index, Coordinates gridCoordinates) {
        //The first line (essentially the second, fourth, sixth ... lines) is the rover's current position. Ensure that they are within the grid
        String input = inputs[index];
        if (result && !Strings.isNullOrEmpty(input)) {
            Coordinates roverCurrentCoordinates = buildCoordinates(input);
            result = areValidCoordinates(roverCurrentCoordinates, gridCoordinates);
        }

        //Validate orientation
        if (result) {
            String[] split = input.split("\\s");
            result = isValidOrientation(split[2]);
        }

        //The second line (essentially the third, fifth, seventh ... lines) is instructions for the rover to move.
        if (result && !Strings.isNullOrEmpty(inputs[index + 1])) {
            result = areValidMovementInstructions(inputs[index + 1]);
        }
        return result;
    }

    private static RoverInput buildRoverInput(String[] inputs, int index) {
        //The first line (essentially the second, fourth, sixth ... lines) is the rover's current position. Ensure that they are within the grid
        Coordinates roverCurrentCoordinates = buildCoordinates(inputs[index]);
        Orientation orientation = getOrientation(inputs[index]);
        RoverPosition roverposition = new RoverPosition(roverCurrentCoordinates, orientation);

        //The second line (essentially the third, fifth, seventh ... lines) is instructions for the rover to move.
        String instructions = inputs[index + 1];
        List<Movement> movements = new ArrayList<>();
        char[] chars = instructions.toCharArray();
        for (char aChar : chars) {
            movements.add(Movement.valueOf(String.valueOf(aChar)));
        }

        return new RoverInput(roverposition, movements);
    }

    private static Coordinates buildCoordinates(String input) {
        String[] values = input.split("\\s");
        int x = Integer.valueOf(values[0]);
        int y = Integer.valueOf(values[1]);
        return new Coordinates(x, y);
    }

    private static Orientation getOrientation(String input) {
        String[] split = input.split("\\s");
        return Orientation.valueOf(split[2]);
    }
}