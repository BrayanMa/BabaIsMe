package fr.umlv.babaisyou.gamesElements.board;

import fr.umlv.babaisyou.gameEngine.Parser;
import fr.umlv.babaisyou.gamesElements.block.Block;
import fr.umlv.zen5.ApplicationContext;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class that will represent the game board with the blocks inside
 */
public class Board {
    private final ArrayList<Block> board;
    private final int x;
    private final int y;

    /**
     * Constructor of the board
     * @param x size max
     * @param y size max
     */
    public Board(int x, int y) {
        if (x <= 0 || y <= 0)
            throw new IllegalArgumentException("Board's cords are not valid");
        this.board = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    /**
     * Function that adds a block to the board
     * @param block The block to add
     */
    public void add(Block block) {
        Objects.requireNonNull(block);
        if (block.getx() >= getX() || block.gety() >= getY())
            throw new IllegalArgumentException("Bloc's cords are not valid for the board");
        if (!(board.contains(block)))
            board.add(block);
    }

    /**
     * Function that remove a block to the board
     * @param block The block to remove
     */
    public void remove(Block block) {
        Objects.requireNonNull(block);
        board.remove(block);
    }

    /**
     * Function that will analyze the board to recover the properties of the blocks
     * @param parse to store the properties
     * @param context for the dispaly part
     * @param width size
     * @param height size
     */
    public void recupProp(Parser parse, ApplicationContext context, double width, double height) {
        Objects.requireNonNull(parse);
        int VerifNameEmpty = parse.deleteAllKey();
        for (var name : board) {
            if (name.isName()) {
                for (var op : board) {
                    checkOperator(parse, name, op);
                }
            }
        }
        if (VerifNameEmpty == 1 && parse.getParserName().isEmpty())
            this.displayBoard(context, (int) width, (int) height);
        if (VerifNameEmpty == 0 && !parse.getParserName().isEmpty())
            parse.displayName(context, this, (int) width, (int) height);
    }

    /**
     * Analysis function of the arrangement of the blocks in the tray
     * @param parse to store the properties
     * @param name block
     * @param op block
     */
    private void checkOperator(Parser parse, Block name, Block op) {
        if (op.isOperator()) {
            if (op.getx() == name.getx() + 1 && op.gety() == name.gety()) {
                var listx = new ArrayList<Block>();
                var listxname = new ArrayList<Block>();
                checkProp(listx, name, op, parse, 'x');
                verifName(listxname, name, op, parse, 'x');
            }
            if (op.gety() == name.gety() + 1 && op.getx() == name.getx()) {
                var listy = new ArrayList<Block>();
                var listyname = new ArrayList<Block>();
                checkProp(listy, name, op, parse, 'y');
                verifName(listyname, name, op, parse, 'y');
            }
        }
    }

    /**
     * Analysis function of the arrangement of the blocks in the tray
     */
    private void checkProp(ArrayList<Block> list, Block name, Block op, Parser parse, char c) {
        for (var prop : board) {
            if (prop.isProperties()) {
                posCorrect(list, name, op, parse, prop, c);
            }
        }
    }

    /**
     * Analysis function of the arrangement of the blocks in the tray
     */
    private void verifName(ArrayList<Block> list, Block name, Block op, Parser parse, char c) {
        for (var prop : board) {
            if (prop.isName()) {
                posCorrect(list, name, op, parse, prop, c);
            }
        }
    }

    /**
    *Analysis function of the arrangement of the blocks in the tray
     */
    private void posCorrect(ArrayList<Block> list, Block name, Block op, Parser parse, Block prop, char c) {
        if (prop.getx() == op.getx() + 1 && prop.gety() == op.gety() && c == 'x') {
            this.recupObj(list, name);
            if (prop.isName())
                parse.addName(prop.getName(), list);
            if (prop.isProperties())
                parse.add(prop.getProp(), list);
        }
        if (prop.gety() == op.gety() + 1 && prop.getx() == op.getx() && c == 'y') {
            if (!list.contains(name))
                this.recupObj(list, name);
            if (prop.isName())
                parse.addName(prop.getName(), list);
            if (prop.isProperties())
                parse.add(prop.getProp(), list);
        }
    }

    /**
     * Analysis function of the arrangement of the blocks in the tray
     */
    private void recupObj(ArrayList<Block> list, Block name) {
        for (var val : board) {
            if (val.isObj()) {
                if (name.getName().equals(val.getName())) {
                    if (!list.contains(val))
                        list.add(val);
                }
            }
        }
    }

    /**
     * Function that return y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Function that return x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Function which places in a table the blocks located at a given position in the board
     * @param x coords
     * @param y coords
     * @return Array
     */
    public ArrayList<Block> emptyBox(int x, int y) {
        var tmp = new ArrayList<Block>();
        for (var val : board) {
            if (val.getx() == x && val.gety() == y)
                tmp.add(val);
        }
        return tmp;
    }

    /**
     * Fonction that retrun the board
     * @return the board
     */
    public ArrayList<Block> getBoard() {
        return board;
    }

    /**
     * Function that displays the table on the screen
     * @param context for the display
     * @param width size
     * @param height size
     */
    public void displayBoard(ApplicationContext context, int width, int height) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.BLACK);
            graphics.fill(new Rectangle2D.Float(0, 0, width, height));
            graphics.setColor(Color.WHITE);
            for (var val : this.getBoard()) {
                val.displayBlock(context, this, width, height);
            }
        });
    }


    /**
     * Function that displays a block at a position
     * @param context for display
     * @param width size
     * @param height size
     * @param x coords
     * @param y coords
     */
    public void displayBlockPos(ApplicationContext context, int width, int height, int x, int y) {
        for (var val : this.getBoard()) {
            if (val.getx() == x && val.gety() == y)
                val.displayBlock(context, this, width, height);
        }
    }

    /**
     * Function that displays a block at a position, depending on its name
     * @param context for display
     * @param parser parser that contains the properties
     * @param width size
     * @param height size
     * @param x coords
     * @param y coords
     */
    public void displayBlockPosName(ApplicationContext context, Parser parser, int width, int height, int x, int y) {
        Objects.requireNonNull(parser);
        for (var tmp : parser.getParserName().entrySet()) {
            for (var tmp2 : tmp.getValue()) {
                if (tmp2.getx() == x && tmp2.gety() == y) {
                    tmp2.displayBlockName(context, this, width, height, tmp.getKey());
                }
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (var val : board) {
            string.append(val.toString());
            string.append('\n');
        }
        return String.valueOf(string);
    }
}

