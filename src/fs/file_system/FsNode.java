package fs.file_system;

import fs.IllegalOperationException;

public interface FsNode {
  String getName();

  FsNode copy(String newName);

  default void validateName(String path) {
    if (path.contains("$")) throw new IllegalOperationException("Path invalid");
  }
}
