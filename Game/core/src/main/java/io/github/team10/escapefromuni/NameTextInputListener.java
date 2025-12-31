package io.github.team10.escapefromuni;

import com.badlogic.gdx.Input.TextInputListener;

public class NameTextInputListener implements TextInputListener{

    public String name;

    @Override
    public void input(String text) {
        name = text.substring(0, 2).toUpperCase();
    }

    @Override
    public void canceled() {
        name = "N/A";
    }

}