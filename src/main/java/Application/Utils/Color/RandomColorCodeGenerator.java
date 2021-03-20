package Application.Utils.Color;

import java.util.Random;

public class RandomColorCodeGenerator implements ColorCodeGenerator{
    @Override
    public String getColor() {
        StringBuilder color = new StringBuilder("#");
        String[] letters = "0123456789abcdef".split("");
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            color.append(letters[random.nextInt(letters.length)]);
        }
        return color.toString();
    }
}
