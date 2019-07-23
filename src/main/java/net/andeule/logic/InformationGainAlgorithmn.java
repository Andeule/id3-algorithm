package net.andeule.logic;

import net.andeule.model.TrainingExample;
import net.andeule.model.TrainingExampleRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InformationGainAlgorithmn {

    public static int DEVIDE_SCALE = 30;

    /**
     * Returns the attribute (column) with the highes information gain.
     *
     * @param trainingExample           the whole {@link TrainingExample}
     * @param indexOfTakenOutAttributes the index that are included in this list, will not take into consideration.
     * @return returns the index of the attribute (column) with the highest information gain
     */
    public static int getIndexOfAttributeWithHighestInformationGain(TrainingExample trainingExample, List<Integer> indexOfTakenOutAttributes) {
        double highestInformationGain = 0;
        int indexOfAttributeWithHighestInformationgain = 0;
        for (int i = 0; i < trainingExample.getHeadline().getAttributes().size(); i++) {
            if (i != trainingExample.getHeadline().getColumnIndexOfTargetValue() && !indexOfTakenOutAttributes.contains(i)) {
                double informationGain = calculateInformationGain(trainingExample, i);
                if (informationGain > highestInformationGain || informationGainIsSameButNameIsAlphabeticallyEarlier(trainingExample, informationGain, highestInformationGain, i, indexOfAttributeWithHighestInformationgain)) {
                    highestInformationGain = informationGain;
                    indexOfAttributeWithHighestInformationgain = i;
                }
            }
        }
        return indexOfAttributeWithHighestInformationgain;
    }

    public static double calculateInformationGain(TrainingExample trainingExample, int columnIndexOfAttribute) {
        double originalEntropy = calculateOriginalEntropy(trainingExample);
        double relativeEntropy = calculateRelativeEntropy(trainingExample, columnIndexOfAttribute);
        double informationGain = originalEntropy - relativeEntropy;
        //System.out.printf("%s %n", trainingExample.getHeadline().getAttributes().get(columnIndexOfAttribute));
        //System.out.printf("%s %23s %n", originalEntropy, "Original entropy");
        //System.out.printf("%s %23s %n", relativeEntropy, "Relative entropy");
        //System.out.printf("%s %23s %n", informationGain, "Information gain");
        return informationGain;
    }

    protected static double calculateOriginalEntropy(TrainingExample trainingExample) {
        //Get fraction of each target value
        Map<Object, Double> fractionOfTargetValue = calculateFractionOfTargetValue(trainingExample, null, null);
        //Calculate original entropy
        double countOfAttributes = trainingExample.getExampleRowList().size();
        double term = 0;
        for (Map.Entry<Object, Double> entry : fractionOfTargetValue.entrySet()) {
            BigDecimal numinator = new BigDecimal(entry.getValue());
            BigDecimal denominator = new BigDecimal(countOfAttributes);
            term += (-(numinator.divide(denominator, DEVIDE_SCALE, RoundingMode.HALF_UP).doubleValue()) * (Math.log(numinator.divide(denominator, DEVIDE_SCALE, RoundingMode.HALF_UP).doubleValue()) / Math.log(2)));
        }
        return term;
    }

    protected static double calculateRelativeEntropy(TrainingExample trainingExample, int columnOfAttribute) {
        double relativeEntropy = 0;
        int countNumberOfAttributes = trainingExample.getExampleRowList().size();

        //calculate whole fraction of that attribute e.g. age
        Map<Object, Double> fractionOfAttribute = new HashMap<>();
        for (TrainingExampleRow row : trainingExample.getExampleRowList()) {
            Object cellValueAttribute = row.getAttributes().get(columnOfAttribute);
            if (fractionOfAttribute.containsKey(cellValueAttribute)) {
                fractionOfAttribute.put(cellValueAttribute, 1D + fractionOfAttribute.get(cellValueAttribute));
            } else {
                fractionOfAttribute.put(cellValueAttribute, 1D);
            }
        }


        for (Map.Entry<Object, Double> entry : fractionOfAttribute.entrySet()) {

            //FRACTION FOR ONE ATTRIBUTE's target value e.g. old, young
            Map<Object, Double> fractionOfInnerAttribute = calculateFractionOfTargetValue(trainingExample, columnOfAttribute, entry);
            //calculate
            double term = 0;
            for (Map.Entry<Object, Double> entryOfInner : fractionOfInnerAttribute.entrySet()) {
                BigDecimal numinator = new BigDecimal(entryOfInner.getValue());
                BigDecimal denominator = new BigDecimal(entry.getValue());
                term += (-(numinator.divide(denominator, DEVIDE_SCALE, RoundingMode.HALF_UP).doubleValue()) * (Math.log(numinator.divide(denominator, DEVIDE_SCALE, RoundingMode.HALF_UP).doubleValue()) / Math.log(2)));
            }
            BigDecimal numinator = new BigDecimal(entry.getValue());
            BigDecimal denominator = new BigDecimal(countNumberOfAttributes);
            term *= numinator.divide(denominator, DEVIDE_SCALE, RoundingMode.HALF_UP).doubleValue();
            relativeEntropy += term;
        }
        return relativeEntropy;
    }

    /**
     * @param trainingExample
     * @param columnOfAttribute         With this attribute you can restrict the calculation to a column and a specific attribute of it (which will be defiend by the variable restrictionToOneAttribute).
     *                                  If this variable is set, then columnOfAttribute must be also set.
     * @param restrictionToOneAttribute You can restrict the calculation to one value of that attribute e.g. column = "age" attributes of that column "average","old","young". Then you could restrict it to "young"
     *                                  If this variable is set, then columnOfAttribute must be also set.
     * @return
     */
    private static Map<Object, Double> calculateFractionOfTargetValue(TrainingExample trainingExample, Integer columnOfAttribute, Map.Entry<Object, Double> restrictionToOneAttribute) {
        if (columnOfAttribute != null && restrictionToOneAttribute == null || columnOfAttribute == null && restrictionToOneAttribute != null)
            throw new IllegalArgumentException("If columnfOfAttribute is set then restrictionToOneAttribute must be set also and vice versa.");

        Map<Object, Double> fractionOfTargetValue = new HashMap<>();
        for (TrainingExampleRow row : trainingExample.getExampleRowList()) {
            Object targetValue = row.getAttributes().get(row.getColumnIndexOfTargetValue());
            if ((columnOfAttribute == null && restrictionToOneAttribute == null) || row.getAttributes().get(columnOfAttribute).equals(restrictionToOneAttribute.getKey()))
                if (fractionOfTargetValue.containsKey(targetValue)) {
                    fractionOfTargetValue.put(targetValue, 1D + fractionOfTargetValue.get(targetValue));
                } else {
                    fractionOfTargetValue.put(targetValue, 1D);
                }
        }
        return fractionOfTargetValue;
    }

    protected static boolean informationGainIsSameButNameIsAlphabeticallyEarlier(TrainingExample trainingExample, double informationGain, double highestInformationGain, int currentIndex, int indexOfAttributeWithHighestInformationgain) {
        return informationGain == highestInformationGain &&
                (trainingExample.getHeadline().getAttributes().get(currentIndex)
                        .compareTo(trainingExample.getHeadline().getAttributes().get(indexOfAttributeWithHighestInformationgain))
                        < 0);
    }


}
