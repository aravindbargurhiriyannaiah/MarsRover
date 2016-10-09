package com.funkyganesha;

import com.funkyganesha.marsRover.beans.Coordinates;
import com.funkyganesha.marsRover.beans.Input;
import com.funkyganesha.marsRover.beans.RoverInput;
import com.funkyganesha.marsRover.beans.RoverPosition;
import com.funkyganesha.marsRover.enums.Movement;
import com.funkyganesha.marsRover.enums.Orientation;
import com.funkyganesha.marsRover.util.Verifier;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by abargurhiriyannaiah on 8/10/16.
 */
public class VerifierTest {
    @Test
    public void testIsValidCoordinates() {
        assertThat(Verifier.areValidCoordinates(new Coordinates(5, 5))).isTrue();
        assertThat(Verifier.areValidCoordinates(new Coordinates(0, 0))).isTrue();
        assertThat(Verifier.areValidCoordinates(new Coordinates(-1, 0))).isFalse();
        assertThat(Verifier.areValidCoordinates(new Coordinates(0, -10))).isFalse();
    }

    @Test
    public void testAreValidPositionalCoordinates() {
        Coordinates coordinates = new Coordinates(10, 30);
        Coordinates gridCoordinates = new Coordinates(20, 40);
        assertThat(Verifier.areValidCoordinates(coordinates, gridCoordinates)).isTrue();

        coordinates = new Coordinates(10, 10);
        gridCoordinates = new Coordinates(2, 20);
        assertThat(Verifier.areValidCoordinates(coordinates, gridCoordinates)).isFalse();

        coordinates = new Coordinates(10, 1);
        gridCoordinates = new Coordinates(20, 0);

        assertThat(Verifier.areValidCoordinates(coordinates, gridCoordinates)).isFalse();

        assertThat(Verifier.areValidCoordinates(null, gridCoordinates)).isFalse();
        assertThat(Verifier.areValidCoordinates(coordinates, null)).isFalse();
    }

    @Test
    public void testAreValidMovementInstructions() {
        assertThat(Verifier.areValidMovementInstructions(null)).isFalse();
        assertThat(Verifier.areValidMovementInstructions("")).isFalse();
        assertThat(Verifier.areValidMovementInstructions("lmr")).isTrue();
        assertThat(Verifier.areValidMovementInstructions("LMR")).isTrue();
        assertThat(Verifier.areValidMovementInstructions("lMRmrL")).isTrue();
    }

    @Test
    public void testIsValidOrientation() {
        assertThat(Verifier.isValidOrientation("N")).isTrue();
        assertThat(Verifier.isValidOrientation("E")).isTrue();
        assertThat(Verifier.isValidOrientation("W")).isTrue();
        assertThat(Verifier.isValidOrientation("S")).isTrue();

        assertThat(Verifier.isValidOrientation("n")).isTrue();
        assertThat(Verifier.isValidOrientation("e")).isTrue();
        assertThat(Verifier.isValidOrientation("w")).isTrue();
        assertThat(Verifier.isValidOrientation("s")).isTrue();

        assertThat(Verifier.isValidOrientation("q")).isFalse();
        assertThat(Verifier.isValidOrientation("Q")).isFalse();

        assertThat(Verifier.isValidOrientation(null)).isFalse();
        assertThat(Verifier.isValidOrientation("")).isFalse();
    }

    @Test
    public void testIsValidInput() {
        String input = null;
        assertThat(Verifier.isValidInput(input)).isFalse();

        input = "";
        assertThat(Verifier.isValidInput(input)).isFalse();

        //incorrect number of lines in the input.
        input = "5 5";
        assertThat(Verifier.isValidInput(input)).isFalse();

        //Incorrect number of instructions
        input = "5 5\n1 2 N";
        assertThat(Verifier.isValidInput(input)).isFalse();

        //Sunny scenario
        input = "5 5\n1 2 N\nLMLMLMLMM";
        assertThat(Verifier.isValidInput(input)).isTrue();

        //Input with invalid Movement.
        input = "5 5\n1 2 N\nLMLMLMLMS";
        assertThat(Verifier.isValidInput(input)).isFalse();

        //Input is valid, but the number of instruction lines is incorrect
        input = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E";
        assertThat(Verifier.isValidInput(input)).isFalse();

        //Input is valid, but the number of instruction lines is incorrect
        input = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM";
        assertThat(Verifier.isValidInput(input)).isTrue();
    }

    @Test
    public void testBuildInput() {
        String inputString = null;
        Input input = Verifier.buildInput(inputString);
        assertThat(input).isNull();

        //Input is valid, but the number of instruction lines is incorrect
        inputString = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E";
        assertThat(Verifier.buildInput(inputString)).isNull();

        inputString = "5 5\n1 2 N\nLMLMLMLMM";
        input = Verifier.buildInput(inputString);
        assertThat(input).isNotNull();
        Coordinates coordinates = input.getGridCoordinates();
        assertThat(coordinates.getX()).isEqualTo(5);
        assertThat(coordinates.getY()).isEqualTo(5);

        List<RoverInput> roverInputs = input.getRoverInputs();
        assertThat(roverInputs).isNotNull().hasSize(1);

        RoverInput roverInput = roverInputs.get(0);
        RoverPosition roverposition = roverInput.getRoverposition();
        assertThat(roverposition).isNotNull();
        assertThat(roverposition.getOrientation()).isEqualTo(Orientation.N);
        coordinates = roverposition.getCoordinates();
        assertThat(coordinates.getX()).isEqualTo(1);
        assertThat(coordinates.getY()).isEqualTo(2);

        List<Movement> movements = roverInput.getRoverMovements();
        assertThat(movements).isNotNull().hasSize(9);
        StringBuilder sb = new StringBuilder();
        movements.forEach((movement) -> sb.append(movement.toString()));
        assertThat(sb.toString()).isNotNull().isEqualTo("LMLMLMLMM");
    }
}
