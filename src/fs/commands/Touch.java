package fs.commands;

import fs.file_system.FsDirectory;
import fs.file_system.FsFile;

import java.util.Arrays;

public class Touch implements Command {
    private final String fileName;
    private final String content;

    public Touch(String args) {
        var argsArray = args.split("\\s+");
        if (argsArray.length < 1) {
            throw new IllegalArgumentException("Usage: touch <file_name> [<content>...]");
        }

        this.fileName = argsArray[0];
        this.content = argsArray.length > 1 ? String.join(" ", Arrays.copyOfRange(argsArray, 1, argsArray.length)) : "";
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        if (fileName.contains("/")) {
            String[] pathElements = fileName.split("/");
            String file = pathElements[pathElements.length - 1];
            String dirPath = String.join("/", Arrays.copyOf(pathElements, pathElements.length - 1));
            FsDirectory targetDir = dir.cd(dirPath);
            targetDir.put(new FsFile(file, content));
        } else {
            dir.put(new FsFile(fileName, content));
        }

        return dir;
    }
}