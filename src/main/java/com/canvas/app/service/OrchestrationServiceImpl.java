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

    public static final String EXIT = "Exit";
    public static final String SUCCESS = "Success";

    @Autowired
    private RuleEngine ruleEngine;

    @Autowired
    private RequestProcessor requestProcessor;

    private Canvas canvas = null;

    @Override
    public String execute(String input) throws Exception {
        checkEmptyInput(input);
        Request request = parse(input, canvas);

        if (requestProcessor.quitProgram(request))
            return EXIT;

        processRequest(request);
        return SUCCESS;
    }

    private void checkEmptyInput(String userInput) throws EmptyInputException {
        if (isEmpty(userInput.trim()))
            throw new EmptyInputException("Input cannot be blank. Please enter some text");
    }

    private Request parse(String input, Canvas canvas) throws Exception {
        return ruleEngine.parseUserInput(input, canvas);
    }

    private void processRequest(Request request) throws InvalidRequestTypeException {
        canvas = requestProcessor.processRequest(request, canvas);
    }


}
