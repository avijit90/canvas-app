package com.canvas.app.util;

import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class InputParserTest {


    @Test
    public void givenValidInputWithTwoNumerals_ThenShouldBuildRequest() {
        String input = "b 10 4 c";

        Request request = InputParser.parseInput(input);

        assertNotNull(request);
        assertEquals(RequestType.BUCKET_FILL, request.getRequestType());
        assertEquals(of("x1", 10, "y1", 4), request.getDimensions());
        assertEquals("c", request.getColor());
    }

    @Test
    public void givenValidInputWithFourNumerals_ThenShouldBuildRequest() {
        String input = "r 10 4 20 8";

        Request request = InputParser.parseInput(input);

        assertNotNull(request);
        assertEquals(RequestType.RECTANGLE, request.getRequestType());
        assertEquals(of("x1", 10, "y1", 4, "x2", 20, "y2", 8), request.getDimensions());
        assertNull(request.getColor());
    }

    @Test
    public void givenValidInputWithPrefixedAndTrailingSpace_ThenShouldBuildRequest() {
        String input = "   " + "r 10 4 20 8" + "    ";

        Request request = InputParser.parseInput(input);

        assertNotNull(request);
        assertEquals(RequestType.RECTANGLE, request.getRequestType());
        assertEquals(of("x1", 10, "y1", 4, "x2", 20, "y2", 8), request.getDimensions());
        assertNull(request.getColor());
    }

    @Test
    public void givenInvalid_ThenShouldBuildEmptyRequest() {
        String input = "   ghghgjh    ";

        Request request = InputParser.parseInput(input);

        assertNotNull(request);
        assertNull(request.getRequestType());
        assertNull(request.getDimensions());
        assertNotNull(request.getColor());
    }
}
