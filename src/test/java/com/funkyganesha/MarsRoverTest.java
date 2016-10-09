package com.funkyganesha;

import com.funkyganesha.marsRover.MarsRover;
import com.funkyganesha.marsRover.Navigator;
import com.funkyganesha.marsRover.beans.Coordinates;
import com.funkyganesha.marsRover.beans.RoverPosition;
import com.funkyganesha.marsRover.enums.Orientation;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by abargurhiriyannaiah on 9/10/16.
 */
public class MarsRoverTest {
    private Navigator navigator = new MarsRover();

    @Test
    public void testNavigation() {
        List<RoverPosition> navigate = navigator.navigate(null);

        String input = "5 5\n1 2 N\nLMLMLMLMM\n3 3 E\nMMRMMRMRRM";
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
        assertThat(orientation).isEqualTo(Orientation.E);
    }
}
