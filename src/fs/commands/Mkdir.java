package fs.commands;

import fs.file_system.FsDirectory;

import java.util.Arrays;

public class Mkdir implements Command {
    private final String[] dirNames;

    public Mkdir(String[] args) {
        this.dirNames = args;
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        for (String dirName : dirNames) {
            if (dirName.contains("/")) {
                String[] pathElements = dirName.split("/");
                String targetDirName = pathElements[pathElements.length - 1];
                String dirPath = String.join("/", Arrays.copyOf(pathElements, pathElements.length - 1));
                FsDirectory targetDir = dir.cd(dirPath);
                targetDir.put(new FsDirectory(targetDirName));
            } else {
                dir.put(new FsDirectory(dirName));
            }
        }

        return dir;
    }
}