package fs.file_system;

public class FsFile implements FsNode {
    private String name;
    private String content;

    public FsFile(String name, String content) {
        validateName(name);
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public FsNode copy(String newName) {
        return new FsFile(newName, content);
    }
}
