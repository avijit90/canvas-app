package com.canvas.app.util;

import com.canvas.app.exceptions.CanvasUndefinedException;
import com.canvas.app.exceptions.SemanticsIncorrectException;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class RuleEngineTest {

    @InjectMocks
    RuleEngine unit;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenValidCanvasInput_ThenShouldGiveRequestObject() throws Exception {
        String input = "c 40 10";

        Request response = unit.parseUserInput(input, null);

        assertNotNull(response);
        assertEquals(RequestType.CANVAS, response.getRequestType());
        assertEquals(of("x1", 40, "y1", 10), response.getDimensions());
        assertNull(response.getColor());
    }

    @Test(expected = SemanticsIncorrectException.class)
    public void givenInvalidBucketFillInput_ThenShouldThrowException() throws Exception {
        String input = "b 40 10";
        unit.parseUserInput(input, null);
    }

    @Test(expected = CanvasUndefinedException.class)
    public void givenValidLineInputWithoutCanvas_ThenShouldThrowException() throws Exception {
        String input = "l 40 10 50 10";

        unit.parseUserInput(input, null);
    }
}
