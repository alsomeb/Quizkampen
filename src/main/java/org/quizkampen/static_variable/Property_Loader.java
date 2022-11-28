package org.quizkampen.static_variable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property_Loader {

    public final static void loadproperty(){
        Properties gui_pr=new Properties();
        try {
            gui_pr.load(new FileInputStream("src/main/resources/Gui.properties"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fonts=gui_pr.getProperty      ("fonts","Standard_Sansserif_Button");
        String backGroundColor=gui_pr.getProperty ("backGroundColor","backGroundCopy");
        String buttonsColor=gui_pr.getProperty ("buttonsColor","Copy_Btn_clr");

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


        //backGroundColor
        if (backGroundColor.trim().equalsIgnoreCase("grey")){
            CustomColors.background_Clr = CustomColors.grey;
        }
        else if (backGroundColor.trim().equalsIgnoreCase("deep_Blue")){
            CustomColors.background_Clr = CustomColors.deep_Blue;
        }
        else if (backGroundColor.trim().equalsIgnoreCase("ocean_Blue")){
            CustomColors.background_Clr = CustomColors.ocean_Blue;
        } else if (backGroundColor.trim().equalsIgnoreCase("dark_Yellow")){
            CustomColors.background_Clr = CustomColors.dark_Yellow;
        }
        else CustomColors.background_Clr = CustomColors.backGroundCopy;


        //Buttons_Color
        if(buttonsColor.trim().equalsIgnoreCase("light_Button")){
            CustomColors.btn_Clr = CustomColors.light_Button;
        } else if (buttonsColor.trim().equalsIgnoreCase("light_Button2")){
            CustomColors.btn_Clr = CustomColors.light_Button2;
        } else if (buttonsColor.trim().equalsIgnoreCase("darker_Green")){
            CustomColors.btn_Clr = CustomColors.darker_Green;
        }else CustomColors.btn_Clr = CustomColors.btn_Clr_Copy;

    }
}
