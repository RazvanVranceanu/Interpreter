package Model.ADT;

import Model.Value.StringValue;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

public class FileTable implements FileTableInterface{

    private final HashMap<StringValue, BufferedReader> file;

    public FileTable(){
        this.file = new HashMap<StringValue, BufferedReader>();
    }

    @Override
    public void addFile(StringValue fileName, BufferedReader fileDescriptor) {
        file.put(fileName, fileDescriptor);
    }

    @Override
    public void removeFile(StringValue fileName) {
        file.remove(fileName);
    }

    @Override
    public BufferedReader getFileDescriptor(StringValue filename) {
        return file.get(filename);
    }

    @Override
    public boolean isOpened(StringValue fileName) {
        return file.containsKey(fileName);
    }

    @Override
    public HashMap<StringValue, BufferedReader> getContent() {
        return file;
    }

    @Override
    public String toString(){
        String result="[ ";
        int position=0;
        for (StringValue key:file.keySet()){
            if (position==0) result +=""+key+" → "+file.get(key);
            else result+=" , "+key+" → "+file.get(key);
            position++;
        }
        result+=" ]";
        return result;
    }
}
