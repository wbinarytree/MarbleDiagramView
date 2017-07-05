package com.github.wbinarytree.marblediagramview;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoda on 04/07/17.
 */

public class MarbleDiagram {

    private final List<List<Marble>> source;
    private final String operatorName;
    private ObservableAdapter operator;

    public MarbleDiagram(List<List<Marble>> source, String operatorName,
        ObservableAdapter operator) {
        this.source = source;
        this.operatorName = operatorName;
        this.operator = operator;
    }

    public ObservableAdapter getOperator() {
        return operator;
    }

    public List<List<Marble>> getSource() {
        return source;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public interface ObservableAdapter {
        Observable<Marble> convert(List<Observable<Marble>> marbles);
    }

    public static class DiagramBuilder {
        private List<List<Marble>> source;
        private String operatorName;
        private ObservableAdapter adapter;

        public DiagramBuilder() {
            source = new ArrayList<>();
            operatorName = "";
        }

        DiagramBuilder addSource(List<Marble> marbleList) {
            source.add(marbleList);
            return this;
        }

        DiagramBuilder setOperator(String name) {
            this.operatorName = name;
            return this;
        }

        DiagramBuilder setResult(ObservableAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public MarbleDiagram build() {
            return new MarbleDiagram(source, operatorName, adapter);
        }
    }
}
