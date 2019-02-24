package com.canvas.app.service;

import com.canvas.app.exceptions.EmptyInputException;
import com.canvas.app.exceptions.InvalidRequestTypeException;
import com.canvas.app.model.Canvas;
import com.canvas.app.model.Request;
import com.canvas.app.util.RequestProcessor;
import com.canvas.app.util.RuleEngine;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class OrchestrationServiceImpl implements IOrchestrationService {

    RuleEngine ruleEngine = new RuleEngine();
    RequestProcessor requestProcessor = new RequestProcessor();
    Canvas canvas = null;

    @Override
    public void execute(String input) throws Exception {
        checkEmptyInput(input);
        Request request = parse(input, canvas);
        processRequest(request);
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
