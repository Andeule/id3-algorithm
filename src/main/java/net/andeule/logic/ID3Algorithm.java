package net.andeule.logic;


import net.andeule.model.Node;
import net.andeule.model.TrainingExample;
import net.andeule.model.TrainingExampleRow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ID3Algorithm {


    /**
     * This method is almost identical to the algorithm shown in the slides (ID3: Algorithm for Concept Learning)
     *
     * @param trainingExample           The Training example data structure
     * @param indexOfTakenOutAttributes contains the index of attributes that should not be considered when calling this method.
     *                                  This attribute will be filled throughout the recursive calls.
     *                                  Initially it should be empty if no special case is given
     * @param parent                    represents the parent node from which it got called
     * @param branchLabel               The label of the branch, from the parent to the current node. Initially it should be empty
     * @return returns the root of the tree with all its children.
     */
    public static Node<String> execute(TrainingExample trainingExample, List<Integer> indexOfTakenOutAttributes, Node parent, String branchLabel) {
        //this line creates a deep copy of the indexlist. Otherwise it will break our logic in multiple recursive calls.
        //Explanation: if we are in one branch we add indexes to the list with each recursive call. When this branch is finished we should not have the added index from the last branch in our list.
        List<Integer> deepCopyOfIndexOfTakenOutAttributes = new ArrayList<>(indexOfTakenOutAttributes);

        int indexOfBestInformationGainAttribute = InformationGainAlgorithm.getIndexOfAttributeWithHighestInformationGain(trainingExample, deepCopyOfIndexOfTakenOutAttributes);
        Node<String> root = new Node<>(trainingExample.getHeadline().getAttributes().get(indexOfBestInformationGainAttribute), parent, branchLabel);
        if (allExampleTargetValueAreIdentical(trainingExample)) {
            return new Node<String>(trainingExample.getExampleRowList().get(0).getAttributes().get(trainingExample.getHeadline().getColumnIndexOfTargetValue()), parent, branchLabel);
        } else {
            List<String> possibleValueOfA = getPossibleValuesOfA(trainingExample, indexOfBestInformationGainAttribute);
            for (int i = 0; i < possibleValueOfA.size(); i++) {
                String branch = possibleValueOfA.get(i);
                TrainingExample subSetTrainingExampleOfA = extractSubSetByAnAttribute(trainingExample, indexOfBestInformationGainAttribute, possibleValueOfA.get(i));
                if (subSetTrainingExampleOfA.getExampleRowList().size() != 0) {
                    deepCopyOfIndexOfTakenOutAttributes.add(indexOfBestInformationGainAttribute);
                    root.getChildren().add(execute(subSetTrainingExampleOfA, deepCopyOfIndexOfTakenOutAttributes, root, branch));
                }
            }
        }

        return root;
    }


    /**
     * Returns a List of possible values of an Attribute (column).
     * @param trainingExample
     * @param indexOfA
     * @return
     */
    protected static List<String> getPossibleValuesOfA(TrainingExample trainingExample, int indexOfA) {
        Set<String> decisionAttributeSet = new HashSet<>();
        trainingExample.getExampleRowList().stream().forEach(trainingExampleRow ->
                decisionAttributeSet.add(trainingExampleRow.getAttributes().get(indexOfA)));
        List<String> attributeList = new ArrayList<>();
        attributeList.addAll(decisionAttributeSet);
        return attributeList;
    }

    /**
     * Example: If we have an Attribute (column) "age" with the possible values "old","young","average".
     * If we pass this method the index of "age" and e.g. "old", then it will create a {@link TrainingExample} object with only the lines where "age" equals "old"
     * @param trainingExampleOriginal the {@link TrainingExample}
     * @param indexOfColumn see example
     * @param attribute see example
     * @return the subset of the {@link TrainingExample} explained in the description
     */
    protected static TrainingExample extractSubSetByAnAttribute(TrainingExample trainingExampleOriginal, int indexOfColumn, String attribute) {
        TrainingExample trainingExampleSubSet = new TrainingExample(trainingExampleOriginal.getHeadline(), new ArrayList<>());
        for (TrainingExampleRow row : trainingExampleOriginal.getExampleRowList()) {
            if (row.getAttributes().get(indexOfColumn).equals(attribute)) {
                trainingExampleSubSet.getExampleRowList().add(row);
            }
        }
        return trainingExampleSubSet;
    }

    /**
     * Checks if all attributes in the {@link TrainingExample} have the same target value. E.g. all lines have the target value "true".
     * @param trainingExample
     * @return
     */
    protected static boolean allExampleTargetValueAreIdentical(TrainingExample trainingExample) {
        boolean allSame = true;
        Object previousTarget = null;
        for (TrainingExampleRow row : trainingExample.getExampleRowList()) {
            Object newTarget = row.getAttributes().get(row.getColumnIndexOfTargetValue());
            if (!newTarget.equals(previousTarget) && previousTarget != null) {
                allSame = false;
            } else {
                previousTarget = newTarget;
            }
        }
        return allSame;
    }
}
