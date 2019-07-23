package net.andeule.logic;

import net.andeule.model.Node;
import net.andeule.model.TrainingExample;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ID3AlgorithmnTestWithMushrooms {

    TrainingExample trainingExample;
    @Before
    public void setup() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        String filepath = classLoader.getResource("mushrooms.csv").getPath();
        trainingExample = CSVMapper.mapFromCSVFile(filepath, 0);
    }

    @Test
    public void testID3(){
        Node tree = ID3Algorithmn.execute(trainingExample,new ArrayList<>(), null, "");
        tree.print();
    }

}
