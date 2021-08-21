package fr.umlv.babaisyou.gamesElements.block;

import fr.umlv.babaisyou.blockNames.PropEnum;
import fr.umlv.babaisyou.gamesElements.block.Block;

/**
 * This class will define the properties in the program
 */
public class Properties implements Block {
    private final PropEnum name;
    private int x;
    private int y;

    /**
     * Builder of a property
     * @param name name
     * @param x coords
     * @param y coords
     */
    public Properties(PropEnum name, int x, int y) {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coords not valid");
        this.name = name;
        this.x = x;
        this.y = y;
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
    public PropEnum getProp() {
        return name;
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
    public boolean isProperties() {
        return true;
    }

    @Override
    public String toString() {
        return name + " (" + x + ',' + y + ')';
    }
}
