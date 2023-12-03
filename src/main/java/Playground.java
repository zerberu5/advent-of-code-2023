import java.util.ArrayList;
import java.util.List;

public class Playground {

    public static void main(String[] args) {
        String str = "....48302...395..436\n" +
                "728.295..234.29.5731";

        String[] split = str.split("\n");
        List<IntContainer> intContainers = new ArrayList<>();


        for(int h = 0; h < split.length; h++){
            char[] charArray = split[h].toCharArray();
            for (int i = 0; i < charArray.length; i++) {
                if (Character.isDigit(charArray[i])) {
                    IntContainer intContainer = new IntContainer();
                    List<Integer> indices = new ArrayList<>();
                    intContainer.row = h;
                    String valueStr = "";
                    while (i < charArray.length && Character.isDigit(charArray[i])) {
                        valueStr += charArray[i];
                        indices.add(i);
                        i++;
                    }
                    intContainer.value = Integer.parseInt(valueStr);
                    intContainer.indices = indices;
                    intContainers.add(intContainer);
                }
            }
        }
        intContainers.forEach(cont -> System.out.println(cont.value));
    }

    static class IntContainer {
        int row;
        List<Integer> indices;
        int value;
    }

}
