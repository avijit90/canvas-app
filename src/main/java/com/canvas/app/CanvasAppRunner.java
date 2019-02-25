package com.canvas.app;

import com.canvas.app.config.AppConfig;
import com.canvas.app.service.IOrchestrationService;
import com.canvas.app.service.OrchestrationServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class CanvasAppRunner {

    final static Logger logger = Logger.getLogger(CanvasAppRunner.class);

    private IOrchestrationService orchestrationService;

    public static void main(String[] args) {
        CanvasAppRunner runner = new CanvasAppRunner();
        runner.run();
    }

    private void run() {

        System.out.println("---Application started successfully !----");
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        orchestrationService = context.getBean(OrchestrationServiceImpl.class);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                String userInput = null;
                try {
                    System.out.print("Enter command : ");
                    userInput = scanner.nextLine();
                    String output = orchestrationService.execute(userInput);

                    if (equalsIgnoreCase("Exit", output))
                        break;

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    logger.error("______________________");
                    logger.error("input =>"+userInput);
                    logger.error(e.getMessage(), e);
                    continue;
                }
            }
        } finally {
            System.out.println("--- Shutting Down ! -----");
        }
    }

}
