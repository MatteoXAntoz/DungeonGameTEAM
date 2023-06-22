package graphic.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import controller.ScreenController;
import tools.Constants;
import tools.Point;

public class HeroUI <T extends Actor> extends ScreenController<T> {
    private static final HeroUI inst = new HeroUI();

    private ProgressBar healthP;
    private ProgressBar armorP;
    private ProgressBar manaP;
    private ProgressBar xpP;
    private ScreenText levelST;

    private HeroUI() {
        super(new SpriteBatch());

        // Health #######################################################
        ScreenText health = getDefaultScreenText (
            1,
            40,
            60,
            Color.GREEN,
            "HEALTH"
        );
        add((T) health);

        healthP = getDefaultProgressBar(
            10,
            120,
            30,
            30,
            Color.LIGHT_GRAY,
            Color.GREEN,
            0,
            100,
            1,
            100,
            1
        );
        add((T) healthP);

        // Armor ############################################################
        ScreenText armor = getDefaultScreenText (
            1,
            90,
            60,
            Color.BLUE,
            "ARMOR"
        );
        add((T) armor);

        armorP = getDefaultProgressBar(
            10,
            120,
            80,
            30,
            Color.LIGHT_GRAY,
            Color.BLUE,
            0,
            100,
            1,
            0,
            1
        );
        add((T) armorP);

        // Mana ###################################################

        ScreenText mana = getDefaultScreenText (
            1,
            140,
            60,
            Color.MAGENTA,
            "MANA"
        );
        add((T) mana);

        manaP = getDefaultProgressBar(
            10,
            120,
            130,
            30,
            Color.LIGHT_GRAY,
            Color.MAGENTA,
            0,
            15,
            1,
            0,
            2
        );
        add((T) manaP);

        // XP ########################################################
        xpP = getDefaultProgressBar(
            10,
            250,
            Constants.WINDOW_HEIGHT - 40,
            (Constants.WINDOW_WIDTH - 250)/2,
            Color.LIGHT_GRAY,
            Color.GREEN,
            0,
            100,
            1,
            0,
            1
        );
        add((T) xpP);

        // LEVEL ###########################################################
        ScreenText levelST = getDefaultScreenText (
            1,
            Constants.WINDOW_HEIGHT - 70,
            Constants.WINDOW_WIDTH/2,
            Color.GREEN,
            "0"
        );
        add((T) levelST);

        showMenu();
    };

    private ProgressBar getDefaultProgressBar(
        int height,
        int width,
        int y,
        int x,
        Color backgroundColor,
        Color knobColor,
        int min,
        int max,
        int step,
        int value,
        float animateDuration
        ) {
        Skin background = new Skin();
        Pixmap pixmapB = new Pixmap(1, height, Pixmap.Format.RGBA8888);
        pixmapB.setColor(backgroundColor);
        pixmapB.fill();
        background.add("background", new Texture(pixmapB));

        Skin knob = new Skin();
        Pixmap pixmapK = new Pixmap(1, height, Pixmap.Format.RGBA8888);
        pixmapK.setColor(knobColor);
        pixmapK.fill();
        knob.add("knob", new Texture(pixmapK));

        ProgressBar.ProgressBarStyle barStyle = new ProgressBar.ProgressBarStyle(
            background.newDrawable("background", backgroundColor),
            knob.newDrawable("knob", knobColor));

        barStyle.knobBefore = barStyle.knob;

        ProgressBar pBar =
            new ProgressBar(
                min,
                max,
                step,
                false,
                barStyle
            );
        pBar.setPosition(
            x,
            y
        );
        pBar.setAnimateDuration(animateDuration);
        pBar.setValue(value);
        pBar.setWidth(width);
        return pBar;
    }

    private ScreenText getDefaultScreenText (
        int scale,
        int y,
        int x,
        Color color,
        String text
    )
    {
        ScreenText screenText =
            new ScreenText(
                text,
                new Point(0, 0),
                3,
                new LabelStyleBuilder(FontBuilder.DEFAULT_FONT)
                    .setFontcolor(color)
                    .build());
        screenText.setFontScale(scale);
        screenText.setPosition(
            x,
            y,
            Align.center | Align.bottom);
        return screenText;
    }

    public static HeroUI getInstance() {
        return inst;
    }

    public void setCurrentHealth(int value) {
        healthP.setValue(value);
    }
    public void setMaximumHealth(int value) {
        healthP.setRange(0, value);
    }
    public void setCurrentMana(int value) {
        manaP.setValue(value);
    }
    public void setMaximumMana(int value) {
        manaP.setRange(0, value);
    }


}
