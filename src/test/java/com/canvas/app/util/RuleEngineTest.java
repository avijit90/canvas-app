package com.canvas.app.util;

import com.canvas.app.exceptions.CanvasUndefinedException;
import com.canvas.app.exceptions.SemanticsIncorrectException;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class RuleEngineTest {

    @InjectMocks
    RuleEngine unit;

    @Mock
    InputParser inputParser;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenValidCanvasInput_ThenShouldGiveRequestObject() throws Exception {
        String input = "c 40 10";
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.CANVAS);
        when(request.getDimensions()).thenReturn(of("x1", 40, "y1", 10));
        when(inputParser.parseInput(input)).thenReturn(request);

        Request response = unit.parseUserInput(input, null);

        assertNotNull(response);
        verify(inputParser).parseInput(eq(input));
        assertEquals(request, response);
    }

    @Test(expected = SemanticsIncorrectException.class)
    public void givenInvalidBucketFillInput_ThenShouldThrowException() throws Exception {
        String input = "b 40 10";
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.BUCKET_FILL);
        when(request.getDimensions()).thenReturn(of("x1", 40, "y1", 10));
        when(inputParser.parseInput(input)).thenReturn(request);

        unit.parseUserInput(input, null);
    }

    @Test(expected = CanvasUndefinedException.class)
    public void givenValidLineInputWithoutCanvas_ThenShouldThrowException() throws Exception {
        String input = "l 40 10 50 10";
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.LINE);
        when(request.getDimensions()).thenReturn(of("x1", 40, "y1", 10, "x2", 50, "y2", 10));
        when(inputParser.parseInput(input)).thenReturn(request);

        unit.parseUserInput(input, null);
    }
}
