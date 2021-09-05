package fr.umlv.babaisyou.initialize;

import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.blockNames.PropEnum;
import fr.umlv.babaisyou.gameEngine.Engine;
import fr.umlv.babaisyou.gameEngine.Parser;
import fr.umlv.babaisyou.gamesElements.block.Name;
import fr.umlv.babaisyou.gamesElements.block.Operator;
import fr.umlv.babaisyou.gamesElements.block.Properties;
import fr.umlv.babaisyou.gamesElements.board.Board;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;

import java.awt.*;
import java.io.IOException;

/**
 * This class will be used for handling the arguments entered by the user
 */
public class Args {
    /**
     * Function that will analyse arguments
     * @param args arguments when the programme run
     */
    public static void analyseArgs(String[] args) {
        var parser = new Parser();
        Application.run(Color.BLACK, context -> {
            if(args.length <= 0){
                noArgsDetected(parser, context);
            } else{
                int y = -1;
                var level = new StringBuilder();
                var tab2 = new Board(3, 10);
                for(int i = 0; i < args.length; i++){
                    y = verifCommandes(args, y, level, tab2, i);
                }
                if(level.length() == 39 || level.length() == 48) {
                    argsWithLevel(parser, context, level, tab2);
                }else if(String.valueOf(level).equals("src/fr/umlv/babaisyou/externalFiles/LVL/")){
                    Board tab = null;
                    try {
                        tab = Files.init_word("src/fr/umlv/babaisyou/externalFiles/LVL/default-lvl.txt");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    assert tab != null;
                    fusionTab(tab, tab2);
                    Engine.gameEngine(tab, parser, context);
                    sequenceLevels(parser, context, level, tab2, tab);
                }
            }
            context.exit(0);
        });
    }

    private static void sequenceLevels(Parser parser, ApplicationContext context, StringBuilder level, Board tab2, Board tab) {
        for(int i = 1; i < 8; i++){
            try {
                tab = Files.init_word(level + "lvl" + i + ".txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            fusionTab(tab, tab2);
            if(!Engine.gameEngine(tab, parser, context))
                i--;
        }
    }

    private static void argsWithLevel(Parser parser, ApplicationContext context, StringBuilder level, Board tab2) {
        Board tab = null;
        try {
            tab = Files.init_word(String.valueOf(level));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert tab != null;
        fusionTab(tab, tab2);
        Engine.gameEngine(tab, parser, context);
    }

    private static void noArgsDetected(Parser parser, ApplicationContext context) {
        var level = "src/fr/umlv/babaisyou/externalFiles/LVL/default-lvl.txt";
        Board tab = null;
        try {
            tab = Files.init_word(level);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert tab != null;
        Engine.gameEngine(tab, parser, context);
    }

    private static void fusionTab(Board tab, Board tab2){
        int x = tab.getX() - 1, y = tab.getY() - 1;
        for(int i = 0; i < tab2.getBoard().size(); i++){
            switch (i % 3){
                case 0 : tab.add(new Name(tab2.getBoard().get(i).getName(), x - 2, y));break;
                case 1 : tab.add(new Operator(x - 1, y)); break;
                case 2 : if(tab2.getBoard().get(i).isName())
                    tab.add(new Name(tab2.getBoard().get(i).getName(), x, y));
                else
                    tab.add(new Properties(tab2.getBoard().get(i).getProp(), x, y));
                    y--; break;
                default : break;
            }
            /*if(i % 3 == 0)
                tab.add(new Name(tab2.getBoard().get(i).getName(), x - 2, y));
            if(i % 3 == 1)
                tab.add(new Operator(x - 1, y));
            if(i % 3 == 2){
                if(tab2.getBoard().get(i).isName())
                    tab.add(new Name(tab2.getBoard().get(i).getName(), x, y));
                else
                    tab.add(new Properties(tab2.getBoard().get(i).getProp(), x, y));
                y--;
            }*/
        }
    }

    private static int verifCommandes(String[] args, int y, StringBuilder level, Board tab2, int i) {
        int x;
        switch (args[i]) {
            case "--levels" -> level.append("src/fr/umlv/babaisyou/externalFiles/").append(args[i + 1]).append('/');
            case "--level" -> level.append("lvl").append(args[i + 1]).append(".txt");
            case "--execute" -> {
                x = 0;
                y++;
                x = verifName(args, x, y, tab2, i);
                verifExecProp(args, x, y, tab2, i);
            }
        }
        return y;
    }

    private static int verifName(String[] args, int x, int y, Board tab2, int i) {
        switch (args[i + 1]) {
            case ("BABA") -> x = addTab2(x, y, tab2, NameEnum.Baba);
            case ("FLAG") -> x = addTab2(x, y, tab2, NameEnum.Flag);
            case ("WALL") -> x = addTab2(x, y, tab2, NameEnum.Wall);
            case ("WATER") -> x = addTab2(x, y, tab2, NameEnum.Water);
            case ("SKULL") -> x = addTab2(x, y, tab2, NameEnum.Skull);
            case ("LAVA") -> x = addTab2(x, y, tab2, NameEnum.Lava);
            case ("ROCK") -> x = addTab2(x, y, tab2, NameEnum.Rock);
            case ("AROUF") -> x = addTab2(x, y, tab2, NameEnum.Arouf);
            default -> {
            }
        }
        return x;
    }

    private static int addTab2(int x, int y, Board tab2, NameEnum name) {
        tab2.add(new Name(name, x, y));
        tab2.add(new Operator(x + 1, y));
        x += 2;
        return x;
    }

    private static void verifExecProp(String[] args, int x, int y, Board tab2, int i) {
        switch (args[i + 3]) {
            case ("YOU") -> tab2.add(new Properties(PropEnum.You, x, y));
            case ("WIN") -> tab2.add(new Properties(PropEnum.Win, x, y));
            case ("STOP") -> tab2.add(new Properties(PropEnum.Stop, x, y));
            case ("PUSH") -> tab2.add(new Properties(PropEnum.Push, x, y));
            case ("MELT") -> tab2.add(new Properties(PropEnum.Melt, x, y));
            case ("HOT") -> tab2.add(new Properties(PropEnum.Hot, x, y));
            case ("DEFEAT") -> tab2.add(new Properties(PropEnum.Defeat, x, y));
            case ("SINK") -> tab2.add(new Properties(PropEnum.Sink, x, y));
            case ("GANG") -> tab2.add(new Properties(PropEnum.Gang, x, y));
            case ("BABA") -> tab2.add(new Name(NameEnum.Baba, x, y));
            case ("FLAG") -> tab2.add(new Name(NameEnum.Flag, x, y));
            case ("WALL") -> tab2.add(new Name(NameEnum.Wall, x, y));
            case ("WATER") -> tab2.add(new Name(NameEnum.Water, x, y));
            case ("SKULL") -> tab2.add(new Name(NameEnum.Skull, x, y));
            case ("LAVA") -> tab2.add(new Name(NameEnum.Lava, x, y));
            case ("ROCK") -> tab2.add(new Name(NameEnum.Rock, x, y));
            case ("AROUF") -> tab2.add(new Name(NameEnum.Arouf, x, y));
            default -> {
            }
        }
    }
}
