package fs.file_system;

import fs.IllegalOperationException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FsDirectory implements FsNode {
    private String name;
    private Map<String, FsNode> elements;
    private Optional<FsDirectory> parent;

    public FsDirectory(String name) {
        validateName(name);
        this.name = name;
        this.elements = new HashMap<>();
        this.parent = Optional.empty();
    }

    public String getAbsolutePath() {
        return parent.map(p -> p.getAbsolutePath().equals("/") ? "/" + name :
                p.getAbsolutePath() + "/" + name).orElse("/");
    }

    public String getName() {
        return name;
    }

    public Collection<FsNode> getElements() {
        return elements.values();
    }

    public static FsDirectory newRoot() {
        return new FsDirectory("");
    }

    public Optional<FsNode> get(String path) {
        if(path.startsWith("/")) return parent.map(p -> p.get(path)).orElseGet(() -> get(path.substring(1)));

        var elems = path.split("/", 2);
        var first = elems[0];
        if (first.equals("") || first.equals(".")) return Optional.of(this);
        if (first.equals("..")) return parent.map(p -> p);
        var elem = elements.get(first);
        if (elem == null) return Optional.empty();
        if (elem instanceof FsFile f && elems.length == 1) return Optional.of(f);
        if (elem instanceof FsDirectory d) return elems.length == 2 ? d.get(elems[1]) : Optional.of(d);
        else return Optional.empty();
    }

    public void put(FsNode node) {
        if (elements.containsKey(node.getName())) throw new IllegalOperationException("File exists");

        if (node instanceof FsDirectory d) d.setParent(this);
        elements.put(node.getName(), node);
    }

    public FsDirectory cd(String path) {
        return switch (get(path).orElseGet(() -> {
            throw new IllegalOperationException("Invalid path");
        })) {
            case FsDirectory d -> d;
            default -> throw new IllegalOperationException("Invalid path");
        };
    }

    public FsNode copy(String newName) {
        var newDir = new FsDirectory(newName);
        elements.values().forEach(e -> newDir.put(e.copy(e.getName())));
        return newDir;
    }

    public void rm(String name) {
        if (!elements.containsKey(name)) {
            throw new IllegalOperationException("File or directory not found");
        }

        FsNode node = get(name).orElseThrow(() -> new IllegalOperationException("File not exists"));

        if (node instanceof FsDirectory directoryToRemove) {
            if (!directoryToRemove.getElements().isEmpty()) {
                throw new IllegalOperationException("Cannot remove non-empty directory");
            }
        }

        elements.remove(name);
    }

    public FsDirectory getDestinationDir(String path) {
        return FsPath.getParent(path)
                .map(p -> get(p).orElseThrow(() -> new IllegalOperationException("Dest invalid")))
                .map(p -> {
                    if (p instanceof FsDirectory d) return d;
                    else {
                        throw new IllegalOperationException("Dest invalid");
                    }
                })
                .orElse(this);
    }

    private void setParent(FsDirectory parent) {
        this.parent = Optional.of(parent);
    }
}
