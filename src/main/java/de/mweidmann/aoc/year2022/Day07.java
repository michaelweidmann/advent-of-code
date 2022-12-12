package de.mweidmann.aoc.year2022;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.function.Predicate;

/**
 * Contains the solutions to the tasks from 07.12.2022.
 *
 * @author mweidmann
 */
public class Day07 extends AbstractDay2022 {

    /**
     * Default constructor for Day07.
     */
    public Day07() {
        super(7);
    }

    @Override
    protected Object partOne() {
        var rootDirectory = parseInput();

        // Collect all directories with the maximum size of 100_000
        var set = new HashSet<Directory>();
        getDirectoriesWhichMatchPredicate(rootDirectory, set, directorySize -> directorySize <= 100_000);

        return set.stream()
                .mapToLong(Directory::getDirectorySize)
                .sum();
    }

    @Override
    protected Object partTwo() {
        var rootDirectory = parseInput();

        final var totalDiskSpace = 70_000_000;
        final var neededDiskSpaceForUpdate = 30_000_000;
        final var spaceToBeCleaned = neededDiskSpaceForUpdate - (totalDiskSpace - rootDirectory.getDirectorySize());

        // Collect all directories which are minimum the size of spaceToBeCleaned
        var set = new HashSet<Directory>();

        getDirectoriesWhichMatchPredicate(rootDirectory, set, directorySize -> directorySize >= spaceToBeCleaned);
        return set.stream()
                .mapToLong(Directory::getDirectorySize)
                .min()
                .orElseThrow();
    }

    private void getDirectoriesWhichMatchPredicate(Directory directory, Set<Directory> set, Predicate<Long> predicate) {
        if (predicate.test(directory.getDirectorySize())) {
            set.add(directory);
        }

        for (var childDirectory : directory.getChildDirectories().values()) {
            getDirectoriesWhichMatchPredicate(childDirectory, set, predicate);
        }
    }

    private Directory parseInput() {
        var rootDirectory = new Directory(null);
        var currentDirectory = rootDirectory;

        for (var line : this.INPUT) {
            if (line.startsWith("$ ")) {
                currentDirectory = parseCommand(rootDirectory, currentDirectory, line);
            } else if (line.startsWith("dir ")) {
                parseDirectory(currentDirectory, line);
            } else {
                parseFile(currentDirectory, line);
            }
        }
        return rootDirectory;
    }

    private void parseFile(Directory currentDirectory, String fileInformationLine) {
        // Contains file size and file name
        var fileInformation = fileInformationLine.split(" ");
        currentDirectory.addFile(fileInformation[1], Long.parseLong(fileInformation[0]));
    }

    private void parseDirectory(Directory currentDirectory, String directoryInformationLine) {
        var directoryName = directoryInformationLine.replace("dir ", "");
        currentDirectory.addDirectory(directoryName);
    }

    private Directory parseCommand(Directory rootDirectory, Directory currentDirectory, String command) {
        command = command.replace("$ ", "");

        if (command.equals("ls")) {
            return currentDirectory;
        }

        var destination = command.replace("cd ", "");

        return switch (destination) {
            case "/" -> rootDirectory;
            case ".." -> currentDirectory.getParentDirectory();
            default -> currentDirectory.getChildDirectory(destination)
                    .orElseGet(() -> currentDirectory.addDirectory(destination));
        };
    }

    @Getter
    @AllArgsConstructor
    private static class Directory {
        private final Set<String> fileNames;
        private final Directory parentDirectory;
        private final Map<String, Directory> childDirectories;
        private long directorySize;

        private Directory(Directory parentDirectory) {
            this(new HashSet<>(), parentDirectory, new HashMap<>(), 0L);
        }

        private Optional<Directory> getChildDirectory(String name) {
            return Optional.ofNullable(childDirectories.get(name));
        }

        private Directory getParentDirectory() {
            return parentDirectory;
        }

        private Directory addDirectory(String name) {
            if (childDirectories.containsKey(name)) {
                return childDirectories.get(name);
            }
            return childDirectories.put(name, new Directory(this));
        }

        private void addFile(String fileName, long size) {
            if (fileNames.contains(fileName)) {
                return;
            }

            fileNames.add(fileName);
            this.directorySize += size;

            var iterator = this.parentDirectory;
            while (iterator != null) {
                iterator.directorySize += size;
                iterator = iterator.parentDirectory;
            }
        }
    }
}
