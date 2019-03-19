package com.canvas.app.util;

import com.canvas.app.exceptions.CanvasBoundaryBreachedException;
import com.canvas.app.exceptions.CanvasUndefinedException;
import com.canvas.app.exceptions.InvalidLineCoordinatesException;
import com.canvas.app.exceptions.SemanticsIncorrectException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
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

        Request requestObj = unit.parseAndValidateInput(input, null);

        assertNotNull(requestObj);
        assertEquals(RequestType.CANVAS, requestObj.getRequestType());
        assertEquals(of("x1", 40, "y1", 10), requestObj.getDimensions());
        assertNull(requestObj.getColor());
    }

    @Test
    public void givenValidRectangleRequestWithinCanvas_ThenShouldGiveRequestObject() throws Exception {
        String input = "R 10 20 15 25";
        Canvas canvas = mock(Canvas.class);
        when(canvas.getWidth()).thenReturn(100);
        when(canvas.getHeight()).thenReturn(200);

        Request requestObj = unit.parseAndValidateInput(input, canvas);

        assertNotNull(requestObj);
        assertEquals(RequestType.RECTANGLE, requestObj.getRequestType());
        assertEquals(requestObj.getDimensions().get("x1"), new Integer(10));
        assertEquals(requestObj.getDimensions().get("y1"), new Integer(20));
        assertEquals(requestObj.getDimensions().get("y2"), new Integer(25));
        assertEquals(requestObj.getDimensions().get("x2"), new Integer(15));
    }

    @Test(expected = CanvasBoundaryBreachedException.class)
    public void givenLineRequestBreachingCanvas_ThenShouldThrowException() throws Exception {
        String input = "L 10 20 10 25";
        Canvas canvas = mock(Canvas.class);
        when(canvas.getWidth()).thenReturn(10);
        when(canvas.getHeight()).thenReturn(10);

        unit.parseAndValidateInput(input, canvas);
        verify(canvas).getWidth();
    }

    @Test
    public void givenValidBucketFillRequestWithinCanvas_ThenShouldGiveRequestObject() throws Exception {
        String input = "B 15 25 i";
        Canvas canvas = mock(Canvas.class);
        when(canvas.getWidth()).thenReturn(100);
        when(canvas.getHeight()).thenReturn(200);

        Request requestObj = unit.parseAndValidateInput(input, canvas);

        assertNotNull(requestObj);
        assertEquals(RequestType.BUCKET_FILL, requestObj.getRequestType());
        assertEquals(requestObj.getDimensions().get("x1"), new Integer(15));
        assertEquals(requestObj.getDimensions().get("y1"), new Integer(25));
        assertEquals(requestObj.getColor(), "i");
        verify(canvas).getWidth();
        verify(canvas).getHeight();
    }

    @Test(expected = SemanticsIncorrectException.class)
    public void givenInvalidBucketFillInput_ThenShouldThrowException() throws Exception {
        String input = "b 40 10";
        unit.parseAndValidateInput(input, null);
    }

    @Test(expected = CanvasUndefinedException.class)
    public void givenInvalidLineInputWithoutCanvas_ThenShouldThrowException() throws Exception {
        String input = "l 40 10 50 10";

        unit.parseAndValidateInput(input, null);
    }

    @Test(expected = CanvasBoundaryBreachedException.class)
    public void givenRequestWithInvalidDimensions_ThenShouldThrowException() throws Exception {
        String input = "l 0 10 50 10";
        Canvas canvas = Mockito.mock(Canvas.class);

        unit.parseAndValidateInput(input, canvas);
    }

    @Test(expected = InvalidLineCoordinatesException.class)
    public void givenRequestWithInvalidCoordinates_ThenShouldThrowException() throws Exception {
        String input = "l 1 2 3 4";
        Canvas canvas = Mockito.mock(Canvas.class);

        unit.parseAndValidateInput(input, canvas);
    }

    @Test(expected = CanvasBoundaryBreachedException.class)
    public void givenRequestOutsideCanvas_ThenShouldThrowException() throws Exception {
        String input = "b 100 200 c";
        Canvas canvas = Mockito.mock(Canvas.class);

        unit.parseAndValidateInput(input, canvas);
        verify(canvas).getHeight();
        verify(canvas).getWidth();
    }
}
