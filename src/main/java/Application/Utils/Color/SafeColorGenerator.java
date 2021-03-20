package Application.Utils.Color;

import java.util.Random;

public class SafeColorGenerator implements  ColorCodeGenerator{
    @Override
    public String getColor() {
        StringBuilder finalColor = new StringBuilder("#");
        String[] colors = {"00", "33", "66","99", "cc", "ff"};
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            finalColor.append(colors[random.nextInt(colors.length)]);
        }
        if (finalColor.substring(0, finalColor.length()).compareTo("#ffffff") == 0) {
            finalColor = new StringBuilder(getColor());
        }
        return finalColor.toString();
    }
}
