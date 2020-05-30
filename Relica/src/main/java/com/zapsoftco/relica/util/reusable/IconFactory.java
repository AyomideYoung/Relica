package com.zapsoftco.relica.util.reusable;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class IconFactory {
    public static Text createIcon(){
       Text text =  new Text("$");
        text.setFont(Font.font("Keep Calm Med", 30));

        return text;
    }

    public static Text createIcon(char character){
        Text text =  new Text(new Character(character).toString());
        text.setFont(Font.font("Keep Calm Med", 30));

        return text;
    }
}
