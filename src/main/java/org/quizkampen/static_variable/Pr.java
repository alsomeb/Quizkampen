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
        String FontsButton=gui_pr.getProperty      ("FontsButton","Sansserif");
        String BackgroundCollor=gui_pr.getProperty ("BackgroundCollor","Backgroundcopy");
        String ButtonsCollor=gui_pr.getProperty ("ButtonsCollor","Copy_Btn_clr");

        //Fonts
        if (FontsButton.trim().equalsIgnoreCase("Dialog")){
            CustomFonts.current_Font_Button =CustomFonts.Dialog;
        } else if (FontsButton.trim().equalsIgnoreCase("Monospace")) {
            CustomFonts.current_Font_Button =CustomFonts.Monospace;
        }
        else CustomFonts.current_Font_Button =CustomFonts.Standard_Sansserif_Button;
        //BackgroundCollor
        if (BackgroundCollor.trim().equalsIgnoreCase("Grey")){
            CustomCollors.Background_clr=CustomCollors.Grey;
        }
        else if (BackgroundCollor.trim().equalsIgnoreCase("Deep_Blue")){
            CustomCollors.Background_clr=CustomCollors.Deep_Blue;
        }
        else if (BackgroundCollor.trim().equalsIgnoreCase("Ocean_Blue")){
            CustomCollors.Background_clr=CustomCollors.Ocean_Blue;
        } else if (BackgroundCollor.trim().equalsIgnoreCase("Dark_Yellow")){
            CustomCollors.Background_clr=CustomCollors.Dark_Yellow;
        }
        else CustomCollors.Background_clr=CustomCollors.Backgroundcopy;
        //Buttons_Collor
        if(ButtonsCollor.trim().equalsIgnoreCase("Light_Button")){
            CustomCollors.Btn_clr=CustomCollors.Light_Button;
        } else if (ButtonsCollor.trim().equalsIgnoreCase("Light_Button2")){
            CustomCollors.Btn_clr=CustomCollors.Light_Button2;
        } else if (ButtonsCollor.trim().equalsIgnoreCase("Darker_Green")){
            CustomCollors.Btn_clr=CustomCollors.Darker_Green;
        }else CustomCollors.Btn_clr=CustomCollors.Copy_Btn_clr;

    }
}
