package com.canvas.app.util;

import com.canvas.app.exceptions.InvalidRequestTypeException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.RequestType;
import com.canvas.app.model.Shape;
import com.canvas.app.service.IDrawingService;
import com.canvas.app.service.ShapeFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class RequestProcessorTest {

    @InjectMocks
    RequestProcessor unit;

    @Mock
    ShapeFactory shapeFactory;

    @Mock
    IDrawingService paintService;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenQuitRequest_ThenShouldShutdownProgram() {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.QUIT);

        boolean response = unit.quitProgram(request);

        assertTrue(response);
        verifyZeroInteractions(shapeFactory);
        verifyZeroInteractions(paintService);
    }

    @Test
    public void givenCanvasRequest_ThenShouldProcess() throws InvalidRequestTypeException {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.CANVAS);
        int expectedWidth = 10;
        int expectedHeight = 20;
        when(request.getDimensions()).thenReturn(of("x1", expectedWidth, "y1", expectedHeight));

        Canvas response = unit.processRequest(request, null);

        assertNotNull(response);
        assertEquals(response.getWidth(), expectedWidth);
        assertEquals(response.getHeight(), expectedHeight);
        verifyZeroInteractions(shapeFactory);
        verify(paintService).renderCanvas(any(Canvas.class));
    }

    @Test
    public void givenLineRequest_ThenShouldProcess() throws InvalidRequestTypeException {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.LINE);
        Canvas canvas = mock(Canvas.class);
        Shape shape = mock(Shape.class);
        when(shapeFactory.createShape(request)).thenReturn(shape);

        Canvas response = unit.processRequest(request, canvas);

        assertNotNull(response);
        verify(canvas).addShape(eq(shape));
        verify(shapeFactory).createShape(eq(request));
        verify(paintService).drawShapes(eq(canvas));
        verify(paintService).renderCanvas(eq(canvas));
    }

    @Test
    public void givenRectangleRequest_ThenShouldProcess() throws InvalidRequestTypeException {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.RECTANGLE);
        Canvas canvas = mock(Canvas.class);
        Shape shape = mock(Shape.class);
        when(shapeFactory.createShape(request)).thenReturn(shape);

        Canvas response = unit.processRequest(request, canvas);

        assertNotNull(response);
        verify(canvas).addShape(eq(shape));
        verify(shapeFactory).createShape(eq(request));
        verify(paintService).drawShapes(eq(canvas));
        verify(paintService).renderCanvas(eq(canvas));
    }

    @Test
    public void givenBucketFillRequest_ThenShouldProcess() throws InvalidRequestTypeException {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.BUCKET_FILL);
        Canvas canvas = mock(Canvas.class);

        Canvas response = unit.processRequest(request, canvas);

        assertNotNull(response);
        verify(paintService).bucketFill(eq(canvas), eq(request));
        verify(paintService).renderCanvas(eq(canvas));
    }

    @Test(expected = InvalidRequestTypeException.class)
    public void givenInvalidRequestType_ThenShouldThrowException() throws InvalidRequestTypeException {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(null);
        Canvas canvas = mock(Canvas.class);

        unit.processRequest(request, canvas);
    }
}
