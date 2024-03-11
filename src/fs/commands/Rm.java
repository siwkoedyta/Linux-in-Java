package fs.commands;

import fs.file_system.FsDirectory;

import java.util.Arrays;

public class Rm implements Command {
    private final String args;

    public Rm(String args) {
        this.args = args;
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        var targetNames = args.split("\\s+");

        for (String targetName : targetNames) {
            if (targetName.contains("/")) {
                String[] pathElements = targetName.split("/");
                String target = pathElements[pathElements.length - 1];
                String dirPath = String.join("/", Arrays.copyOf(pathElements, pathElements.length - 1));
                FsDirectory targetDir = dir.cd(dirPath);
                targetDir.rm(target);
            } else {
                dir.rm(targetName);
            }
        }

        return dir;
    }
}
