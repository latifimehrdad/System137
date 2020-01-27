package com.ngra.system137.models;

public class ModelChooseFiles {

    String FileName;

    int type;

    int Size;

    public ModelChooseFiles(String fileName, int type, int size) {
        FileName = fileName;
        this.type = type;
        Size = size;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
