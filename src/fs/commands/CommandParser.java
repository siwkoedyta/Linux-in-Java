package fs.commands;

import fs.ExitException;
import fs.IllegalOperationException;

public class CommandParser {
    public Command parse(String line) {
        var splitted = line.split("\\s+", 2);
        var command = splitted[0];
        var args = splitted.length > 1 ? splitted[1] : "";
        var cpArgs = args.split("\\s+");

        if (( "rm".equals(command)) && args.isBlank()
                || (("cp".equals(command) || "mv".equals(command)) && args.split("\\s+").length < 2)
                || ("mkdir".equals(command) && args.isBlank())) {
            throw new IllegalOperationException("Invalid arguments for command: " + command);
        }

        return switch (command) {
            case "cd" -> new Cd(args);
            case "ls" -> new Ls(cpArgs);
            case "touch" -> new Touch(args);
            case "mkdir" -> new Mkdir(cpArgs);
            case "rm" -> new Rm(args);
            case "cp" -> new Cp(cpArgs[0], cpArgs[1]);
            case "mv" -> new Mv(cpArgs);
            case "more" -> new More(cpArgs[0]);
            case "exit" -> throw new ExitException("exit");
            default -> throw new IllegalOperationException("Command illegal, command=" + command);
        };
    }
}
