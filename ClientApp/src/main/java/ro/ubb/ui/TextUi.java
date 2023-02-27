package ro.ubb.ui;

import lombok.AllArgsConstructor;
import ro.ubb.service.ClientService;

import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class TextUi {
    private final Scanner scanner;
    private final ClientService clientService;

    public void start() {
        boolean inMenu = true;

        while (inMenu) {
            printMenu();
            String fullCommand = scanner.nextLine();
            List<String> commandParts = List.of(fullCommand.split(" "));

            switch (commandParts.get(0)) {
                case "sum", "product", "divisible", "divisible2" -> {
                    String result = clientService.getResultFromServer(fullCommand);
                    System.out.println(result);
                }
                case "exit" -> inMenu = false;
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void printMenu() {
        System.out.print("""
                     
                AVAILABLE COMMANDS
         --------------------------------
         sum [arg1,arg2,...]
         product [arg1,arg2,...]
         divisible [arg1,arg2,arg3]
         divisible2 [arg1,arg2,arg3]
         exit
         --------------------------------
            ~\s""");
    }
}