package fr.umlv.babaisyou.gameEngine;

import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.blockNames.PropEnum;
import fr.umlv.babaisyou.gamesElements.block.Block;
import fr.umlv.babaisyou.gamesElements.board.Board;
import fr.umlv.zen5.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * This class will retrieve properties for associated blocks
 */
public class Parser {
    private final HashMap<PropEnum, ArrayList<Block>> parser;
    private final HashMap<NameEnum, ArrayList<Block>> parserName;

    /**
     * Constructor of a parser
     */
    public Parser() {
        this.parser = new HashMap<>();
        this.parserName = new HashMap<>();
    }

    /**
     * Function that adds an object to the hashmap
     * @param prop the property
     * @param list the list of block
     */
    public void add(PropEnum prop, ArrayList<Block> list) {
        Objects.requireNonNull(prop);
        Objects.requireNonNull(list);
        var newlst = List.copyOf(list);
        if (parser.containsKey(prop))
            parser.get(prop).addAll(newlst);
        else
            parser.put(prop, list);
    }

    /**
     * Function that adds an object to the hashmap Name
     * @param name the name
     * @param list the list of block
     */
    public void addName(NameEnum name, ArrayList<Block> list) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(list);
        if (parserName.containsKey(name)) parserName.get(name).addAll(list);
        else parserName.put(name, list);
    }

    /**
     *
     * Function that removes elements from hashmaps
     * @return int To know if we should display if there is a change of name
     */
    public int deleteAllKey() {
        parser.clear();
        if (!parserName.isEmpty()) {
            parserName.clear();
            return 1;
        }
        return 0;
    }

    /**
     * Function that determines if a game is won or not
     * @return boolean
     */
    public boolean victory() {
        if (!parser.containsKey(PropEnum.You) || !parser.containsKey(PropEnum.Win))
            return false;
        for (var val : parser.get(PropEnum.You)) {
            for (var val2 : parser.get(PropEnum.Win)) {
                if (val.equals(val2))
                    return true;
                if (val.getx() == val2.getx() && val.gety() == val2.gety())
                    return true;
            }
        }
        return false;
    }

    /**
     * Function that determines if a game is lost or not
     * @return boolean
     */
    public boolean defeat() {
        return !parser.containsKey(PropEnum.You) || parser.get(PropEnum.You).isEmpty();
    }

    /**
     * Parser return function
     * @return The parser
     */
    public HashMap<PropEnum, ArrayList<Block>> getParser() {
        return parser;
    }

    /**
     * Parser return function
     * @return The parserName
     */
    public HashMap<NameEnum, ArrayList<Block>> getParserName() {
        return parserName;
    }


    /**
     * Function which will add in the NameParser the names which are related to other names
     */
    public void transfereProp() {
        if (getParserName().isEmpty())
            return;
        for (var valname : parserName.entrySet()) {
            var list = new ArrayList<PropEnum>();
            for (var prop : parser.entrySet()) {
                for (var objprop : prop.getValue()) {
                    if (valname.getKey() == objprop.getName()) {
                        if (!list.contains(prop.getKey()))
                            list.add(prop.getKey());
                    }
                }
            }
            for (var val : list) {
                add(val, valname.getValue());
            }
        }
    }

    /**
     * Function that will manage the display relative to the parsername
     * @param context for the display
     * @param board the game board
     * @param width size
     * @param height size
     */
    public void displayName(ApplicationContext context, Board board, int width, int height) {
        Objects.requireNonNull(board);
        for (var val : parserName.entrySet()) {
            for (var tmp : val.getValue()) {
                tmp.clearBloc(context, board, width, height);
                tmp.displayBlockName(context, board, width, height, val.getKey());
            }
        }
    }

    @Override
    public String toString() {
        var string = new StringBuilder();
        for (var val : parser.entrySet()) {
            string.append(val.getKey()).append(" -> ").append(val.getValue());
            string.append('\n');
        }
        for (var val : parserName.entrySet()) {
            string.append(val.getKey()).append(" -> ").append(val.getValue());
            string.append('\n');
        }
        return String.valueOf(string);
    }
}
