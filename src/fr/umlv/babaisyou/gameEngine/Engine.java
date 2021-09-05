package fr.umlv.babaisyou.gameEngine;

import fr.umlv.babaisyou.blockNames.PropEnum;
import fr.umlv.babaisyou.gamesElements.block.Block;
import fr.umlv.babaisyou.gamesElements.board.Board;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;
import fr.umlv.zen5.KeyboardKey;
import fr.umlv.zen5.ScreenInfo;

import java.util.ArrayList;
import java.util.Objects;

/**
 * User Movements and Actions Management Class
 */
public class Engine {

    /**
     * Function which places in a board the blocks present in the box following the movement of the player box
     */
    private static ArrayList<Block> assignPlat(Board board, Block block, int direction) {
        return switch (direction) {
            case 0 -> board.emptyBox(block.getx() + 1, block.gety());
            case 1 -> board.emptyBox(block.getx(), block.gety() + 1);
            case 2 -> board.emptyBox(block.getx() - 1, block.gety());
            case 3 -> board.emptyBox(block.getx(), block.gety() - 1);
            default -> null;
        };
    }

    /**
     * Function that will manage the movement in relation to the properties of the blocks in the board
     */
    private static void move(ApplicationContext context, Block block, Board board, Parser parser, int width, int height, int direction) {
        ArrayList<Block> plat = Engine.assignPlat(board, block, direction);
        assert plat != null;
        if (simpleMove(context, block, board, parser, width, height, direction, plat)) return;
        for (var tmp : plat) {
            if (tmp.gety() == board.getY())
                return;
            else if (block.isGang(parser) && tmp.isWall() && block.isYou(parser))
                conditionGang(context, block, board, parser, width, height, direction, tmp);
            else if (tmp.isStop(parser)) return;
            else if (tmp.isDefeat(parser) && block.isYou(parser)) {
                conditionDefeat(context, block, board, width, height);
                return;
            } else if (tmp.isProperties() || tmp.isName() || tmp.isOperator() || tmp.isPush(parser))
                conditionPush(context, block, board, parser, width, height, direction, tmp);
            else if (tmp.isSink(parser) || (tmp.isHot(parser) && block.isMelt(parser))) {
                conditionSinkOrHot(context, block, board, width, height, tmp);
                return;
            } else
                block.moveBlock(context, board, width, height, direction, parser);
        }
    }

    /**
     * Function that performs simple movement in one direction
     */
    private static boolean simpleMove(ApplicationContext context, Block block, Board board, Parser parser, int width, int height, int direction, ArrayList<Block> plat) {
        if (plat.isEmpty()) {
            block.moveBlock(context, board, width, height, direction, parser);
            return true;
        }
        return false;
    }

    /**
     * Function that will perform an action that depends on the property of the following block
     */
    private static void conditionSinkOrHot(ApplicationContext context, Block block, Board board, int width, int height, Block tmp) {
        board.remove(tmp);
        board.remove(block);
        block.clearBloc(context, board, width, height);
        tmp.clearBloc(context, board, width, height);
        board.displayBlockPos(context, width, height, block.getx(), block.gety());
    }

    /**
     * Function that will perform an action that depends on the property of the following block
     */
    private static void conditionPush(ApplicationContext context, Block block, Board board, Parser parser, int width, int height, int direction, Block tmp) {
        ArrayList<Block> plat;
        move(context, tmp, board, parser, width, height, direction);
        plat = Engine.assignPlat(board, block, direction);
        if (plat.isEmpty())
            block.moveBlock(context, board, width, height, direction, parser);
    }

    /**
     * Function that will perform an action that depends on the property of the following block
     */
    private static void conditionDefeat(ApplicationContext context, Block block, Board board, int width, int height) {
        block.clearBloc(context, board, width, height);
        board.remove(block);
        board.displayBlockPos(context, width, height, block.getx(), block.gety());
    }

    /**
     * Function that will perform an action that depends on the property of the following block
     */
    private static void conditionGang(ApplicationContext context, Block block, Board board, Parser parser, int width, int height, int direction, Block tmp) {
        board.remove(tmp);
        tmp.clearBloc(context, board, width, height);
        block.moveBlock(context, board, width, height, direction, parser);
    }

    /**
     * Function which will make the game function, it will analyze the movements of the player and adapt this movement in concequence in relation to the blocks present in the table as well as their properties. This will be done in the move function. It also takes care of correctly displaying the movement on the screen
     * @param board The game board
     * @param parser To know the properties
     * @param context For graphic display
     * @return A boolean to know the state of the current game
     */
    public static Boolean gameEngine(Board board, Parser parser, ApplicationContext context) {
        Objects.requireNonNull(board);
        Objects.requireNonNull(parser);
        ScreenInfo screenInfo = context.getScreenInfo();
        int width = (int) screenInfo.getWidth();
        int height = (int) screenInfo.getHeight();
        displayAndProp(board, parser, context, width, height);
        for (; ; ) {
            if (parser.defeat()) {
                context.exit(0);
                return false;
            }
            Event event = context.pollOrWaitEvent(10);
            if (event == null)
                continue;
            Action action = getAction(board, parser, context, width, height, event);
            if (endGame(parser, event, action) == 1) return true;
            else if (endGame(parser, event, action) == 2) return false;
        }
    }

    private static void displayAndProp(Board board, Parser parser, ApplicationContext context, int width, int height) {
        board.recupProp(parser, context, width, height);
        board.displayBoard(context, width, height);
        parser.displayName(context, board, width, height);
    }

    private static Action getAction(Board board, Parser parser, ApplicationContext context, int width, int height, Event event) {
        Action action = event.getAction();
        action(board, parser, context, width, height, event, action, KeyboardKey.RIGHT, 0);
        action(board, parser, context, width, height, event, action, KeyboardKey.DOWN, 1);
        action(board, parser, context, width, height, event, action, KeyboardKey.LEFT, 2);
        action(board, parser, context, width, height, event, action, KeyboardKey.UP, 3);
        return action;
    }

    /**
     * Function which analyzes if the player lost or won returns the result of this
     */
    private static int endGame(Parser parser, Event event, Action action) {
        if (parser.victory())
            return 1;
        else if (parser.defeat() || (event.getKey() == KeyboardKey.R && action == Action.KEY_PRESSED))
            return 2;
        return 0;
    }

    /**
     * Function that analyzes the action and key used by the player
     */
    private static void action(Board board, Parser parser, ApplicationContext context, int width, int height, Event event, Action action, KeyboardKey dir, int i) {
        if (event.getKey() == dir && action == Action.KEY_PRESSED) {
            for (var val : parser.getParser().get(PropEnum.You)) {
                Engine.move(context, val, board, parser, width, height, i);
            }
            extractProp(board, parser, context, width, height);
        }
    }

    /**
     * Function to reset the Parser and the ParserName
     */
    private static void extractProp(Board board, Parser parser, ApplicationContext context, int width, int height) {
        board.recupProp(parser, context, width, height);
        parser.transfereProp();
    }
}
