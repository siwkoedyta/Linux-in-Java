package fs;

import fs.commands.CommandParser;
import fs.file_system.FsDirectory;
import fs.file_system.FsFile;
import fs.file_system.FsPath;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        FsDirectory root = FsDirectory.newRoot();
        FsDirectory dev = new FsDirectory("dev");
        FsDirectory usr = new FsDirectory("usr");
        FsDirectory docs = new FsDirectory("docs");
        FsFile admin = new FsFile("admin", "Przkładowa zawartość pliku admin.");
        FsFile file = new FsFile("file.txt", "Przkładowa zawartość pliku file.txt.");

        root.put(dev);
        root.put(usr);
        root.put(docs);
        usr.put(admin);
        docs.put(file);

        FsDirectory currentDirectory = root;

        var scanner = new Scanner(System.in);
        var parser = new CommandParser();

        while (true) {
            System.out.print(currentDirectory.getAbsolutePath() + ">");
            var line = scanner.nextLine();
            try {
                currentDirectory = parser.parse(line).run(currentDirectory);
            } catch (ExitException e) {
                return;
            } catch (IllegalOperationException e) {
                System.out.println("Illegal operation: " + e.getMessage());
            }
        }

    }
}