package com.canvas.app.service;

import com.canvas.app.model.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
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
    public void givenShapeRequest_ThenShouldDrawShape() {
        Canvas canvas = mock(Canvas.class);
        Shape shape = mock(Shape.class);
        when(canvas.getShapes()).thenReturn(Lists.newArrayList(shape));

        unit.drawShapes(canvas);

        verify(shape, times(canvas.getShapes().size())).draw(canvas);
        verify(canvas, times(3)).getShapes();
        canvas.getShapes().forEach(s -> verify(s).draw(eq(canvas)));
    }

    @Test
    public void givenLineRequest_ThenShouldDrawLine() {
        Request canvasRequest = new Request.RequestBuilder().requestType("C")
                .dimensions(ImmutableMap.of("x1", 5, "y1", 5)).build();
        Request lineRequest = new Request.RequestBuilder().requestType("L")
                .dimensions(ImmutableMap.of("x1", 1, "y1", 3, "x2", 3, "y2", 3)).build();
        Canvas canvas = new Canvas(canvasRequest);
        canvas.setMatrix(new String[5][5]);
        Line line = new Line(lineRequest);
        canvas.addShape(line);

        unit.drawShapes(canvas);

        assertEquals(canvas.getMatrix()[2][0], "X");
        assertEquals(canvas.getMatrix()[2][1], "X");
        assertEquals(canvas.getMatrix()[2][2], "X");
        assertNull(canvas.getMatrix()[2][3], null);
        assertNull(canvas.getMatrix()[2][4], null);

        for (int i = 0; i < canvas.getMatrix().length; i++) {
            if (i == 2)
                continue;//elements of row 2 and 3 verified above.
            Arrays.stream(canvas.getMatrix()[i]).forEach(e -> assertNull(e));
        }
    }

    @Test
    public void givenRectangleRequest_ThenShouldDrawRectangle() {
        Request canvasRequest = new Request.RequestBuilder().requestType("C")
                .dimensions(ImmutableMap.of("x1", 5, "y1", 5)).build();
        Request rectangleRequest = new Request.RequestBuilder().requestType("R")
                .dimensions(ImmutableMap.of("x1", 1, "y1", 3, "x2", 3, "y2", 4)).build();
        Canvas canvas = new Canvas(canvasRequest);
        canvas.setRequest(canvasRequest);
        canvas.setMatrix(new String[5][5]);
        Rectangle rectangle = new Rectangle(rectangleRequest);
        canvas.addShape(rectangle);

        unit.drawShapes(canvas);

        assertEquals(canvas.getRequest(), canvasRequest);
        assertEquals(canvas.getMatrix()[2][0], "X");
        assertEquals(canvas.getMatrix()[2][1], "X");
        assertEquals(canvas.getMatrix()[2][2], "X");
        assertEquals(canvas.getMatrix()[3][0], "X");
        assertEquals(canvas.getMatrix()[3][1], "X");
        assertEquals(canvas.getMatrix()[3][2], "X");
        assertNull(canvas.getMatrix()[2][3], null);
        assertNull(canvas.getMatrix()[2][4], null);
        assertNull(canvas.getMatrix()[3][3], null);
        assertNull(canvas.getMatrix()[3][4], null);

        for (int i = 0; i < canvas.getMatrix().length; i++) {
            if (i == 2 || i == 3)
                continue;//elements of row 2 and 3 verified above.
            Arrays.stream(canvas.getMatrix()[i]).forEach(e -> assertNull(e));
        }
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
