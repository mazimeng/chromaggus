package com.workasintended.chromaggus.unitcomponent;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.workasintended.chromaggus.Unit;

import java.util.LinkedList;

/**
 * Created by mazimeng on 1/24/16.
 */
public class DialogComponent extends UnitComponent {
    private Label dialog;
    private LinkedList<String> lines = new LinkedList<String>();
    private String text;
    private int visibleLineCount = 3;
    private float fade = 3;
    private float fadeProgress = 0;

    public DialogComponent(Unit self, Label dialog) {
        super(self);
        this.dialog = dialog;
        this.dialog.setPosition(0, self.getHeight());

        self.addActor(dialog);
    }

    public void say(String line) {
        lines.addLast(line);
        while(lines.size()>visibleLineCount) {
            lines.pollFirst();
        }

        updateText();
    }

    private void updateText() {
        StringBuilder stringBuilder = new StringBuilder();

        for (String s : lines) {
            stringBuilder.append(s);
            stringBuilder.append("\n");
        }

        text = stringBuilder.toString();
        dialog.setText(text);
    }

    @Override
    public void update(float delta) {
        if(lines.size()==0) return;
        if(fadeProgress == fade) {
            fadeProgress = 0;
            lines.pollFirst();
            updateText();
        }

        fadeProgress = Math.min(fadeProgress+delta, fade);

    }
}
