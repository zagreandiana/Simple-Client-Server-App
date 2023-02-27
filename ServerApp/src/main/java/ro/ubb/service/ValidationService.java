package ro.ubb.service;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ValidationService {
    public boolean validateArgumentsType(List<String> commandParts) {
        if (commandParts.size() != 2) {
            return false;
        }

        List<String> argumentParts = List.of(commandParts.get(1).trim().split(","));

        for (String part : argumentParts) {
            if (!StringUtils.isNumeric(part)) {
                return false;
            }
        }

        return true;
    }

    public boolean validateArgumentsByFeature(String command, List<Integer> arguments) {
        if (arguments.isEmpty()) {
            return false;
        }

        return switch (command) {
            case "sum" -> validateForSum(arguments);
            case "product" -> validateForProduct(arguments);
            case "divisible" -> validateForDivisible(arguments);
            case "divisible2" -> validateForDivisible2(arguments);
            default -> false;
        };
    }

    private boolean validateForSum(List<Integer> arguments) {
        return arguments.stream()
                .allMatch(x -> x > 0);
    }

    private boolean validateForProduct(List<Integer> arguments) {
        return arguments.stream()
                .noneMatch(x -> x == 0);
    }

    private boolean validateForDivisible(List<Integer> arguments) {
        int lowerLimit = arguments.get(0);
        int upperLimit = arguments.get(1);
        int divider = arguments.get(2);
        return (divider > 0 && lowerLimit >= 0 && upperLimit > 0) && lowerLimit < upperLimit;
    }

    private boolean validateForDivisible2(List<Integer> arguments) {
        int lowerLimit = arguments.get(0);
        int upperLimit = arguments.get(1);
        int divider = arguments.get(2);

        return lowerLimit >= 10 && lowerLimit <= upperLimit && divider != 0;
    }
}