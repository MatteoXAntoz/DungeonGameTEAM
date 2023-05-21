package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import org.w3c.dom.Text;
import tools.Constants;
import tools.Point;

public class GameOverMenu<T extends Actor> extends ScreenController<T> {

    /** Creates a new PauseMenu with a new Spritebatch */
    public GameOverMenu() {
        this(new SpriteBatch());
    }

    /** Creates a new PauseMenu with a given Spritebatch */
    public GameOverMenu(SpriteBatch batch) {
        super(batch);

        // GameOver text
        ScreenText gameOverText =
            new ScreenText(
                "Game Over",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.RED)
                    .build());
        gameOverText.setFontScale(3);
        gameOverText.setPosition(
            (Constants.WINDOW_WIDTH) / 2f - gameOverText.getWidth(),
            (Constants.WINDOW_HEIGHT) / 1.5f + gameOverText.getHeight(),
            Align.center | Align.bottom);
        add((T) gameOverText);

        // Restart Button
        ScreenButton restart =
            new ScreenButton(
                "Restart",
                new Point(0, 0),
                this.restartGame(),
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.RED)
                    .setDownFontColor(new Color(255, 0, 0, 0.6f))
                    .build());
        restart.getLabel().setFontScale(2);
        restart.setPosition(
            (Constants.WINDOW_WIDTH) / 2f,
            (Constants.WINDOW_HEIGHT) / 1.7f + restart.getHeight()/2,
            Align.center | Align.bottom);
        add((T) restart);

        // Exit Button
        ScreenButton exit =
            new ScreenButton(
                "Exit",
                new Point(0, 0),
                this.exitGame(),
                new TextButtonStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontColor(Color.RED)
                    .setDownFontColor(new Color(255, 0, 0, 0.6f))
                    .build());
        exit.getLabel().setFontScale(2);
        exit.setPosition(
            (Constants.WINDOW_WIDTH) / 2f,
            (Constants.WINDOW_HEIGHT) / 2f + exit.getHeight()/2,
            Align.center | Align.bottom);
        add((T) exit);
//        hideMenu();
    }

    private TextButtonListener restartGame()
    {
        return new TextButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("restart");

            }
        };
    }

    private TextButtonListener exitGame()
    {
        return new TextButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.exit(0);
            }
        };
    }

    /** shows the Menu */
    public void showMenu() {
        this.forEach((Actor s) -> s.setVisible(true));
    }

    /** hides the Menu */
    public void hideMenu() {
        this.forEach((Actor s) -> s.setVisible(false));
    }
}
