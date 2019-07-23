package net.andeule.logic;

import net.andeule.model.TrainingExample;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class InformationGainAlgorithmTestWithMushroomsCSV {

    TrainingExample trainingExample;
    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String filepath = classLoader.getResource("mushrooms.csv").getPath();
        trainingExample = CSVMapper.mapFromCSVFile(filepath, 0);
    }

    @Test
    public void test() {
        InformationGainAlgorithm.calculateInformationGain(trainingExample,1);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,2);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,3);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,4);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,5);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,6);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,7);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,8);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,9);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,10);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,11);
        InformationGainAlgorithm.calculateInformationGain(trainingExample,12);
    }

    @Test
    public void testHighestInformationGain(){
        Assert.assertEquals(5, InformationGainAlgorithm.getIndexOfAttributeWithHighestInformationGain(trainingExample, new ArrayList<>()));
    }
}
