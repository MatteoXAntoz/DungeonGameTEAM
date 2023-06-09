package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import ecs.entities.Hero;
import java.util.logging.Level;
import java.util.logging.Logger;
import level.LevelAPI;
import starter.Game;
import tools.Constants;
import tools.Point;

/**
 * Menu for exiting and restarting the Game
 *
 * <p>When the Player dies the <code>GameOverMenu</code> is set Visible. Two <code>ScreenButton
 * </code> restart and exit are being shown. When pressing on Exit the game Closes and when pressed
 * on restart the player is being reset and a new level is being loaded.
 *
 * @param <T> Elements of the GameOverMenu
 */
public class GameOverMenu<T extends Actor> extends ScreenController<T> {
    private LevelAPI levelAPI;

    private Logger gameOverLogger;

    /** Creates a new GameOverMenu with a new Spritebatch */
    public GameOverMenu(LevelAPI levelAPI) {
        this(new SpriteBatch());
        this.levelAPI = levelAPI;
    }

    /** Creates a new GameOverMenu with a given Spritebatch */
    public GameOverMenu(SpriteBatch batch) {
        super(batch);

        gameOverLogger = Logger.getLogger(this.getClass().getName());
        gameOverLogger.setLevel(Level.ALL);

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
                (Constants.WINDOW_HEIGHT) / 1.7f + restart.getHeight() / 2,
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
                (Constants.WINDOW_HEIGHT) / 2f + exit.getHeight() / 2,
                Align.center | Align.bottom);
        add((T) exit);
        hideMenu();
    }

    private TextButtonListener restartGame() {
        return new TextButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameOverLogger.info("Restarting Game.");
                Game.toggleGameOverMenu();
                Game.setHero(new Hero());
                levelAPI.setLevelID(0);
                levelAPI.loadLevel(Game.LEVELSIZE);
            }
        };
    }

    private TextButtonListener exitGame() {
        return new TextButtonListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameOverLogger.info("Exit Game.");
                System.exit(0);
            }
        };
    }

    @Override
    public void showMenu() {
        gameOverLogger.info(this.getClass().getSimpleName() + " is toggled to Visible.");
        super.showMenu();
    }
}
