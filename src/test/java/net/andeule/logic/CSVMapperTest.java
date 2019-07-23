package net.andeule.logic;

import net.andeule.model.TrainingExample;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class CSVMapperTest {

    String filepath;

    @Before
    public void setup() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        filepath = classLoader.getResource("mushrooms.csv").getPath();
    }

    @Test
    public void testHeadlineWithMushroomsCSVFile() throws IOException {
        TrainingExample trainingExample = CSVMapper.mapFromCSVFile(filepath, 0);
        String[] assertionHeadline = "class,cap-shape,cap-surface,cap-color,bruises,odor,gill-attachment,gill-spacing,gill-size,gill-color,veil-color,spore-print-color,population,habitat".split(",");
        Assert.assertEquals(assertionHeadline, trainingExample.getHeadline().getAttributes().toArray());
    }

    @Test
    public void testFirstLineWithMushroomsCSVFile() throws IOException {
        TrainingExample trainingExample = CSVMapper.mapFromCSVFile(filepath, 0);
        String[] assertionFirstAttributeLine = "p,x,s,n,t,p,f,c,n,k,w,k,s,u".split(",");
        Assert.assertEquals(assertionFirstAttributeLine, trainingExample.getExampleRowList().get(0).getAttributes().toArray());
    }

    @Test
    public void testLastLineWithMushroomsCSVFile() throws IOException {
        TrainingExample trainingExample = CSVMapper.mapFromCSVFile(filepath, 0);
        String[] assertionLastAttributeLine = "e,x,s,n,f,n,a,c,b,y,o,o,c,l".split(",");
        Assert.assertEquals(assertionLastAttributeLine, trainingExample.getExampleRowList().get(trainingExample.getExampleRowList().size() - 1).getAttributes().toArray());
    }
}
