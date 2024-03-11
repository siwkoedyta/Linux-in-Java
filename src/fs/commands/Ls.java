package fs.commands;

import fs.IllegalOperationException;
import fs.file_system.FsDirectory;
import fs.file_system.FsFile;
import fs.file_system.FsNode;

public class Ls implements Command {
    private final String dest;

    public Ls(String[] args) {
        if (args.length > 1) throw new IllegalOperationException("Invalid args");
        this.dest = args.length == 0 ? "." : args[0];
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        var node = dir.get(dest)
                .orElseThrow(() -> new IllegalOperationException("Invalid dest"));

        switch (node) {
            case FsDirectory d -> d.getElements().forEach(e -> System.out.println(line(e)));
            default -> new IllegalOperationException("Destination is not directory");
        }

        return dir;
    }

    private String line(FsNode node) {
        return node.getName() + " " +
                switch (node) {
                    case FsDirectory d -> "(dir)";
                    case FsFile f -> "(file)";
                    default -> "";
                };
    }
}
