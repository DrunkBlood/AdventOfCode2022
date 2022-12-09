package drunkblood.day7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VirtualFile {

    enum FileType{
        DIR,
        FILE;

        @Override
        public String toString() {
            return this == DIR ? "dir" : "file";
        }
    }
    private final String name;
    private final FileType type;
    private long fileSize;
    private VirtualFile parent;

    private List<VirtualFile> children = new ArrayList<>();

    private VirtualFile(String name, long size, FileType type){
        this.name = name;
        this.type = type;
        this.fileSize = size;
    }

    public VirtualFile(String name, long size){
        this(name, size, FileType.FILE);
    }
    public VirtualFile(String name){
        this(name, 0, FileType.DIR);
    }

    public void addChild(VirtualFile child) {
        long previousSize = this.fileSize;
        this.fileSize += child.getFileSize();
        this.children.add(child);
        if(this.parent != null){
            parent.updateChild(previousSize, this.fileSize);
        }
        child.setParent(this);
    }

    public void updateChild(long oldSize, long newSize){
        long previousSize = this.fileSize;
        this.fileSize -= oldSize;
        this.fileSize += newSize;
        if(parent != null) parent.updateChild(previousSize, this.fileSize);
    }

    public VirtualFile getChildrenByName(String name){
        return children.stream().filter(children -> children.getName().equals(name)).findAny().orElse(null);
    }

    public long getFileSize() {
        return fileSize;
    }

    public FileType getType() {
        return type;
    }

    public VirtualFile getParent() {
        return parent;
    }

    public void setParent(VirtualFile parent){
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public List<VirtualFile> getChildren() {
        return Collections.unmodifiableList(this.children);
    }

    @Override
    public String toString() {
        return "VirtualFile{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", fileSize=" + fileSize +
                '}';
    }
}
