package a3.application;

import java.io.IOException;

import a3.data.CSVParser;

public class Application {
	
    public static void main(String[] args) throws InterruptedException, IOException {
    	
    	CSVParser.parseZooDataset();
        
    }
}