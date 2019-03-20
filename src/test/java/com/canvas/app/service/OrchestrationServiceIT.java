package com.canvas.app.service;

import com.canvas.app.config.AppConfig;
import com.canvas.app.exceptions.SemanticsIncorrectException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.canvas.app.service.OrchestrationServiceImpl.EXIT;
import static com.canvas.app.service.OrchestrationServiceImpl.SUCCESS;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class OrchestrationServiceIT {

    @Autowired
    OrchestrationServiceImpl unit;

    @Test(expected = SemanticsIncorrectException.class)
    public void givenInvalidInput_ThenShouldThrowException() throws Exception {
        unit.execute("garbage input");
    }

    @Test
    public void givenCanvasRequest_ThenShouldDrawCanvas() throws Exception {
        String response = unit.execute("C 40 15");
        assertEquals(SUCCESS, response);
    }

    @Test
    public void givenLineRequest_ThenShouldDrawLine() throws Exception {
        unit.execute("C 40 15");
        String response = unit.execute("L 5 3 15 3");
        assertEquals(SUCCESS, response);
    }

    @Test
    public void givenRectangleRequest_ThenShouldDrawRectangle() throws Exception {
        unit.execute("C 40 15");
        String response = unit.execute("R 5 3 15 7");
        assertEquals(SUCCESS, response);
    }

    @Test
    public void givenBucketFillRequest_ThenShouldBucketFill() throws Exception {
        unit.execute("C 40 15");
        unit.execute("R 5 3 15 7");
        String response = unit.execute("B 6 4 i");
        assertEquals(SUCCESS, response);
    }

    @Test
    public void givenQuitRequest_ThenShouldExecuteSuccessfully() throws Exception {
        String response = unit.execute("Q");
        assertEquals(EXIT, response);
    }

}
