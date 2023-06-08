package ecs.components.ai.idle;

import com.badlogic.gdx.ai.pfa.GraphPath;
import ecs.components.ai.AITools;
import ecs.entities.Entity;
import java.util.logging.Logger;
import level.elements.tile.Tile;
import starter.Game;
import tools.Point;
import tools.Timer;

/**
 * Idle AI in which the entity goes to the ladder an back to a random point
 */
public class GoToLadder implements IIdleAI {

    private final Logger goToLadder_logger = Logger.getLogger(this.getClass().getName());

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

    /**
     * Konstruktor
     */
    public GoToLadder() {
        ladderTile = Game.currentLevel.getExitTiles().get(0);
        randomPoint = Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint();
        timer.setDuration(4);
        goToLadder_logger.info("Timer for was set up to 4 seconds");
    }

    /**
     * Puts the entity in an idle state, alternating between left and right modes. If the timer is
     * not counting, it switches the mode and activates the timer. Otherwise, it moves the entity
     * according to the current mode and reduces the timer counter.
     *
     * @param entity The entity to put in idle state.
     */
    public void idle(Entity entity) {
        if (!getTimer().isCounting()) {
            // If the mode is left, switch to right mode; if the mode is right, switch to left mode
            if (getMode().equals('L')) {
                setMode('R');
            } else if (getMode().equals('R')) {
                setMode('L');
            }
            // Activate the timer counter
            getTimer().activateCounter();
        } else {
            // Move the entity according to the current mode
            move(entity, getMode());
        }

        // Reduce the timer counter
        getTimer().reduceCounter();
    }

    // Moves the entity according to the specified mode.
    private void move(Entity entity, Character mode) {
        if (mode.equals('L')) {
            // If there is no graph random point set, calculate a new path from the entity's
            // position to a random point
            if (getGraphRandomPoint() == null) {
                setGraphRandomPoint(
                    AITools.calculatePath(entity.positionComponent.getPosition(), randomPoint));
            }
            // Move the entity along the calculated path
            AITools.move(entity, graphRandomPoint);
            // Update the random point to a new random floor tile
            randomPoint = Game.currentLevel.getRandomFloorTile().getCoordinateAsPoint();
            // Reset the ladder path
            graphLadderPath = null;
        }
        if (mode.equals('R')) {
            // If the ladder path is not set, calculate a new path from the entity's position to the
            // ladder tile
            if (graphLadderPath == null) {
                graphLadderPath =
                    AITools.calculatePath(
                        entity.positionComponent.getPosition(),
                        ladderTile.getCoordinate().toPoint());
            }
            // Move the entity along the ladder path
            AITools.move(entity, graphLadderPath);
            // Reset the random point
            graphRandomPoint = null;
        }
    }

    /**
     * Returns the graph path for the ladder.
     *
     * @return The graph path for the ladder.
     */
    public GraphPath<Tile> getGraphLadderPath() {
        return graphLadderPath;
    }

    /**
     * Sets the graph path for the ladder.
     *
     * @param graphLadderPath The new graph path for the ladder.
     */
    public void setGraphLadderPath(GraphPath<Tile> graphLadderPath) {
        this.graphLadderPath = graphLadderPath;
    }

    /**
     * Returns the graph path for the random point.
     *
     * @return The graph path for the random point.
     */
    public GraphPath<Tile> getGraphRandomPoint() {
        return graphRandomPoint;
    }

    /**
     * Sets the graph path for the random point.
     *
     * @param graphRandomPoint The new graph path for the random point.
     */
    public void setGraphRandomPoint(GraphPath<Tile> graphRandomPoint) {
        this.graphRandomPoint = graphRandomPoint;
    }

    /**
     * Returns the timer instance.
     *
     * @return The timer instance.
     */
    public Timer getTimer() {
        return timer;
    }

    /**
     * Sets the timer instance.
     *
     * @param timer The new timer instance.
     */
    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    /**
     * Returns the mode character.
     *
     * @return The mode character.
     */
    public Character getMode() {
        return mode;
    }

    /**
     * Sets the mode character.
     *
     * @param mode The new mode character.
     */
    public void setMode(Character mode) {
        this.mode = mode;
    }

    /**
     * Returns the random point.
     *
     * @return The random point.
     */
    public Point getRandomPoint() {
        return randomPoint;
    }

    /**
     * Sets the random point.
     *
     * @param randomPoint The new random point.
     */
    public void setRandomPoint(Point randomPoint) {
        this.randomPoint = randomPoint;
    }

    /**
     * Returns the ladder tile.
     *
     * @return The ladder tile.
     */
    public Tile getLadderTile() {
        return ladderTile;
    }

    /**
     * Sets the ladder tile.
     *
     * @param ladderTile The new ladder tile.
     */
    public void setLadderTile(Tile ladderTile) {
        this.ladderTile = ladderTile;
    }
}
