package fs.commands;

import fs.IllegalOperationException;
import fs.file_system.FsDirectory;
import fs.file_system.FsPath;

public class Mv implements Command {
    private final String source;
    private final String destination;

    public Mv(String []args) {
        if(args.length != 2) throw new IllegalOperationException("Illegal args");
        this.source = args[0];
        this.destination = args[1];
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        var srcNode = dir.get(source).orElseThrow(() -> new IllegalOperationException("Src invalid"));
        var destDir = dir.getDestinationDir(destination);
        var newName = FsPath.getDestinationName(source, destination);
        destDir.put(srcNode.copy(newName));
        dir.rm(source);

        return dir;
    }
}
