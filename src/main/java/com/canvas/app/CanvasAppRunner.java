package com.canvas.app;

import com.canvas.app.service.IOrchestrationService;
import com.canvas.app.service.OrchestrationServiceImpl;

import java.util.Scanner;

public class CanvasAppRunner {

    public static void main(String[] args) {
        CanvasAppRunner runner = new CanvasAppRunner();
        runner.run();
    }

    private void run() {

        System.out.println("---Application started successfully !----");
        IOrchestrationService orchestrationService = new OrchestrationServiceImpl();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.println("-->> Command <<--");
                    String userInput = scanner.nextLine();
                    orchestrationService.execute(userInput);
                } catch (Exception e) {
                    System.out.println("########### Exception found ###############");
                    System.out.println(e.getMessage());
                    continue;
                }
            }
        }
    }

}
