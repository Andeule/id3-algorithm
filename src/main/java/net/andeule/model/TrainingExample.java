package net.andeule.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class basically represents a whole table with a headline and an infinite number of lines.
 */
@Setter
@Getter
@NoArgsConstructor
public class TrainingExample {

    private List<TrainingExampleRow> exampleRowList = new ArrayList<>();
    private TrainingExampleHeadline headline;

    public TrainingExample(TrainingExampleHeadline headline, List<TrainingExampleRow> exampleRowList) {
        this.headline = headline;
        this.exampleRowList = exampleRowList;
    }

}
