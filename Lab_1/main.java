import java.util.Scanner;

public class Testare {
    public static void main(String [] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduceti textul: ");
        String text = scan.nextLine();
        System.out.println("Introduceti dimensiunea textului pentru a fi schimbat: ");
        int length = scan.nextInt();
        System.out.println("Introduceti diapazonul subsirului : ");
        int firstIndex = scan.nextInt();
        int secondIndex = scan.nextInt();
        textUtils test = new textUtils(text,length,firstIndex,secondIndex);
        test.showReplacedText();

    }
}

class textUtils {
    private String text;
    private int length;
    private int firstIndex;
    private int secondIndex;

    public textUtils(String text, int length, int firstIndex, int secondIndex) {
        this.text = text;
        this.length = length;
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    public void showText() {
        System.out.println(text);
    }

    public int getLength() {
        return text.length();
    }

    private String getSubstring() {
        String replacement = new String(this.text.substring(this.firstIndex,this.secondIndex));
        return replacement;
    }
    private String changeWords(){
        String[] words = this.text.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() == length) {
                words[i] = getSubstring();
            }
        }
        String result = String.join(" ", words);
        return result;
    }

    public void showReplacedText() {
        System.out.println(changeWords());
    }
}
