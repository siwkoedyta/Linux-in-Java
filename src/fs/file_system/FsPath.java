package fs.file_system;

import fs.IllegalOperationException;

import java.util.Optional;

public class FsPath {
    public static Optional<String> getParent(String path) {
        var lastIndex = path.lastIndexOf("/");
        return lastIndex > 0 ? Optional.of(path.subSequence(0, lastIndex).toString()) : Optional.empty();
    }

    public static Optional<String> getName(String path) {
        var lastIndex = path.lastIndexOf("/");
        if(lastIndex < 0) return Optional.of(path);
        if(lastIndex == path.length() -1) return Optional.empty();
        return Optional.of(path.subSequence(lastIndex + 1, path.length()).toString());
    }

    public static String getDestinationName(String srcPath, String destPath) {
        return getName(destPath).or(() -> getName(srcPath)).orElseThrow(() -> new IllegalOperationException("Dest invalid"));
    }
}
