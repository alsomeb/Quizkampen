package org.quizkampen.static_variable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Property_Loader {

    public final static void loadproperty(){
        Properties gui_pr=new Properties();
        try {
            gui_pr.load(new FileInputStream("src/main/resources/Gui.properties"));

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fonts=gui_pr.getProperty      ("fonts","Standard_Sansserif_Button");
        String backGroundCollor=gui_pr.getProperty ("backGroundCollor","backGroundCopy");
        String buttonsCollor=gui_pr.getProperty ("buttonsCollor","Copy_Btn_clr");

        //fonts
        if (fonts.trim().equalsIgnoreCase("dialog")){
            CustomFonts.current_Font_Button =CustomFonts.dialog_Button;
            CustomFonts.current_Font_Label=CustomFonts.dialog_Label;
        } else if (fonts.trim().equalsIgnoreCase("monospace")) {
            CustomFonts.current_Font_Button =CustomFonts.monospace_Button;
            CustomFonts.current_Font_Label=CustomFonts.monospace_Label;
        } else if (fonts.trim().equalsIgnoreCase("serif")) {
            CustomFonts.current_Font_Button =CustomFonts.serifButton;
            CustomFonts.current_Font_Label=CustomFonts.serif_Label;
        }else if (fonts.trim().equalsIgnoreCase("dialog_Input")) {
            CustomFonts.current_Font_Button =CustomFonts.dialog_Input_Button;
            CustomFonts.current_Font_Label=CustomFonts.dialog_Input_Label;
        }
        else CustomFonts.current_Font_Button =CustomFonts.standard_Sansserif_Button;


        //backGroundCollor
        if (backGroundCollor.trim().equalsIgnoreCase("grey")){
            CustomCollors.background_Clr =CustomCollors.grey;
        }
        else if (backGroundCollor.trim().equalsIgnoreCase("deep_Blue")){
            CustomCollors.background_Clr =CustomCollors.deep_Blue;
        }
        else if (backGroundCollor.trim().equalsIgnoreCase("ocean_Blue")){
            CustomCollors.background_Clr =CustomCollors.ocean_Blue;
        } else if (backGroundCollor.trim().equalsIgnoreCase("dark_Yellow")){
            CustomCollors.background_Clr =CustomCollors.dark_Yellow;
        }
        else CustomCollors.background_Clr =CustomCollors.backGroundCopy;


        //Buttons_Collor
        if(buttonsCollor.trim().equalsIgnoreCase("light_Button")){
            CustomCollors.btn_Clr =CustomCollors.light_Button;
        } else if (buttonsCollor.trim().equalsIgnoreCase("light_Button2")){
            CustomCollors.btn_Clr =CustomCollors.light_Button2;
        } else if (buttonsCollor.trim().equalsIgnoreCase("darker_Green")){
            CustomCollors.btn_Clr =CustomCollors.darker_Green;
        }else CustomCollors.btn_Clr =CustomCollors.btn_Clr_Copy;

    }
}
