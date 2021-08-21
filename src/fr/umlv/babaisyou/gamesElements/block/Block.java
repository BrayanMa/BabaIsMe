package fr.umlv.babaisyou.gamesElements.block;

import fr.umlv.babaisyou.gameEngine.Parser;
import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.blockNames.PropEnum;
import fr.umlv.babaisyou.gamesElements.board.Board;
import fr.umlv.babaisyou.graphic.Graphic;
import fr.umlv.zen5.ApplicationContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * This class will represent all the blocks that will be present in the game board
 */
public interface Block {
    /**
     * Function use for return x
     * @return int
     */
    int getx();

    /**
     * Function use for return y
     * @return int
     */
    int gety();

    /**
     * Function use for the move
     */
    void left();

    /**
     * Function use for the move
     */
    void right();

    /**
     * Function use for the move
     */
    void up();

    /**
     * Function use for the move
     */
    void down();

    @Override
    String toString();

    /**
     * Function that return the Name
     * @return null
     */
    default NameEnum getName() {
        return null;
    }

    /**
     * Function that return the Property
     * @return null
     */
    default PropEnum getProp() {
        return null;
    }

    /**
     * If the block is a Name
     * @return boolean
     */
    default boolean isName() {
        return false;
    }

    /**
     * If the block is an Operator
     * @return boolean
     */
    default boolean isOperator() {
        return false;
    }

    /**
     * If the block is a Property
     * @return boolean
     */
    default boolean isProperties() {
        return false;
    }

    /**
     * If the block is an Obj
     * @return boolean
     */
    default boolean isObj() {
        return false;
    }


    /**
     * All these functions are used to know if the blocks have certain properties.
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isStop(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property Defeat
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isDefeat(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property Push
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isPush(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property Sink
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isSink(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property Melt
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isMelt(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property Hot
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isHot(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property You
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isYou(Parser parser) {
        Objects.requireNonNull(parser);
        return false;
    }

    /**
     * If the block owns the property Gang
     * @param parser The parser that contains the properties
     * @return boolean
     */
    default boolean isGang(Parser parser) {
        return false;
    }

    /**
     * If the block is a Wall
     * @return boolean
     */
    default boolean isWall() {
        return false;
    }

    /**
     * Display function of a block in the window
     * @param context for the display
     * @param board The board where to look for the block
     * @param width The size of the window
     * @param height The size of the window
     */
    default void displayBlock(ApplicationContext context, Board board, int width, int height) {
        Objects.requireNonNull(board);
        context.renderFrame(graphics -> {
            if (this.isName())
                graphics.drawImage(Graphic.loadName(this), width / board.getX() * this.getx(), height / board.getY() * this.gety(), width / board.getX(), height / board.getY(), null);
            else if (this.isOperator())
                graphics.drawImage(Graphic.loadOp(), width / board.getX() * this.getx(), height / board.getY() * this.gety(), width / board.getX(), height / board.getY(), null);
            else if (this.isProperties())
                graphics.drawImage(Graphic.loadProp(this.getProp()), width / board.getX() * this.getx(), height / board.getY() * this.gety(), width / board.getX(), height / board.getY(), null);
            else if (this.isObj())
                graphics.drawImage(Graphic.loadName(this), width / board.getX() * this.getx(), height / board.getY() * this.gety(), width / board.getX(), height / board.getY(), null);
        });
    }

    /**
     * Display function of a block in the window according to his name
     * @param context for the display
     * @param name name of the block
     * @param board The board where to look for the block
     * @param width The size of the window
     * @param height The size of the window
     */
    default void displayBlockName(ApplicationContext context, Board board, int width, int height, NameEnum name) {
        Objects.requireNonNull(board);
        context.renderFrame(graphics -> graphics.drawImage(Graphic.loadName2(this, name), width / board.getX() * this.getx(), height / board.getY() * this.gety(), width / board.getX(), height / board.getY(), null));
    }

    /**
     * Function that deletes a block in the window
     * @param context for the display
     * @param board The board where to look for the block
     * @param width The size of the window
     * @param height The size of the window
     */
    default void clearBloc(ApplicationContext context, Board board, int width, int height) {
        Objects.requireNonNull(board);
        context.renderFrame(graphics -> {
            graphics.setColor(Color.BLACK);
            graphics.fill(new Rectangle(width / board.getX() * this.getx(), height / board.getY() * this.gety(), width / board.getX(), height / board.getY()));
        });
    }

    /**
     *Function that will manage the movement of a block in the window
     * @param context for the display
     * @param board The board
     * @param width The size of the window
     * @param height The size of the window
     * @param direction The direction in which the block should move
     * @param parser Parse it where there are properties
     */
    default void moveBlock(ApplicationContext context, Board board, int width, int height, int direction, Parser parser) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(parser);
        if (checkLimitBoard(board, direction)) return;
        this.clearBloc(context, board, width, height);
        moveDirection(context, parser, board, width, height, direction);
        for (var val : parser.getParserName().entrySet()) {
            if (val.getValue().contains(this)) {
                this.displayBlockName(context, board, width, height, val.getKey());
                return;
            } else
                this.displayBlock(context, board, width, height);
        }
        if (parser.getParserName().isEmpty())
            this.displayBlock(context, board, width, height);
    }

    /**
     * Function that checks the limits of the plateau
     */
    private boolean checkLimitBoard(Board board, int direction) {
        if (this.getx() >= board.getX() - 1 && direction == 0)
            return true;
        if (this.gety() >= board.getY() - 1 && direction == 1)
            return true;
        if (this.getx() == 0 && direction == 2)
            return true;
        return this.gety() == 0 && direction == 3;
    }

    /**
     * Travel management function
     */
    private void moveDirection(ApplicationContext context, Parser parser, Board board, int width, int height, int direction) {
        switch (direction) {
            case 0 -> displayMove(context, parser, board, width, height, 0);
            case 1 -> displayMove(context, parser, board, width, height, 1);
            case 2 -> {
                this.left();
                board.displayBlockPos(context, width, height, this.getx() + 1, this.gety());
            }
            case 3 -> {
                this.up();
                board.displayBlockPos(context, width, height, this.getx(), this.gety() + 1);
            }
        }
    }

    /**
     * Function will display blocks in a certain way relative to displacement
     */
    private void displayMove(ApplicationContext context, Parser parser, Board board, int width, int height, int direction) {
        switch (direction) {
            case 0 -> this.right();
            case 1 -> this.down();
        }
        for (var val : parser.getParserName().entrySet()) {
            if (val.getValue().contains(this))
                if (displayWithName(context, parser, board, width, height, direction, val)) return;
            else
                switch (direction) {
                    case 0 -> board.displayBlockPos(context, width, height, this.getx() - 1, this.gety());
                    case 1 -> board.displayBlockPos(context, width, height, this.getx(), this.gety() - 1);
                }
        }
        if (parser.getParserName().isEmpty())
            switch (direction) {
                case 0 -> board.displayBlockPos(context, width, height, this.getx() - 1, this.gety());
                case 1 -> board.displayBlockPos(context, width, height, this.getx(), this.gety() - 1);
            }
    }

    /**
     * Display function according to the name present in the parserName
     */
    private boolean displayWithName(ApplicationContext context, Parser parser, Board board, int width, int height, int direction, Map.Entry<NameEnum, ArrayList<Block>> val) {
        switch (direction) {
            case 0 -> {
                board.displayBlockPosName(context, parser, width, height, this.getx() - 1, this.gety());
                this.displayBlockName(context, board, width, height, val.getKey());
                return true;
            }
            case 1 -> {
                board.displayBlockPosName(context, parser, width, height, this.getx(), this.gety() - 1);
                this.displayBlockName(context, board, width, height, val.getKey());
                return true;
            }
        }
        return false;
    }
}
