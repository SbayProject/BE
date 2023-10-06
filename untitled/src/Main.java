import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
//        String message = "theredfoxjumpedoverthelazydog";
//        String secretKey = "quickfoxlazy";
//        boolean isAuthentic = verifyMessageAuthenticity(message, secretKey);
//        System.out.println(isAuthentic);
        List<Integer> a =new ArrayList<>();
        a.add(10);
        a.add(1);
        a.add(2);
        a.add(3);

        List<Integer> b =new ArrayList<>();
        b.add(2);
        b.add(4);
        b.add(7);
        b.add(8);
        System.out.println(longestDominoChain(4,a,b));
    }

    public static int longestDominoChain(int n, List<Integer> heights, List<Integer> position) {
        // Write your code here
        int count =1;
        for (int i = 0; i < heights.size() -1; i++) {
            int count1=1;
            for (int j = i+1; j < position.size(); j++) {
                List<Integer> list =new ArrayList<>();
                int k = position.get(j)- position.get(i);
                if (heights.get(j)>k){
                    count1++;
                    list.add(j);
                }else {
                    break;
                }
            }
            if (count1>count){
                count=count1;
            }
        }int count2 =1;

        for (int i = 0; i < heights.size() -1; i++) {
            int count1=1;
            for (int j = i+1; j < position.size(); j++) {
                List<Integer> list =new ArrayList<>();
                int k = position.get(j)- position.get(i);
                if (heights.get(i)>k){
                    count1++;
                    list.add(j);
                }else {
                    break;
                }
            }
            if (count1>count2){
                count2=count1;
            }
        }
        if (count2>count){
            return count2;
        }
        return count;
    }
    public static int longestDominoChain(int length, int[] height, int[] position) {
        int maxChainLength = 1; // Minimum chain length is 1 (knocking over the initial domino)

        for (int i = 0; i < length - 1; i++) {
            int currChainLength = 1; // Current chain length starting from domino at index i

            for (int j = i + 1; j < length; j++) {
                int distance = position[j] - position[i]; // Distance between dominoes i and j

                if (height[j] > distance) {
                    currChainLength++;
                } else {
                    break; // Domino j cannot be knocked over, so stop checking further
                }
            }

            if (currChainLength > maxChainLength) {
                maxChainLength = currChainLength;
            }
        }

        return maxChainLength;
    }


//public static boolean verifyMessageAuthenticity(String message, String secret_key) {
//    int i = 0;
//    for (char c : message.toCharArray()) {
//        if (c == secret_key.charAt(i)) {
//            i++;
//            if (i == secret_key.length()) {
//                return true;
//            }
//        }
//    }
//    return false;
//}
}