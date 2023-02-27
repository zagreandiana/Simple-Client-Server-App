package ro.ubb.handler;

import ro.ubb.service.ValidationService;
import ro.ubb.utils.ErrorMessages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientHandler implements Runnable {

    private final Socket client;
    ValidationService validationService;

    public ClientHandler(Socket client, ValidationService validationService) {
        this.client = client;
        this.validationService = validationService;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
             OutputStream os = client.getOutputStream()) {

            String result = null;
            String fullCommand = br.readLine();
            List<String> commandParts = List.of(fullCommand.split(" "));

            boolean isNumeric = validationService.validateArgumentsType(commandParts);

            List<Integer> arguments = new ArrayList<>();

            if (isNumeric) {
                arguments = transformArgumentsToInteger(List.of(commandParts.get(1).split(",")));
            }

            boolean isValid = validationService.validateArgumentsByFeature(commandParts.get(0), arguments);

            if (isNumeric && isValid) {
                switch (commandParts.get(0)) {
                    case "sum" -> result = getSum(arguments);
                    case "product" -> result = getProduct(arguments);
                    case "divisible" -> result = getDivisible(arguments);
                    case "divisible2" -> result = getDivisible2(arguments);
                    default -> result = ErrorMessages.UNSUPPORTED_COMMAND.message();
                }
            }

            String finalResult = Optional.ofNullable(result).orElse(ErrorMessages.UNSUPPORTED_ARGS.message());
            os.write(prepareToSend(finalResult).getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getSum(List<Integer> arguments) {
       return  arguments.stream()
               .reduce(0, Integer::sum)
               .toString();
    }

    private String getProduct(List<Integer> arguments) {
        return arguments.stream()
                .reduce(1, (x, y) -> x * y)
                .toString();
    }

    private String getDivisible(List<Integer> arguments) {
        List<Integer> finalList = new ArrayList<>();
        int lowerLimit = arguments.get(0);
        int upperLimit = arguments.get(1);
        int divider = arguments.get(2);
        for (int i = lowerLimit; i < upperLimit; i++) {
            if (i % divider == 0) {
                finalList.add(i);
            }
        }

        return finalList.toString();
    }

    private String getDivisible2(List<Integer> arguments) {
        StringBuilder sb = new StringBuilder();
        int lowerLimit = arguments.get(0);
        int upperLimit = arguments.get(1);
        int divider = arguments.get(2);

        for (int i = lowerLimit; i < upperLimit; i++) {
            int lastTwoDigit = i % 100;
            if (i >= 10 && lastTwoDigit % divider == 0) {
                sb.append(i).append(",");
            }
        }

        sb.deleteCharAt(sb.lastIndexOf(","));

        return sb.toString();
    }

    private List<Integer> transformArgumentsToInteger(List<String> arguments) {
        return arguments.stream()
                .map(Integer::parseInt)
                .toList();
    }

    private String prepareToSend(String stringToBeSent) {
        return stringToBeSent + "\n";
    }
}