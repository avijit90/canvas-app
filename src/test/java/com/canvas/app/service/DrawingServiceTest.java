package com.canvas.app.service;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.model.Shape;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class DrawingServiceTest {

    @InjectMocks
    DrawingServiceImpl unit;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenBlankCanvas_ThenShouldRenderNewCanvas() {
        Request request = mock(Request.class);
        when(request.getDimensions()).thenReturn(ImmutableMap.of("x1", 40, "y1", 10));
        Canvas canvas = new Canvas(request);

        unit.renderCanvas(canvas);
        assertNotNull(canvas.getMatrix());
    }

    @Test
    public void givenLineRequest_ThenShouldDrawLine() {
        Canvas canvas = mock(Canvas.class);
        Shape shape = mock(Shape.class);
        canvas.addShape(shape);

        unit.drawShapes(canvas);

        verify(shape, times(canvas.getShapes().size())).draw(canvas);
    }

    @Test
    public void givenBucketFillRequest_ThenShouldBucketFill() {
        Canvas canvas = mock(Canvas.class);
        when(canvas.getWidth()).thenReturn(40);
        when(canvas.getHeight()).thenReturn(10);
        when(canvas.getMatrix()).thenReturn(new String[10][40]);
        Request request = mock(Request.class);
        when(request.getDimensions()).thenReturn(ImmutableMap.of("x1", 40, "y1", 10));
        when(request.getColor()).thenReturn("c");

        unit.bucketFill(canvas, request);

        verify(canvas).getWidth();
        verify(canvas).getHeight();
        verify(canvas).getMatrix();
        verify(request, times(2)).getDimensions();
        verify(request).getColor();
        verify(canvas).setMatrix(any());
    }

}
