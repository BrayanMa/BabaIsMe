package fr.umlv.babaisyou.initialize;

import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.blockNames.PropEnum;
import fr.umlv.babaisyou.gamesElements.block.Name;
import fr.umlv.babaisyou.gamesElements.block.Obj;
import fr.umlv.babaisyou.gamesElements.block.Operator;
import fr.umlv.babaisyou.gamesElements.block.Properties;
import fr.umlv.babaisyou.gamesElements.board.Board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


/**
 * Class that will manage file playback for loading levels
 */
public class Files {

    /**
     * Function that will initialize the world from a file,
     * It will look at the file passed in parameter and add in a tray the good blocks
     * corresponding to the good coordinates in the file
     * @param fichier a String wich the name of the file
     * @return a new "Plateau"
     * @throws IOException .
     */
    public static Board init_word(String fichier) throws IOException {
        Objects.requireNonNull(fichier);
        var plateau = world_size(fichier);
        java.io.File file = new java.io.File(fichier);
        FileReader fr = new FileReader(file);
        char act = 0;
        StringBuilder entier = new StringBuilder();
        int act_int;
        coord pos = new coord();
        ArrayList<coord> line = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(fr)) {
            int c;
            int i = 0;
            int j = 0;
            while ((c = br.read()) != -1) {
                char ch = (char) c;
                if (c != '\n') {
                    if (j > 1) {
                        if (c != ' ') {
                            if (i == 0) {
                                act = ch;
                            }
                            if (ch == '[')
                                entier.setLength(0);
                            else if (ch == ',') {
                                act_int = Integer.parseInt(entier.toString());
                                pos.abs = act_int;
                                entier.setLength(0);
                            } else if (ch == ']') {
                                act_int = Integer.parseInt(entier.toString());
                                pos.ord = act_int;
                                entier.setLength(0);
                                line.add(pos);
                                pos = new coord();
                            } else
                                entier.append(ch);
                        }
                    }
                    i += 1;
                } else {
                    add_line(plateau, line, act);
                    line.clear();
                    i = 0;
                    j += 1;
                }
            }
        }
        return plateau;
    }


    /**
     * Function which will add the blocks in the board present on a line of the file
     */
    private static void add_line(Board gameboard, ArrayList<coord> line, char object) {
        Objects.requireNonNull(gameboard);
        Objects.requireNonNull(line);
        for (coord value : line) {
            add_block(gameboard, value.abs, value.ord, object);
        }
    }

    /**
     * Function that will add the blocks to the board
     */
    private static void add_block(Board gameboard, int abs, int ord, char object) {
        Objects.requireNonNull(gameboard);
        if ( abs < 0|| ord < 0 )
            throw new IllegalArgumentException("Cords are not valid");
        switch (object) {
            case 'A' -> addObj(gameboard, abs, ord, NameEnum.Arouf);
            case 'o' -> addNameEnum(gameboard, abs, ord, NameEnum.Arouf);
            case 'b' -> addNameEnum(gameboard, abs, ord, NameEnum.Baba);
            case 'B' -> addObj(gameboard, abs, ord, NameEnum.Baba);
            case 'w' -> addNameEnum(gameboard, abs, ord, NameEnum.Wall);
            case 'W' -> addObj(gameboard, abs, ord, NameEnum.Wall);
            case 'f' -> addNameEnum(gameboard, abs, ord, NameEnum.Flag);
            case 'F' -> addObj(gameboard, abs, ord, NameEnum.Flag);
            case 'v' -> addNameEnum(gameboard, abs, ord, NameEnum.Water);
            case 'V' -> addObj(gameboard, abs, ord, NameEnum.Water);
            case 's' -> addNameEnum(gameboard, abs, ord, NameEnum.Skull);
            case 'S' -> addObj(gameboard, abs, ord, NameEnum.Skull);
            case 'l' -> addNameEnum(gameboard, abs, ord, NameEnum.Lava);
            case 'L' -> addObj(gameboard, abs, ord, NameEnum.Lava);
            case 'r' -> addNameEnum(gameboard, abs, ord, NameEnum.Rock);
            case 'R' -> addObj(gameboard, abs, ord, NameEnum.Rock);
            case 'i' -> gameboard.add(new Operator(ord, abs));
            case 'k' -> addPropEnum(gameboard, abs, ord, PropEnum.Gang);
            case 'y' -> addPropEnum(gameboard, abs, ord, PropEnum.You);
            case 'n' -> addPropEnum(gameboard, abs, ord, PropEnum.Sink);
            case 'm' -> addPropEnum(gameboard, abs, ord, PropEnum.Melt);
            case 'h' -> addPropEnum(gameboard, abs, ord, PropEnum.Hot);
            case 'd' -> addPropEnum(gameboard, abs, ord, PropEnum.Defeat);
            case 'g' -> addPropEnum(gameboard, abs, ord, PropEnum.Win);
            case 'p' -> addPropEnum(gameboard, abs, ord, PropEnum.Push);
            case 'a' -> addPropEnum(gameboard, abs, ord, PropEnum.Stop);
        }
    }

    /**
     * Function that adds a PropEnum
     */
    private static void addPropEnum(Board gameboard, int abs, int ord, PropEnum stop) {
        gameboard.add( new Properties(stop, ord, abs));
    }

    /**
     * Function that adds an Obj
     */
    private static void addObj(Board gameboard, int abs, int ord, NameEnum baba) {
        gameboard.add(new Obj(baba, ord, abs));
    }

    /**
     * Function that adds a NameEnum
     */
    private static void addNameEnum(Board gameboard, int abs, int ord, NameEnum baba) {
        gameboard.add(new Name(baba, ord, abs));
    }

    /**
     * Function that will define the size of the tray
     */
    private static Board world_size(String world) throws IOException {
        Objects.requireNonNull(world);
        String act_int;
        int x, y, i, j;
        x = y = i = j = 0;
        StringBuilder act_int1 = new StringBuilder();
        java.io.File file = new java.io.File(world);
        FileReader fr = new FileReader(file);
        int c;
        try (BufferedReader br = new BufferedReader(fr)) {
            while ((c = br.read()) != -1) {
                j += 1;
                if (i == 2) {
                    return new Board(x, y);
                }
                if (c == '\n') {
                    act_int = act_int1.toString();
                    if (i == 0) {
                        x = Integer.parseInt(act_int);
                    } else {
                        y = Integer.parseInt(act_int);
                    }
                    act_int1.setLength(0);
                    i += 1;
                    j = 0;
                } else if (j <= 2) {
                    char ch = (char) c;
                    act_int1.append(ch);
                }
            }
            return new Board(x, y);
        }
    }

    private static class coord {
        private int abs;
        private int ord;
    }
}
