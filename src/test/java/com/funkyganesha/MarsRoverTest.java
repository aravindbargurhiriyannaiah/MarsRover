package com.funkyganesha;

import com.funkyganesha.marsRover.MarsRover;
import com.funkyganesha.marsRover.Navigator;
import com.funkyganesha.marsRover.beans.Coordinates;
import com.funkyganesha.marsRover.beans.RoverPosition;
import com.funkyganesha.marsRover.enums.Orientation;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by abargurhiriyannaiah on 9/10/16.
 */
public class MarsRoverTest {
    private Navigator navigator;
    private String input;

    @Before
    public void beforeEachTestCase() {
        navigator = new MarsRover();
    }

    @Test
    public void testNavigation() {
        input = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM";
        System.out.println("Input - \n" + input);
        List<RoverPosition> result = navigator.navigate(input);
        System.out.println("\nOutput - ");
        result.forEach(System.out::println);
        assertThat(result).isNotNull().isNotEmpty().hasSize(2);
        RoverPosition roverPosition = result.get(0);
        Coordinates coordinates = roverPosition.getCoordinates();
        Orientation orientation = roverPosition.getOrientation();

        assertThat(roverPosition).isNotNull();
        assertThat(coordinates).isNotNull();
        assertThat(orientation).isNotNull();
        assertThat(coordinates.getX()).isEqualTo(1);
        assertThat(coordinates.getY()).isEqualTo(3);
        assertThat(orientation).isEqualTo(Orientation.N);

        roverPosition = result.get(1);
        coordinates = roverPosition.getCoordinates();
        orientation = roverPosition.getOrientation();

        assertThat(roverPosition).isNotNull();
        assertThat(coordinates).isNotNull();
        assertThat(orientation).isNotNull();
        assertThat(coordinates.getX()).isEqualTo(5);
        assertThat(coordinates.getY()).isEqualTo(1);
        assertThat(orientation).overridingErrorMessage("Incorrect orientation. Should face East.").isEqualTo(Orientation.E);
    }

    @Test
    public void testToMoveTheRoverOffTheGrd() {
        String input = "1 1\n1 1 N\nM";
        System.out.println("Input - \n" + input);
        List<RoverPosition> result = navigator.navigate(input);
        System.out.println("\nOutput - ");
        result.forEach(System.out::println);
        assertThat(result).isNotNull().isNotEmpty().hasSize(1);
        RoverPosition roverPosition = result.get(0);
        Coordinates coordinates = roverPosition.getCoordinates();
        Orientation orientation = roverPosition.getOrientation();
        assertThat(coordinates).isNotNull();
        assertThat(coordinates.getX()).isEqualTo(1);
        assertThat(coordinates.getY()).isEqualTo(1);
        assertThat(orientation).isNotNull().isEqualTo(Orientation.N);
    }

    @Test
    public void testValidBoundaryConditions() {
        String input = "0 0\n0 0 N\nLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRLRL";
        System.out.println("Input - \n" + input);
        List<RoverPosition> result = navigator.navigate(input);
        System.out.println("\nOutput - ");
        result.forEach(System.out::println);
        assertThat(result).isNotNull().isNotEmpty().hasSize(1);
        RoverPosition roverPosition = result.get(0);
        Coordinates coordinates = roverPosition.getCoordinates();
        Orientation orientation = roverPosition.getOrientation();
        assertThat(coordinates).isNotNull();
        assertThat(coordinates.getX()).isEqualTo(0);
        assertThat(coordinates.getY()).isEqualTo(0);
        assertThat(orientation).isNotNull().isEqualTo(Orientation.W);
    }
}
