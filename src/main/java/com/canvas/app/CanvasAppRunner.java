package com.canvas.app;

import com.canvas.app.config.AppConfig;
import com.canvas.app.service.IOrchestrationService;
import com.canvas.app.service.OrchestrationServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Scanner;

import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class CanvasAppRunner {

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
                try {
                    System.out.print("Enter command : ");
                    String userInput = scanner.nextLine();
                    String output = orchestrationService.execute(userInput);

                    if (equalsIgnoreCase("Exit", output))
                        break;

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
            }
        } finally {
            System.out.println("--- Shutting Down ! -----");
        }
    }

}
