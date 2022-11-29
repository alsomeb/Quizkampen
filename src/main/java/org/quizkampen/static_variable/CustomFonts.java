package org.quizkampen.static_variable;

import java.awt.*;

public class CustomFonts {
   //--------------------------------------------------this Font is on the bench----------------------------------------
   //public static Font dialog_Input = new Font(Font.DIALOG, Font.PLAIN,  CustomSizes.button_size);
   //-------------------------------------------------------------------------------------------------------------------
   public static Font dialog_Input_Button = new Font(Font.DIALOG, Font.PLAIN,  CustomSizes.button_size);
   public static Font serifButton  = new Font       (Font.SERIF, Font.PLAIN,  CustomSizes.button_size);
   public static Font dialog_Button = new Font      (Font.DIALOG, Font.PLAIN,  CustomSizes.button_size);
   public static Font monospace_Button = new Font   (Font.MONOSPACED, Font.PLAIN,  CustomSizes.button_size);

   public static Font dialog_Input_Label = new Font(Font.DIALOG, Font.PLAIN,  CustomSizes.label_size);
   public static Font serif_Label  = new Font       (Font.SERIF, Font.BOLD,  CustomSizes.label_size);
   public static Font dialog_Label = new Font      (Font.DIALOG, Font.BOLD,  CustomSizes.label_size);
   public static Font monospace_Label = new Font   (Font.MONOSPACED, Font.BOLD,  CustomSizes.label_size);

   //--------------------------------------------------Default Fonts----------------------------------------------------
   public static Font standard_Sansserif_Label = new Font   (Font.SANS_SERIF, Font.BOLD,  CustomSizes.label_size);
   public static Font standard_Sansserif_Button = new Font   (Font.SANS_SERIF, Font.BOLD,  CustomSizes.button_size);
   //--------------------------------------------------Current used Fonts-----------------------------------------------
   public static Font current_Font_Label = new Font   (Font.SANS_SERIF, Font.BOLD,  CustomSizes.label_size);
   public static Font current_Font_Button = new Font   (Font.SANS_SERIF, Font.BOLD,  CustomSizes.button_size);



}
