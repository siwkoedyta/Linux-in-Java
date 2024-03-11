package fs.commands;

import fs.IllegalOperationException;
import fs.file_system.FsDirectory;

public class Cd implements Command {
    private final String args;

    public Cd(String args) {
        this.args = args;
    }

    public FsDirectory run(FsDirectory dir) {
        try {
            return dir.cd(args);
        } catch (IllegalOperationException e) {
            System.out.println("Error changing directory: " + e.getMessage());
            return dir;
        }
    }
}
