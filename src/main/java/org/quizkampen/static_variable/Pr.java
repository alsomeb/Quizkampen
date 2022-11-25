package org.quizkampen.static_variable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Pr {

    public final static void loadproperty(){
        Properties gui_pr=new Properties();
        try {
            gui_pr.load(new FileInputStream("src/main/resources/Gui.properties"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String FontsButton=gui_pr.getProperty("FontsButton","Sansserif");



        if (FontsButton.trim().equalsIgnoreCase("Dialog")){
            CustomFonts.current_Font_Button =CustomFonts.Dialog;
        } else if (FontsButton.trim().equalsIgnoreCase("Monospace")) {
            CustomFonts.current_Font_Button =CustomFonts.Monospace;
        }
        else CustomFonts.current_Font_Button =CustomFonts.Standard_Sansserif_Button;


    }
}
