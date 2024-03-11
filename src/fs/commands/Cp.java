package fs.commands;

import fs.IllegalOperationException;
import fs.file_system.FsDirectory;
import fs.file_system.FsPath;

public class Cp implements Command {
    private final String source;
    private final String destination;

    public Cp(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        var srcNode = dir.get(source).orElseThrow(() -> new IllegalOperationException("Src invalid"));
        var destDir = dir.getDestinationDir(destination);
        var newName = FsPath.getDestinationName(source, destination);
        destDir.put(srcNode.copy(newName));

        return dir;
    }
}
