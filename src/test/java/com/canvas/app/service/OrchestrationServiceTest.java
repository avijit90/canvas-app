package com.canvas.app.service;

import com.canvas.app.exceptions.EmptyInputException;
import com.canvas.app.model.Request;
import com.canvas.app.util.RequestProcessor;
import com.canvas.app.util.RuleEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
public class OrchestrationServiceTest {

    @InjectMocks
    OrchestrationServiceImpl unit;

    @Mock
    RuleEngine ruleEngine;

    @Mock
    RequestProcessor requestProcessor;

    @Before
    public void setup() {
        initMocks(this);
    }

    @Test(expected = EmptyInputException.class)
    public void givenEmptyInput_ThenShouldThrowException() throws Exception {
        String input = " ";
        unit.execute(input);
    }

    @Test
    public void givenAValidQuitInput_ThenShouldExecute() throws Exception {
        String input = "q ";
        Request request = mock(Request.class);
        when(ruleEngine.parseUserInput(input, null)).thenReturn(request);
        when(requestProcessor.quitProgram(request)).thenReturn(true);

        String result = unit.execute(input);

        assertNotNull(result);
        assertEquals("Exit", result);
        verify(ruleEngine).parseUserInput(input, null);
        verify(requestProcessor).quitProgram(request);
        verify(requestProcessor, times(0)).processRequest(request, null);
    }

    @Test
    public void givenAValidDrawInput_ThenShouldExecute() throws Exception {
        String input = "l 1 2 3 2";
        Request request = mock(Request.class);
        when(ruleEngine.parseUserInput(input, null)).thenReturn(request);
        when(requestProcessor.quitProgram(request)).thenReturn(false);

        String result = unit.execute(input);

        assertNotNull(result);
        assertEquals("Success", result);
        verify(ruleEngine).parseUserInput(input, null);
        verify(requestProcessor).quitProgram(request);
        verify(requestProcessor).processRequest(request, null);
    }
}
