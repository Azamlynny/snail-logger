package frc.util;

import java.io.*;

class SnailLogger{
    
    String stream;
    PrintWriter out;
    
    public SnailLogger(String outputStream){
        stream = outputStream;
        out = new PrintWriter(new BufferedWriter(new FileWriter(stream)));
    }

    void open(){

    }

    void close(){

    }

    void writeString(String s){

    }

    void writeDouble(double d){

    }
    
}