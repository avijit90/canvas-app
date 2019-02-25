package com.canvas.app.service;

import com.canvas.app.model.Canvas;
import com.canvas.app.model.Line;
import com.canvas.app.model.Request;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    public void givenLine_ThenShouldDrawLine() {
        Request canvasRequest = mock(Request.class);
        when(canvasRequest.getDimensions()).thenReturn(ImmutableMap.of("x1", 40, "y1", 10));
        Request lineRequest = mock(Request.class);
        when(lineRequest.getDimensions()).thenReturn(ImmutableMap.of("x1", 10, "y1", 5, "x2", 20, "y2", 5));
        Canvas canvas = new Canvas(canvasRequest);

        unit.renderCanvas(canvas);
        canvas.addShape(new Line(lineRequest));

        unit.drawShapes(canvas);
    }

}
