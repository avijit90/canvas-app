package com.canvas.app.service;

import com.canvas.app.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.ImmutableMap.of;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class ShapeFactoryTest {

    @InjectMocks
    ShapeFactory unit;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test
    public void givenLineRequest_ThenShouldCreateLine() {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.LINE);
        when(request.getDimensions()).thenReturn(of("x1", 10, "y1", 4, "x2", 20, "y2", 8));
        Shape shape = unit.createShape(request);

        assertNotNull(shape);
        assertEquals(request, shape.getRequest());
        assertTrue(shape instanceof Line);
    }

    @Test
    public void givenRectangleRequest_ThenShouldCreateRectangle() {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(RequestType.RECTANGLE);
        when(request.getDimensions()).thenReturn(of("x1", 10, "y1", 4, "x2", 20, "y2", 8));
        Shape shape = unit.createShape(request);

        assertNotNull(shape);
        assertEquals(request, shape.getRequest());
        assertTrue(shape instanceof Rectangle);
    }

    @Test
    public void givenGarbageRequest_ThenShouldNotCreateShape() {
        Request request = mock(Request.class);
        when(request.getRequestType()).thenReturn(null);
        Shape shape = unit.createShape(request);

        assertNull(shape);
    }

}
