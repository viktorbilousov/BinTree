package com.company.printer;


import java.util.ArrayList;
import java.util.List;

public  class ConsolePrinter {

    public enum Format {

        // Reset
        Reset("\033[0m", null, null),      //RESET

        // Regular Colors

        Black("\033[0;30m", Color.Black,    null),        // Black
        Red("\033[0;31m",   Color.Red,      null),          // Red
        Green("\033[0;32m", Color.Green,    null),        // Green
        Yellow("\033[0;33m",Color.Yellow,   null),       // Yellow
        Blue("\033[0;34m",  Color.Blue,     null),         // Blue
        Purple("\033[0;35m",Color.Purple,   null),       // Purple
        Cyan("\033[0;36m",  Color.Cyan,     null),         // Cyan
        White("\033[0;37m", Color.White,    null),        // White

        // Bold
        BBlack("\033[1;30m", Color.Black,    Style.Bold),        // Black
        BRed("\033[1;31m",   Color.Red,      Style.Bold),          // Red
        BGreen("\033[1;32m", Color.Green,    Style.Bold),        // Green
        BYellow("\033[1;33m",Color.Yellow,   Style.Bold),       // Yellow
        BBlue("\033[1;34m",  Color.Blue,     Style.Bold),         // Blue
        BPurple("\033[1;35m",Color.Purple,   Style.Bold),       // Purple
        BCyan("\033[1;36m",  Color.Cyan,     Style.Bold),         // Cyan
        BWhite("\033[1;37m", Color.White,    Style.Bold),        // White

        // Underline
        UBlack("\033[4;30m", Color.Black,    Style.Underline),        // Black
        URed("\033[4;31m",   Color.Red,      Style.Underline),          // Red
        UGreen("\033[4;32m", Color.Green,    Style.Underline),        // Green
        UYellow("\033[4;33m",Color.Yellow,   Style.Underline),       // Yellow
        UBlue("\033[4;34m",  Color.Blue,     Style.Underline),         // Blue
        UPurple("\033[4;35m",Color.Purple,   Style.Underline),       // Purple
        UCyan("\033[4;36m",  Color.Cyan,     Style.Underline),         // Cyan
        UWhite("\033[4;37m", Color.White,    Style.Underline),        // White


        // Background
        On_Black("\033[40m",  Color.Black,    Style.Background),       // Black
        On_Red("\033[41m",    Color.Red,      Style.Background),
        On_Green("\033[42m",  Color.Red,      Style.Background),
        On_Yellow("\033[43m", Color.Red,      Style.Background),
        On_Blue("\033[44m",   Color.Red,      Style.Background),
        On_Purple("\033[45m", Color.Red,      Style.Background),
        On_Cyan("\033[46m",   Color.Red,      Style.Background),
        On_White("\033[47m",  Color.Red,      Style.Background),

        // High Intensty

        IBlack("\033[0;90m", Color.Black,    Style.HighIntensty),        // Black
        IRed("\033[0;91m",   Color.Red,      Style.HighIntensty),          // Red
        IGreen("\033[0;92m", Color.Green,    Style.HighIntensty),        // Green
        IYellow("\033[0;93m",Color.Yellow,   Style.HighIntensty),       // Yellow
        IBlue("\033[0;94m",  Color.Blue,     Style.HighIntensty),         // Blue
        IPurple("\033[0;95m",Color.Purple,   Style.HighIntensty),       // Purple
        ICyan("\033[0;96m",  Color.Cyan,     Style.HighIntensty),         // Cyan
        IWhite("\033[0;97m", Color.White,    Style.HighIntensty),        // White


        BIBlack("\033[1;90m", Color.Black,    Style.BoldHighIntensty),        // Black
        BIRed("\033[1;91m",   Color.Red,      Style.BoldHighIntensty),          // Red
        BIGreen("\033[1;92m", Color.Green,    Style.BoldHighIntensty),        // Green
        BIYellow("\033[1;93m",Color.Yellow,   Style.BoldHighIntensty),       // Yellow
        BIBlue("\033[1;94m",  Color.Blue,     Style.BoldHighIntensty),         // Blue
        BIPurple("\033[1;95m",Color.Purple,   Style.BoldHighIntensty),       // Purple
        BICyan("\033[1;96m",  Color.Cyan,     Style.BoldHighIntensty),         // Cyan
        BIWhite("\033[1;97m", Color.White,    Style.BoldHighIntensty),        // White


        On_IBlack("\033[0;100m", Color.Black,    Style.HighIntenstyBackgrounds),        // Black
        On_IRed("\033[0;101m",   Color.Red,      Style.BoldHighIntensty),          // Red
        On_IGreen("\033[0;102m", Color.Green,    Style.BoldHighIntensty),        // Green
        On_IYellow("\033[0;103m",Color.Yellow,   Style.BoldHighIntensty),       // Yellow
        On_IBlue("\033[0;104m",  Color.Blue,     Style.BoldHighIntensty),         // Blue
        On_IPurple("\033[0;105m",Color.Purple,   Style.BoldHighIntensty),       // Purple
        On_ICyan("\033[0;106m",  Color.Cyan,     Style.BoldHighIntensty),         // Cyan
        On_IWhite("\033[0;107m", Color.White,    Style.BoldHighIntensty);       // White



        private final String format;
        private final Color color;
        private final Style style;

        Format(String format, Color color, Style style) {
            this.format = format;
            this.color = color;
            this.style = style;
        }

        @Override
        public String toString() {
            return format;
        }

        public static Format getFormat(Color color, Style style) {

            Color[] colors = Color.values();
            Style[] styles = Style.values();
            Format[] formats = Format.values();
            int colorindex = -1;
            int styleindex = 0;
            if(color == null){
                color = Color.White;
            }
            for (int i = 0; i < colors.length; i++) {
                if(color.equals(colors[i])) {
                    colorindex = i ;
                    break;
                }
            }

            for (int i = 0; i < styles.length; i++) {
                if(styles[i].equals(style)){
                    styleindex = i+1;
                    break;
                }
            }

            return formats[styleindex * colors.length + colorindex+1];
        }
        

        private enum Color{
            Black("<black>"),           // Black
            Red("<red>"),               // Red
            Green("<green>"),           // Green
            Yellow("<yellow>"),         // Yellow
            Blue("<blue>"),             // Blue
            Purple("<purple>"),         // Purple
            Cyan("<cyan>"),             // Cyan
            White("<white>");           // White

            private final String colorName;

            Color(String color){
                colorName = color;
            }
            @Override
            public String toString() {
                return colorName;
            }

            public static boolean isColor(String colorName){
                for (Color color : values()) {
                    if(color.colorName.equals(colorName))
                        return true;
                }
                return false;
            }

            public static Color valueOfToken(String colorToken){
                for (Color color : values()) {
                    if(color.colorName.equals(colorToken.toLowerCase()))
                        return color;
                }
                return null;
            }
        }

        private enum Style {

            Bold("<b>"),
            Underline("<u>"),
            Background("<bk>"),
            HighIntensty("<i>"),
            BoldHighIntensty("<bi>"),
            HighIntenstyBackgrounds("<hibk>");

            private final String nameStyle;
            Style(String name){
                nameStyle = name;
            }

            public static boolean isStyle(String styleName){
                for (Style style : values()) {
                    if(style.nameStyle.equals(styleName))
                        return true;
                }
                return false;
            }

            public static Style valueOfToken(String styleToken){
                for (Style color : values()) {
                    if(color.nameStyle.equals(styleToken))
                        return color;
                }
                return null;
            }
        }

    }


    public void println(String line){
        line += " ";
        System.out.println(formatString(line));
    }

    public void printf(String line){
        line += " ";
        System.out.printf(formatString(line));
    }

    public String formatText(String s, Format format){
        if(format == null) return s;
        return format + s + Format.Reset;
    }

    public String formatText(String s, Format format, int from, int to){
        if(format == null) return s;
        return s.substring(0, from) + format + s.substring(from, to) + Format.Reset + s.substring(to, s.length());
    }

    private String formatString(String line)  {

        String newLine = "";
        ArrayList<Format> stack = new ArrayList<>();

        int indexF = -1;
        int indexL = -1;
        int lastIndexF = line.lastIndexOf("<");
        int lastIndexL = line.lastIndexOf(">");

        while(indexF != lastIndexF && indexL != lastIndexL) {


            String[] tokens = new String[]{"",""};

            for (int i = 0; i < 2; i++) { // <first><second> or <first>
                indexF = line.indexOf("<", indexF + 1);
                newLine += line.substring(indexL + 1, indexF);
                indexL = line.indexOf(">", indexL + 1);

                //check incorrect > ... <
                while (indexL < indexF) {
                   // newLine += line.substring(indexL + 1, indexF);
                    indexL = line.indexOf(">", indexL + 1);
                }

                tokens[i] = line.substring(indexF, indexL + 1);

                // expl error <token>text text text <= text<token>
                while (tokens[i].indexOf("<") != tokens[i].lastIndexOf("<")){
                    String dif = tokens[i].substring(0,tokens[i].lastIndexOf("<"));
                    indexF += dif.length();
                    newLine += dif;
                    tokens[i] = tokens[i].substring(tokens[i].lastIndexOf("<"), tokens[i].length());
                }

               if (line.charAt(indexL + 1) != '<') break;

            }

            Format format = recogniseFormat(tokens);

            if (format == null){
                for (String s : tokens) {
                    newLine += s;
                }
                continue;
            }

            newLine += getCorrectTokens(format, stack);

        }
        if(indexL != line.length()-1) {
            newLine += line.substring(indexL + 1, line.length());
        }
        return newLine + Format.Reset;
    }

    private Format recogniseFormat(String[] tokens){
        Format.Color color = null;
        Format.Style style = null;
        for (String token : tokens) {
            if (Format.Color.isColor(token)) {
                color = Format.Color.valueOfToken(token);
                continue;
            } else if (Format.Style.isStyle(token)) {
                style = Format.Style.valueOfToken(token);
                continue;
            }
        }
        if( color == null && style == null) return null;
        return Format.getFormat(color,style);
    }

    private String getCorrectTokens(Format format, ArrayList<Format> stack){

        String tokens = "";
        Format.Style s = format.style;
        Format.Color c = format.color;

        if(stack.isEmpty()){
            stack.add(format);
            tokens = format.toString();
        }else {
           if(stack.contains(format)){
               stack.remove(format);
               format = Format.getFormat(null, null);
               tokens = format.toString();
           }

           else if(s == null){
               boolean isFoundColor = false;
               for (int i = stack.size() - 1; i >= 0; i--) {
                   if(stack.get(i).color.equals(c)){
                       s = stack.get(i).style;
                       tokens += stack.get(i) + "" +Format.getFormat(null, s);
                       stack.remove(stack.get(i));
                       stack.add(Format.getFormat(null, s));
                       isFoundColor = true;
                       break;
                   }
               }

               if(!isFoundColor){
                   Format f = stack.get(stack.size()-1);
                   stack.remove(f);
                   stack.add(Format.getFormat(c, f.style));
                   tokens  += f + "" + Format.getFormat(c, f.style);
               }
           }
           else{
               Format f = stack.get(stack.size()-1);
               stack.remove(f);
               stack.add(Format.getFormat(c, f.style));
               tokens  += f + "" + Format.getFormat(c, f.style);
           }

        }

        return tokens;
    }

    public String formatList(List list, String selectionWord, int from, int to ) {

        int[] indexes = new int[to-from];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = from + i;
        }
        return formatList(list,selectionWord, indexes);
    }

    public String formatList(List list, Format format, int from, int to ) {

        int[] indexes = new int[to-from];
        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = from + i;
        }
        return formatList(list,format, indexes);
    }

    public String formatList(List list, String selectionToken, int ... formatIndex){

        String[] tokens = null;
        int index = selectionToken.indexOf(">");
        if(index != selectionToken.length()-1){
            tokens = new String[2];
            tokens[0] = selectionToken.substring(0, index+1);
            tokens[1] = selectionToken.substring(index+1, selectionToken.length());
        }else{
            tokens = new String[1];
            tokens[0] = selectionToken;
        }
        Format f = recogniseFormat(tokens);
        return formatList(list, f, formatIndex);
    }

    public String formatList(List list, Format format, int ... formatIndex){
        StringBuilder formatLine = new StringBuilder();

        formatLine.append(Format.Black);
        formatLine.append("[");
        formatLine.append(Format.White);
        for (int i = 0; i < list.size(); i++) {
            if(isContain(formatIndex, i)){
                formatLine.append( formatText(list.get(i).toString(), format )).append(", ");
            }
           else {
                formatLine.append(list.get(i).toString()).append(", ");
            }
        }
        formatLine.delete(formatLine.lastIndexOf(","), formatLine.lastIndexOf(",") +2);
        formatLine.append(Format.Black);
        formatLine.append("]");
        return formatLine.toString();
    }

    public String formatList(List list, Format[] formats, int[][] indexes){
        StringBuilder formatLine = new StringBuilder();

        formatLine.append(Format.Black);
        formatLine.append("[");
        formatLine.append(Format.White);
        for (int i = 0; i < list.size(); i++) {
            boolean isCont = false;
            for (int j = 0; j < indexes.length; j++) {
                if(isContain(indexes[j], i)){
                    formatLine.append( formatText(list.get(i).toString(), formats[j] )).append(", ");
                    isCont = true;
                }
            }
            if(!isCont) {
                formatLine.append(list.get(i).toString()).append(", ");
            }
        }
        formatLine.delete(formatLine.lastIndexOf(","), formatLine.lastIndexOf(",") +2);
        formatLine.append(Format.Black);
        formatLine.append("]");
        return formatLine.toString();
    }

    private boolean isContain(int[] arr, int num){
        for (int i : arr) {
            if(num == i) return true;
        }
        return false;
    }

    public String getTree(int lvl){
        return getTree(lvl, 5,4);
    }
    public String getTree(int lvl, int spaceLength, int arrowLength){
        if(lvl == 0 ) {
            return "->";
        }
        String out = "";
        String jumpWord ="|";
        String arrow = "|";
        for (int i = 0; i < spaceLength; i++) jumpWord += " ";
        for (int i = 0; i < arrowLength; i++) arrow += "-";
        arrow += ">";
        out += " "; // arrow from lvl 0
        for (int i = 0; i < lvl-1; i++) {
            out += jumpWord;
        }
        out += arrow;
        return out;
    }

    public void printTree(int lvl){
        System.out.printf(getTree(lvl));
    }
    public void printTree(int lvl, int spaceLength, int arrowLength){
        System.out.printf(getTree(lvl, spaceLength, arrowLength));
    }

    public void printSpace(int cnt){
        System.out.printf(getSpace(cnt));
    }
    public String getSpace(int cnt){
        String line = "";
        for (int i = 0; i < cnt; i++) {
            line += " ";
        }
        return line;
    }

    public String clearAll(String line){
        for (Format format : Format.values()) {
            line = line.replace(format.toString(), "");
        }
        return line;
    }

}
