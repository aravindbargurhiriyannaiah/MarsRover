package com.funkyganesha.marsRover;

import com.funkyganesha.marsRover.beans.RoverPosition;

import java.util.List;

/**
 * Created by abargurhiriyannaiah on 9/10/16.
 */
public interface Navigator {
    List<RoverPosition> navigate(String input);
}
