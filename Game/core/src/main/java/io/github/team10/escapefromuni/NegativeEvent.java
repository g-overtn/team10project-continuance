package io.github.team10.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class NegativeEvent extends Event {

    private final NegativeEventType type;
    private final ScoreManager scoreManager;

    private final Texture titlePanelTexture;
    private final Texture questionPanelTexture;
    private final Texture leftButtonTexture;
    private final Texture rightButtonTexture;

    private final Sprite titlePanelSprite;
    private final Sprite questionPanelSprite;
    private final Sprite leftButtonSprite;
    private final Sprite rightButtonSprite;

    private boolean questionAnswered = false;
    private float answerDisplayTimer = 0f;

    private final String questionText;
    private String feedbackText = "";

    private Rectangle leftButtonBounds;
    private Rectangle rightButtonBounds;

    /**
     * Creates a new negative event.
     */
    public NegativeEvent(NegativeEventType type, Player player, EscapeGame game, ScoreManager scoreManager) {
        super(EventType.NEGATIVE, player, game);
        this.type = type;
        questionText = type.getQuestionText();
        titlePanelTexture = new Texture("UI/Blue4x1Panel.png");
        questionPanelTexture = new Texture("UI/BlueBorder10x3Panel.png");
        leftButtonTexture = new Texture("UI/GreenBorder5x2Panel.png");
        rightButtonTexture = new Texture("UI/OrangeBorder5x2Panel.png");

        titlePanelSprite = new Sprite(titlePanelTexture);
        questionPanelSprite = new Sprite(questionPanelTexture);
        leftButtonSprite = new Sprite(leftButtonTexture);
        rightButtonSprite = new Sprite(rightButtonTexture);

        this.scoreManager = scoreManager;
    }

    @Override
    public void startEvent() {
        if (eventFinished) return;
        super.startEvent();

        player.enableMovement(false);
        AudioManager.getInstance().playEventSound(EventType.NEGATIVE);
        questionAnswered = false;
        initialiseQuizUI();
    }

    /**
     * Initialises and positions all UI components for the quiz screen.
     * This includes a title, question display and two achievement_texts (true or false).
     */
    private void initialiseQuizUI() {
        float uiWidth = game.uiViewport.getWorldWidth();
        float uiHeight = game.uiViewport.getWorldHeight();

        titlePanelSprite.setSize(480f, 120f);
        titlePanelSprite.setCenter(uiWidth / 2f, uiHeight * 0.75f);

        questionPanelSprite.setSize(1200f, 360f);
        questionPanelSprite.setCenter(uiWidth / 2f, uiHeight * 0.5f);

        leftButtonSprite.setSize(600f, 240f);
        rightButtonSprite.setSize(600f, 240f);
        leftButtonSprite.setCenter(uiWidth / 2f - 320f, uiHeight * 0.20f);
        rightButtonSprite.setCenter(uiWidth / 2f + 320f, uiHeight * 0.20f);

        leftButtonBounds = new Rectangle(
            leftButtonSprite.getX(), leftButtonSprite.getY(),
            leftButtonSprite.getWidth(), leftButtonSprite.getHeight()
        );

        rightButtonBounds = new Rectangle(
            rightButtonSprite.getX(), rightButtonSprite.getY(),
            rightButtonSprite.getWidth(), rightButtonSprite.getHeight()
        );
    }

    @Override
    public void endEvent() {
        eventFinished = true;
        player.enableMovement(true);

        titlePanelTexture.dispose();
        questionPanelTexture.dispose();
        leftButtonTexture.dispose();
        rightButtonTexture.dispose();
    }

    @Override
    public void update(float delta) {
        if (eventFinished) return;

        // End event 1 second after answering the question.
        if (questionAnswered)
        {
            answerDisplayTimer += delta;
            if (answerDisplayTimer > 1f)
            {
                endEvent();
            }
            return;
        }

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.uiCamera.unproject(touchPos);

            if (leftButtonBounds.contains(touchPos.x, touchPos.y)) {
                // TRUE selected.
                handleAnswer(true);
            }
            else if (rightButtonBounds.contains(touchPos.x, touchPos.y)) {
                // FALSE selected.
                handleAnswer(false);
            }
        }
    }

    /**
     * Apply's effects based on the player's answer.
     * If correct, score is increased.
     * If incorrect, player speed is decreased and score is decreased.
     * @param answer {@code true} if the right button was pressed, {@code false} if the left was pressed.
     */
    private void handleAnswer(boolean answer) {
        questionAnswered = true;
        answerDisplayTimer = 0f;

        if (answer == type.getCorrectAnswer()) {
            feedbackText = "Correct: Score +500";
            scoreManager.increaseScore(500);
            game.achievementManager.check_PASS();
        }
        else {
            feedbackText = "Incorrect: Speed Decrease, Score -250";
            player.increaseSpeed(-2f);
            scoreManager.increaseScore(-250);
            game.achievementManager.check_FAIL();
        }
    }

    @Override
    public void draw() {}

    @Override
    public void drawUI() {
        if (eventFinished) return;

        game.font.setColor(Color.BLACK);
        GlyphLayout layout = new GlyphLayout();

        titlePanelSprite.draw(game.batch);
        questionPanelSprite.draw(game.batch);
        leftButtonSprite.draw(game.batch);
        rightButtonSprite.draw(game.batch);

        float uiWidth = game.uiViewport.getWorldWidth();

        String titleText = type.getTitle();
        layout.setText(game.font, titleText);
        float titleX = (uiWidth - layout.width) / 2f;
        float titleY = titlePanelSprite.getY() + titlePanelSprite.getHeight() / 2f + layout.height / 2f;
        game.font.draw(game.batch, layout, titleX, titleY);

        // Draw question or feedback test, depending on whether the question has been answered.
        String displayText = questionAnswered ? feedbackText : questionText;
        layout.setText(game.font, displayText);
        float questionX = (uiWidth - layout.width) / 2f;
        float questionY = questionPanelSprite.getY() + questionPanelSprite.getHeight() / 2f + layout.height / 2f;
        game.font.draw(game.batch, layout, questionX, questionY);

        layout.setText(game.font, type.getLeftText());
        float trueTextX = leftButtonSprite.getX() + (leftButtonSprite.getWidth() - layout.width) / 2f;
        float trueTextY = leftButtonSprite.getY() + (leftButtonSprite.getHeight() + layout.height) / 2f;
        game.font.draw(game.batch, layout, trueTextX, trueTextY);

        layout.setText(game.font, type.getRightText());
        float falseTextX = rightButtonSprite.getX() + (rightButtonSprite.getWidth() - layout.width) / 2f;
        float falseTextY = rightButtonSprite.getY() + (rightButtonSprite.getHeight() + layout.height) / 2f;
        game.font.draw(game.batch, layout, falseTextX, falseTextY);
    }

    // getters and setters

    /**
     * Used for testing purposes.
     * @return event type.
     */
    public NegativeEventType getType() {
        return type;
    }

    /**
     * Used for testing purposes.
     * @return questionAnswered value.
     */
    public boolean getAnswered() {
        return questionAnswered;
    }

    /**
     * Used for testing purposes.
     * @return a list contains all sprites.
     */
    public Sprite[] getSprites() {
        return new Sprite[]{titlePanelSprite, questionPanelSprite, leftButtonSprite, rightButtonSprite};
    }

    /**
     * Used for testing purposes.
     * @return feedbackText value.
     */
    public String getFeedbackText() {
        return feedbackText;
    }

    /**
     * Used for testing purposes.
     * @return questionText value.
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Used for testing purposes.
     * @return scoreManager object.
     */
    public ScoreManager getScoreManager() {
        return scoreManager;
    }
}

