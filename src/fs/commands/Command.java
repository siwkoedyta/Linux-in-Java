package fs.commands;

import fs.file_system.FsDirectory;

public interface Command {
    FsDirectory run(FsDirectory dir);
}
