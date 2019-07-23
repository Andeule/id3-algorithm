package net.andeule.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class TrainingExampleRow {

    List<String> attributes;
    int columnIndexOfTargetValue;

    public TrainingExampleRow(int columnIndexOfTargetValue, String... attributes) {
        this.columnIndexOfTargetValue = columnIndexOfTargetValue;
        this.attributes = Arrays.asList(attributes);
    }
    public TrainingExampleRow(int columnIndexOfTargetValue, List<String> attributes) {
        this.columnIndexOfTargetValue = columnIndexOfTargetValue;
        this.attributes = attributes;
    }


}
