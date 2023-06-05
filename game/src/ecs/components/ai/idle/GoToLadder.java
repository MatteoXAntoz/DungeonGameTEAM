package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.components.skill.ISkillFunction;
import ecs.entities.Entity;
import ecs.entities.items.Item;
import level.elements.tile.ExitTile;
import level.elements.tile.FloorTile;
import level.elements.tile.Tile;
import level.tools.LevelElement;
import starter.Game;
import tools.Constants;
import tools.Point;
import tools.Timer;

import javax.swing.plaf.PanelUI;
import java.awt.event.ItemEvent;

import static starter.Game.currentLevel;

public class GoToLadder implements IIdleAI {

    /**
     * grapLadderPath is the path to the Ladder
     */
    public GraphPath<Tile> graphLadderPath;
    /**
     * graphRandomPoint is the path to a random Point in the dungeon
     */
    public GraphPath<Tile> graphRandomPoint;
    // timer is used for the cooldown of the paths
    private Timer timer = new Timer();
    // mode is initialised with R for, which means, that the first path is a random Point
    private Character mode = 'R';
    private Point randomPoint;

    //
    private Tile ladderTile;


    public GoToLadder() {
        setLadderTile(currentLevel.getExitTiles().get(0));
        setRandomPoint(currentLevel.getRandomFloorTile().getCoordinateAsPoint());
        getTimer().setDuration(4);
    }

    /**
     * {@inheritDoc}
     *
     * @param entity associated entity
     */
    @Override
    public void idle(Entity entity) {

        if (!getTimer().isCounting()) {
            if (getMode().equals('L')) {
                setMode('R');
            } else if (getMode().equals('R')) {
                setMode('L');
            }
            getTimer().activateCounter();
        } else {
            move(entity, getMode());
        }

        getTimer().reduceCounter();


    }


    // random Point and the path to the Ladder is set up as cycle
    private void move(Entity entity, Character mode) {
        if (mode.equals('L')) {
            if (getRandomPoint() == null) {
                setGraphRandomPoint(AITools.calculatePath(entity.positionComponent.getPosition(), randomPoint));
            }
            AITools.move(entity, getGraphRandomPoint());
            setRandomPoint(currentLevel.getRandomFloorTile().getCoordinateAsPoint());
            setGraphLadderPath(null);

        }
        if (mode.equals('R')) {
            if (getGraphLadderPath() == null) {
                setGraphLadderPath(AITools.calculatePath(entity.positionComponent.getPosition()
                    , getLadderTile().getCoordinate().toPoint()));
            }
            AITools.move(entity, getGraphLadderPath());
            setGraphRandomPoint(null);


        }


    }

    public GraphPath<Tile> getGraphLadderPath() {
        return graphLadderPath;
    }

    public void setGraphLadderPath(GraphPath<Tile> graphLadderPath) {
        this.graphLadderPath = graphLadderPath;
    }

    public GraphPath<Tile> getGraphRandomPoint() {
        return graphRandomPoint;
    }

    public void setGraphRandomPoint(GraphPath<Tile> graphRandomPoint) {
        this.graphRandomPoint = graphRandomPoint;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public Character getMode() {
        return mode;
    }

    public void setMode(Character mode) {
        this.mode = mode;
    }

    public Point getRandomPoint() {
        return randomPoint;
    }

    public void setRandomPoint(Point randomPoint) {
        this.randomPoint = randomPoint;
    }

    public Tile getLadderTile() {
        return ladderTile;
    }

    public void setLadderTile(Tile ladderTile) {
        this.ladderTile = ladderTile;
    }
}


