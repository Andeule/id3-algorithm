package net.andeule.logic;

import net.andeule.model.Node;
import net.andeule.model.TrainingExample;
import net.andeule.model.TrainingExampleHeadline;
import net.andeule.model.TrainingExampleRow;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ID3AlgorithmTestWithCreditWorthiness {

    TrainingExample trainingExample;

    @Before
    public void prepare() {
        List<TrainingExampleRow> exampleRows = new ArrayList<>();
        exampleRows.add(new TrainingExampleRow(0, "high", "old", "clerk", "sufficient"));
        exampleRows.add(new TrainingExampleRow(0, "high", "old", "clerk", "sparse"));
        exampleRows.add(new TrainingExampleRow(0, "average", "young", "clerk", "sparse"));
        exampleRows.add(new TrainingExampleRow(0, "high", "average", "clerk", "sufficient"));
        exampleRows.add(new TrainingExampleRow(0, "low", "young", "self-employed", "sparse"));
        exampleRows.add(new TrainingExampleRow(0, "low", "young", "self-employed", "sufficient"));
        exampleRows.add(new TrainingExampleRow(0, "average", "average", "self-employed", "sufficient"));
        exampleRows.add(new TrainingExampleRow(0, "low", "average", "self-employed", "sparse"));
        exampleRows.add(new TrainingExampleRow(0, "average", "old", "self-employed", "sparse"));
        exampleRows.add(new TrainingExampleRow(0, "high", "old", "self-employed", "sufficient"));
        trainingExample = new TrainingExample(new TrainingExampleHeadline(0, "worthy", "age", "occupation", "security"), exampleRows);
    }

    @Test
    public void testExtractSubset(){
        TrainingExample subset= ID3Algorithm.extractSubSetByAnAttribute(trainingExample,1,"old");
        Assert.assertEquals(4,subset.getExampleRowList().size());
    }

    @Test
    public void testGetDecisionAttributesOfA(){
        int index = InformationGainAlgorithm.getIndexOfAttributeWithHighestInformationGain(trainingExample, new ArrayList<>());
        List<String> decisionAttributesOfA = ID3Algorithm.getPossibleValuesOfA(trainingExample, index);
        Assert.assertEquals(3, decisionAttributesOfA.size());
        List<String> expectedList = new ArrayList<>();
        expectedList.add("average");
        expectedList.add("young");
        expectedList.add("old");
        Assert.assertEquals(expectedList,decisionAttributesOfA);
    }

    @Test
    public void testAllTargetValuesAreSame(){
        boolean same = ID3Algorithm.allExampleTargetValueAreIdentical(trainingExample);
        Assert.assertFalse(same);


        List<TrainingExampleRow> exampleRows = new ArrayList<>();
        exampleRows.add(new TrainingExampleRow(0, "high", "old", "clerk", "sufficient"));
        exampleRows.add(new TrainingExampleRow(0, "high", "old", "clerk", "sparse"));
        exampleRows.add(new TrainingExampleRow(0, "high", "average", "clerk", "sufficient"));
        TrainingExample trainingExampleWithSameTargetValue = new TrainingExample(new TrainingExampleHeadline(0, "worthy", "age", "occupation", "security"), exampleRows);

        same = ID3Algorithm.allExampleTargetValueAreIdentical(trainingExampleWithSameTargetValue);
        Assert.assertTrue(same);
    }

    @Test
    public void testID3(){
        Node tree = ID3Algorithm.execute(trainingExample,new ArrayList<>(), null, "");
        tree.print();

    }
}
