package Model.ADT;

import Model.Value.StringValue;

import java.io.BufferedReader;
import java.util.HashMap;

public interface FileTableInterface {
    void addFile(StringValue fileName, BufferedReader fileDescriptor);
    void removeFile(StringValue fileName);
    BufferedReader getFileDescriptor(StringValue filename);

    HashMap<StringValue, BufferedReader> getContent();

    String toString();

    boolean isOpened(StringValue fileName);
}
