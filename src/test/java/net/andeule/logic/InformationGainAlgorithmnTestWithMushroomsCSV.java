package net.andeule.logic;

import net.andeule.model.TrainingExample;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class InformationGainAlgorithmnTestWithMushroomsCSV {

    TrainingExample trainingExample;
    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String filepath = classLoader.getResource("mushrooms.csv").getPath();
        trainingExample = CSVMapper.mapFromCSVFile(filepath, 0);
    }

    @Test
    public void test() {
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,1);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,2);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,3);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,4);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,5);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,6);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,7);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,8);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,9);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,10);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,11);
        InformationGainAlgorithmn.calculateInformationGain(trainingExample,12);
    }

    @Test
    public void testHighestInformationGain(){
        Assert.assertEquals(5,InformationGainAlgorithmn.getIndexOfAttributeWithHighestInformationGain(trainingExample, new ArrayList<>()));
    }
}
