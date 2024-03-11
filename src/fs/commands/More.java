package fs.commands;
import fs.IllegalOperationException;
import fs.file_system.FsDirectory;
import fs.file_system.FsFile;


public class More implements Command {
    private final String filename;

    public More(String filename) {
        if (filename == null || filename.isBlank()) {
            throw new IllegalOperationException("Invalid filename");
        }
        this.filename = filename;
    }

    @Override
    public FsDirectory run(FsDirectory dir) {
        var node = dir.get(filename)
                .orElseThrow(() -> new IllegalOperationException("File not found: " + filename));

        if (node instanceof FsFile file) {
            System.out.println(file.getContent());
        } else {
            throw new IllegalOperationException("Cannot use 'more' on a directory: " + filename);
        }

        return dir;
    }
}
