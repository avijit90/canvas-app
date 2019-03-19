package com.canvas.app.util;

import com.canvas.app.model.Canvas;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static com.canvas.app.util.DrawingHelper.createAndFillFreshMatrix;
import static java.util.Arrays.stream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DrawingHelperTest {

    @Test
    public void givenValidCanvas_ThenShouldCreateAndFillNewMatrix() {
        Canvas canvas = Mockito.mock(Canvas.class);
        when(canvas.getWidth()).thenReturn(5);
        when(canvas.getHeight()).thenReturn(5);

        String[][] response = createAndFillFreshMatrix(canvas);

        assertNotNull(response);
        verify(canvas, times(2)).getWidth();
        verify(canvas, times(2)).getHeight();
        stream(response).forEach(row -> stream(row)
                .forEach(element -> assertEquals(" ", element)));
    }
}