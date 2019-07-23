package net.andeule.logic;


import net.andeule.model.TrainingExample;
import net.andeule.model.TrainingExampleHeadline;
import net.andeule.model.TrainingExampleRow;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVMapper {

    public static TrainingExample mapFromCSVFile(String filepath, int indexOfTargetColumn) throws IOException {
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            TrainingExample trainingExample = new TrainingExample();
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                String[] lineArray = line.split(cvsSplitBy);
                if(isFirstLine) {
                    TrainingExampleHeadline trainingExampleHeadline = new TrainingExampleHeadline(indexOfTargetColumn,lineArray);
                    trainingExample.setHeadline(trainingExampleHeadline);
                    isFirstLine = false;
                }
                else{
                    TrainingExampleRow trainingExampleRow = new TrainingExampleRow(indexOfTargetColumn,lineArray);
                    trainingExample.getExampleRowList().add(trainingExampleRow);
                }
            }
            return trainingExample;

        } catch (IOException e) {
            throw e;
        }
    }
}
