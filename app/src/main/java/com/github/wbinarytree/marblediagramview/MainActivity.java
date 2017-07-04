package com.github.wbinarytree.marblediagramview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MarbleDiagramView diagramView = (MarbleDiagramView) findViewById(R.id.marble);
        List<Marble> source1 = new ArrayList<>();
        source1.add(new Marble("A", 0xFFE6E6FA));
        source1.add(new Marble("B", 0xFFE6E6FA));
        source1.add(new Marble("C", 0xFFE6E6FA));
        List<Marble> source2 = new ArrayList<>();
        source2.add(new Marble("1", 0xFFFFB6C1));
        source2.add(new Marble("2", 0xFFFFB6C1));
        source2.add(new Marble("3", 0xFFFFB6C1));
        diagramView.loadDiagram(new MarbleDiagram.DiagramBuilder().addSource(source1)
            .addSource(source2)
            .setOperator("concat")
            .build());
    }
}
