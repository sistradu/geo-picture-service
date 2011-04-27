package de.hszigr.gpics.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Stefan Radusch
 * Date: 27.04.11
 * Time: 14:37
 * To change this template use File | Settings | File Templates.
 */
public class CoordinateCalculatorTest {
    @Test
    public void testGetDecimalCoordinate() throws Exception {
        CoordinateCalculator c = new CoordinateCalculator();
        Assert.assertEquals("51.148833333333336", c.getDecimalCoordinate("51\"8'55.8", "N"));
        Assert.assertEquals("14.998884722222222", c.getDecimalCoordinate("14\"59'55.985", "E"));
        Assert.assertEquals("-51.148833333333336", c.getDecimalCoordinate("51\"8'55.8", "S"));
        Assert.assertEquals("-14.998884722222222", c.getDecimalCoordinate("14\"59'55.985", "W"));
    }
}
