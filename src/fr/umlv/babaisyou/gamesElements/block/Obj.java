package fr.umlv.babaisyou.gamesElements.block;

import fr.umlv.babaisyou.gameEngine.Parser;
import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.blockNames.PropEnum;

import java.util.Objects;

/**
 * Class that will represent a game element
 */
public class Obj implements Block {
    private final NameEnum name;
    private int x;
    private int y;

    /**
     * Builder of a bloc Obj
     * @param name his name
     * @param x coords
     * @param y coords
     */
    public Obj(NameEnum name, int x, int y) {
        Objects.requireNonNull(name, "Name must be not null");
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coordonnées non valides");
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override
    public void left() {
        x -= 1;
    }

    @Override
    public void right() {
        x += 1;
    }

    @Override
    public void up() {
        y -= 1;
    }

    @Override
    public void down() {
        y += 1;
    }

    @Override
    public boolean isWall() {
        return name.equals(NameEnum.Wall);
        //return name == NameEnum.Wall;
    }

    /**
     * Functions used to find out whether certain blocks respect certain properties
     * @param parser what contains the properties
     * @return a boolean
     */
    @Override
    public boolean isYou(Parser parser) {
        Objects.requireNonNull(parser);
        for (var val : parser.getParser().get(PropEnum.You)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }


    @Override
    public boolean isGang(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Gang))
            return false;
        for (var val : parser.getParser().get(PropEnum.Gang)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public boolean isStop(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Stop))
            return false;
        for (var val : parser.getParser().get(PropEnum.Stop)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public boolean isPush(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Push))
            return false;
        for (var val : parser.getParser().get(PropEnum.Push)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public boolean isMelt(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Melt))
            return false;
        for (var val : parser.getParser().get(PropEnum.Melt)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public boolean isHot(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Hot))
            return false;
        for (var val : parser.getParser().get(PropEnum.Hot)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public boolean isDefeat(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Defeat))
            return false;
        for (var val : parser.getParser().get(PropEnum.Defeat)) {
            if (this.equals(val))
                return true;
        }
        return false;
    }

    @Override
    public boolean isSink(Parser parser) {
        Objects.requireNonNull(parser);
        if (!parser.getParser().containsKey(PropEnum.Sink))
            return false;
        for (var val : parser.getParser().get(PropEnum.Sink)) {
            if (this.equals(val))
                return true;
        }
        return false;
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
    public NameEnum getName() {
        return name;
    }

    @Override
    public boolean isObj() {
        return true;
    }

    @Override
    public String toString() {
        return name + "obj" + " (" + x + ',' + y + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return x == obj.x &&
                y == obj.y &&
                name == obj.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, x, y);
    }
}

