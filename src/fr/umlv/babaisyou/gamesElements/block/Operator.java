package fr.umlv.babaisyou.gamesElements.block;

import fr.umlv.babaisyou.gamesElements.block.Block;

/**
 * This class will retrieve properties for associated blocks
 */
public class Operator implements Block {
    private final String name;
    private int x;
    private int y;

    /**
     * Builder of a operator
     * @param x coords
     * @param y coords
     */
    public Operator(int x, int y) {
        this.x = x;
        this.y = y;
        name = "Is";
    }

    public void left() {
        x -= 1;
    }

    public void right() {
        x += 1;
    }

    public void up() {
        y -= 1;
    }

    public void down() {
        y += 1;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public int getx() {
        return x;
    }

    @Override
    public int gety() {
        return y;
    }

    @Override
    public String toString() {
        return name + " (" + x + ',' + y + ')';
    }

}
