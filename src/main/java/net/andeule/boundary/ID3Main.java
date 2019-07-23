package net.andeule.boundary;

import net.andeule.logic.CSVMapper;
import net.andeule.logic.ID3Algorithm;
import net.andeule.model.Node;
import net.andeule.model.TrainingExample;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ID3Main {

    public static void main(String args[]) {
        if(args.length != 2){
            System.out.println("Wrong number of arguments.");
            System.out.println("First parameter should be the absolute path to the csv file");
            System.out.println("Second parameter should be the index of the column, that is the target value.");
            return;
        }
        String absolutePathToFile = args[0];
        Integer indexOfTargetValule = Integer.valueOf(args[1]);
        File csvFile = new File(absolutePathToFile);
        if(csvFile.exists()){
            System.out.println("File found");
            TrainingExample trainingExample = null;
            try {
                trainingExample = CSVMapper.mapFromCSVFile(absolutePathToFile, indexOfTargetValule);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("Passed index was: %d. Therefore the target column is: %s%n", indexOfTargetValule, trainingExample.getHeadline().getAttributes().get(indexOfTargetValule));
            System.out.println("ID3-Algorithmn started");

            Node tree = ID3Algorithm.execute(trainingExample, new ArrayList<>(), null, "");
            System.out.println("Printing result:");
            tree.print();
        }
        else{
            System.out.printf("File does not exists (%s). Check your passed arguments%n", absolutePathToFile);

        }
    }

}
