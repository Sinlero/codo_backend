package Application.Utils.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HsvColorGenerator implements ColorCodeGenerator{
    @Override
    public String getColor() {
        StringBuilder finalColor = new StringBuilder("#");
        String firstOct = "ff";
        String secondOct = "00";
        StringBuilder thirdOct = new StringBuilder();
        String[] letters = "0123456789abcdef".split("");
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            thirdOct.append(letters[random.nextInt(letters.length)]);
        }
        List<String> list = new ArrayList<>();
        list.add(firstOct);
        list.add(secondOct);
        list.add(thirdOct.toString());
        for (int i = 0; i < 3; i++) {
            int numberOct = random.nextInt(list.size());
            finalColor.append(list.get(numberOct));
            list.remove(numberOct);
        }
        return finalColor.toString();
    }
}
