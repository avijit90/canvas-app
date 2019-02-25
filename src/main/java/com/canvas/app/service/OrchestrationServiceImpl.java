package com.canvas.app.service;

import com.canvas.app.exceptions.EmptyInputException;
import com.canvas.app.exceptions.InvalidRequestTypeException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.util.RequestProcessor;
import com.canvas.app.util.RuleEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class OrchestrationServiceImpl implements IOrchestrationService {

    @Autowired
    RuleEngine ruleEngine;

    @Autowired
    RequestProcessor requestProcessor;

    Canvas canvas = null;

    @Override
    public String execute(String input) throws Exception {
        checkEmptyInput(input);
        Request request = parse(input, canvas);

        if (requestProcessor.quitProgram(request))
            return "Exit";

        processRequest(request);
        return null;
    }

    private void checkEmptyInput(String userInput) throws EmptyInputException {
        if (isEmpty(userInput))
            throw new EmptyInputException("Input cannot be blank. Please enter some text");
    }

    private Request parse(String input, Canvas canvas) throws Exception {
        return ruleEngine.parseUserInput(input, canvas);
    }

    private void processRequest(Request request) throws InvalidRequestTypeException {
        canvas = requestProcessor.processRequest(request, canvas);
    }


}
