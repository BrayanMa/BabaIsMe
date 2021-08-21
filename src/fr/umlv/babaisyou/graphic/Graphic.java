package fr.umlv.babaisyou.graphic;

import fr.umlv.babaisyou.gamesElements.block.Block;
import fr.umlv.babaisyou.blockNames.NameEnum;
import fr.umlv.babaisyou.blockNames.PropEnum;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * This class will be useful to load images into the game
 */
public class Graphic {

    /**
     * Function that will load an image relative to the name
     */
    public static Image loadName(Block block) {
        Objects.requireNonNull(block);
        StringBuilder string = new StringBuilder();
        if (block.isName())
            string.append("src/fr/umlv/babaisyou/externalFiles/IMG/Name/");
        if (block.isObj())
            string.append("src/fr/umlv/babaisyou/externalFiles/IMG/Obj/");
        if (block.isName()) {
            string.append("Text_");
        }
        string.append(block.getName());
        string.append(".gif");
        return getImage(string);
    }

    /**
     * Function that will load an image relative to the name, this is used to load an image when the block is present in parserName
     */
    public static Image loadName2(Block block, NameEnum name) {
        Objects.requireNonNull(block);
        Objects.requireNonNull(name);
        StringBuilder string = new StringBuilder();
        if (block.isName())
            string.append("src/fr/umlv/babaisyou/externalFiles/IMG/Name/");
        if (block.isObj())
            string.append("src/fr/umlv/babaisyou/externalFiles/IMG/Obj/");
        if (block.isName()) {
            string.append("Text_");
            string.append(block.getName());
        } else
            string.append(name);
        string.append(".gif");
        return getImage(string);
    }

    /**
     * Function that will load an image relative to the prop
     */
    public static Image loadProp(PropEnum prop) {
        Objects.requireNonNull(prop);
        StringBuilder string = new StringBuilder();
        string.append("src/fr/umlv/babaisyou/externalFiles/IMG/Prop/Text_");
        string.append(prop);
        string.append(".gif");
        return getImage(string);
    }

    /**
     * Function that will load an image relative to the operator
     */
    public static Image loadOp() {
        StringBuilder string = new StringBuilder();
        string.append("src/fr/umlv/babaisyou/externalFiles/IMG/Ope/Text_Is.gif");
        return getImage(string);
    }

    private static Image getImage(StringBuilder string) {
        Objects.requireNonNull(string);
        Image img = null;
        try {
            img = ImageIO.read(new File(String.valueOf(string)));
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return img;
    }
}
