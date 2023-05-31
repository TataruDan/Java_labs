// interfață care definește metodele necesare pentru a realiza operații cu fracții, cum ar fi afișarea fracției, adunarea și scăderea fracțiilor.
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface FractionOperations {
    public String display();
    public FractionOperations add(FractionOperations f);
    public FractionOperations subtract(FractionOperations f);
}

class Fraction implements FractionOperations {
    private int numarator;
    private int numitor;

    Fraction() {
    }

    Fraction(int numarator, int numitor) {
        this.numarator = numarator;
        this.numitor = numitor;
    }

    public String display() {
        return String.format("%d/%d", numarator, numitor);
    }

    public FractionOperations add(FractionOperations f) {
        Fraction fraction = (Fraction) f;
        int lcm = findLCM(numitor, fraction.numitor);
        int sumNumerator = (lcm / numitor * numarator) + (lcm / fraction.numitor * fraction.numarator);
        return new Fraction(sumNumerator, lcm);
    }

    public FractionOperations subtract(FractionOperations f) {
        Fraction fraction = (Fraction) f;
        int lcm = findLCM(numitor, fraction.numitor);
        int diffNumerator = (lcm / numitor * numarator) - (lcm / fraction.numitor * fraction.numarator);
        return new Fraction(diffNumerator, lcm);
    }

    private int findLCM(int a, int b) {
        return a * (b / findGCD(a, b));
    }

    private int findGCD(int a, int b) {
        if (b == 0) {
            return a;
        }
        return findGCD(b, a % b);
    }
}
//Utilizarea acestei clase este de a stoca un set de numere fracționare (obiecte Fraction) într-un vector și de a efectua operații aritmetice asupra acestora.
class FractionArray implements FractionOperations {
    private Fraction[] fractions;

    FractionArray(Fraction[] fractions) {
        this.fractions = fractions;
    }

    // returnează valoarea sub forma de string
    public String display() {
        String result = "";
        for (Fraction fraction : fractions) {
            result += fraction.display() + " ";
        }
        return result;
    }

    // Rescrierea funcției add
    public FractionOperations add(FractionOperations f) {
        FractionArray fractionArray = (FractionArray) f;
        int size = Math.min(fractions.length, fractionArray.fractions.length);
        Fraction[] sum = new Fraction[size];
        for (int i = 0; i < size; i++) {
            sum[i] = (Fraction) fractions[i].add(fractionArray.fractions[i]);
        }
        return new FractionArray(sum);
    }

    // Rescrierea functiei substract
    public FractionOperations subtract(FractionOperations f) {
        FractionArray fractionArray = (FractionArray) f;
        int size = Math.min(fractions.length, fractionArray.fractions.length);
        Fraction[] diff = new Fraction[size];
        for (int i = 0; i < size; i++) {
            diff[i] = (Fraction) fractions[i].subtract(fractionArray.fractions[i]);
        }
        return new FractionArray(diff);
    }
}

class FractionGUI extends JFrame implements ActionListener {
    private JLabel fractionLabel1, fractionLabel2, resultLabel;
    private JTextField numeratorField1, numeratorField2, denominatorField1, denominatorField2;
    private JButton addButton, subtractButton;

    public FractionGUI() {

        setTitle("Calculator de fractii");

        // Denumirile la fractii
        fractionLabel1 = new JLabel("Fraction 1:");
        fractionLabel2 = new JLabel("Fraction 2:");

        // Numarator si numitor
        numeratorField1 = new JTextField(5);
        numeratorField2 = new JTextField(5);
        denominatorField1 = new JTextField(5);
        denominatorField2 = new JTextField(5);

        // Crearea butoanelor de adunare si scadere
        addButton = new JButton("Add");
        subtractButton = new JButton("Subtract");

        // Adaugarea butoanelor necesare
        addButton.addActionListener(this);
        subtractButton.addActionListener(this);

        // Creaza locul de rezultat
        resultLabel = new JLabel("");

        // Setarea de JFrame
        setLayout(new GridLayout(6, 3, 10, 10));

        // Adauga componentele necesare
        add(fractionLabel1);
        add(numeratorField1);
        add(new JLabel("/"));
        add(denominatorField1);
        add(fractionLabel2);
        add(numeratorField2);
        add(new JLabel("/"));
        add(denominatorField2);
        add(addButton);
        add(subtractButton);
        add(resultLabel);


        setSize(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        // Obțineți valorile numărătorului și numitorului din câmpurile de text
        int numerator1 = Integer.parseInt(numeratorField1.getText());
        int denominator1 = Integer.parseInt(denominatorField1.getText());
        int numerator2 = Integer.parseInt(numeratorField2.getText());
        int denominator2 = Integer.parseInt(denominatorField2.getText());

        // Create the fractions
        Fraction fraction1 = new Fraction(numerator1, denominator1);
        Fraction fraction2 = new Fraction(numerator2, denominator2);

        // Realizeaza calculele necesare
        FractionOperations result;
        if (e.getSource() == addButton) {
            result = fraction1.add(fraction2);
        } else {
            result = fraction1.subtract(fraction2);
        }

        // Setează textul etichetei rezultat la rezultatul calculului
        resultLabel.setText(result.display());
    }
}

public class FractionProgram {
    public static void main(String[] args) {
        // Interfața codului
        new FractionGUI();
    }
}
