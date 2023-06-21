package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Constants;
import tools.Point;

public class HeroUI <T extends Actor> extends ScreenController<T> {
    private static final HeroUI inst = new HeroUI();

    private ScreenText health;
    private HeroUI() {
        super(new SpriteBatch());
        health =
            new ScreenText(
                "HEALTH:",
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(Color.GREEN)
                    .build());
        health.setFontScale(1);
        health.setPosition(
            50,
            50,
            Align.center | Align.bottom);
        add((T) health);
        showMenu();
    };

    public static HeroUI getInstance() {
        return inst;
    }

    public void setHealth(int value) {
        health.setText("HEALTH: " + value);
    }


}
