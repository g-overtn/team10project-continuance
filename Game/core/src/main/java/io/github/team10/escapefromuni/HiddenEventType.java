package io.github.team10.escapefromuni;

public enum HiddenEventType {

    LONGBOI("Longboi.png", "LongboiShadow.png", "Ghost of Longboi: \"Quack ... Quack\"") {
        @Override
        void doThing(Player player, EscapeGame game) {}
    },
    BOB("Bob.png", "Bob_shadow.png", "AHHHH! ITS A BUG!!\nOh... its just Bob.") {
        @Override
        void doThing(Player player, EscapeGame game) {}
    },
    INVERSE_CONTROLS("Inverted_controls.png", "Inverted_controls_shadow.png", "What happened??") {
        @Override
        void doThing(Player player, EscapeGame game) {
            // inverts controls by just reversing player speed
            player.setSpeed(player.getSpeed() * -1);
        }
    };

    private final String texture_path;
    private final String hidden_texture_path;
    private final String message;

    HiddenEventType(String texture_path, String hidden_texture_path, String message) {
        this.texture_path = texture_path;
        this.hidden_texture_path = hidden_texture_path;
        this.message = message;
    }

    abstract void doThing(Player player, EscapeGame game);

    // getters and setters

    public String getTexturePath() { return texture_path; }
    public String getHiddenTexturePath() { return hidden_texture_path; }
    public String getMessage() { return message; }

}
