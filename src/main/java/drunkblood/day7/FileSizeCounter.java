package drunkblood.day7;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class FileSizeCounter {

    public static void main(String[] args) throws URISyntaxException, IOException {
        URL resource = FileSizeCounter.class.getResource("data.log");
        File dataFile = new File(resource.toURI());
        try(BufferedReader dataReader = new BufferedReader(new FileReader(dataFile))){
            // dir parsing
            String currentCommandLine;
            VirtualFile root = new VirtualFile("/");
            VirtualFile currentDir = root;
            while ((currentCommandLine = dataReader.readLine()) != null) {
                String[] dataIn = currentCommandLine.split(" ");
                if (Objects.equals(dataIn[0], "$") &&
                        Objects.equals(dataIn[1], "ls")) {
                    // skip ls
                    continue;
                } else if (Objects.equals(dataIn[0], "$") &&
                        Objects.equals(dataIn[2], "..")) {
                    // go up
                    currentDir = currentDir.getParent();
                } else if (Objects.equals(dataIn[0], "$") &&
                        Objects.equals(dataIn[2], "/")) {
                    // change to root
                    currentDir = root;
                } else if (Objects.equals(dataIn[0], "$")) {
                    // change to child
                    currentDir = currentDir.getChildrenByName(dataIn[2]);
                } else if (Objects.equals(dataIn[0], "dir")) {
                    // found new dir
                    VirtualFile newDir = new VirtualFile(dataIn[1]);
                    currentDir.addChild(newDir);
                } else {
                    // found new file
                    VirtualFile newFile = new VirtualFile(dataIn[1], Long.parseLong(dataIn[0]));
                    currentDir.addChild(newFile);
                }
            }

            // traverse tree
            Queue<VirtualFile> dirs = new LinkedList<>();
            dirs.add(root);
            long sum = 0;
            long smallest = Long.MAX_VALUE;
            long freeSpace = 70000000 - root.getFileSize();
            long neededFree = 30000000;
            while ( !dirs.isEmpty()){
                currentDir = dirs.remove();
                long fileSize = currentDir.getFileSize();
                if(fileSize <= 100000){
                    sum += fileSize;
                }
                long possibleFreeSpace = freeSpace + fileSize;
                if(possibleFreeSpace > neededFree){
                    smallest = Math.min(smallest, fileSize);
                }

                currentDir.getChildren().stream()
                        .filter(virtualFile -> VirtualFile.FileType.DIR.equals(virtualFile.getType()))
                        .forEach(dirs::add);
            }
            System.out.println(sum);
            System.out.println(smallest);
        }
    }
}
