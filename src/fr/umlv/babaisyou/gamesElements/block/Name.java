package fr.umlv.babaisyou.gamesElements.block;

import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.gamesElements.block.Block;

import java.util.Objects;

/**
 * Class that will represent a Name in the game
 */
public class Name implements Block {
    private final NameEnum name;
    private int x;
    private int y;

    /**
     * Builder of a block Name
     * @param name his name
     * @param x coords
     * @param y coords
     */
    public Name(NameEnum name, int x, int y) {
        Objects.requireNonNull(name, "Name must be not null");
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("X and Y not correct");
        this.name = name;
        this.x = x;
        this.y = y;
    }

    /**
     * Position change function
     */
    @Override
    public void left() { x -= 1; }

    @Override
    public void right() { x += 1; }

    @Override
    public void up() { y -= 1; }

    @Override
    public void down() { y += 1; }

    @Override
    public NameEnum getName() { return name; }

    @Override
    public String toString() { return name + " (" + x + ',' + y + ')'; }

    @Override
    public boolean isName() { return true; }

    @Override
    public int getx() { return x; }

    @Override
    public int gety() { return y; }
}
