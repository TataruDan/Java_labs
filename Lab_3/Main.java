import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class RecordStack {
    ArrayList<Record> stack = new ArrayList<Record>();

    public boolean isEmpty() {
        return stack.isEmpty();
    }
    public void push(Record record) {
        stack.add(record);
    }
    public Record pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.remove(stack.size() - 1);
    }

    public Record peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack.get(stack.size() - 1);
    }

    public static class Record {
        private int field1;
        private String field2;

        public Record(int field1, String field2) throws InvalidInputException {
            if (!field2.matches("\\d+")) {
                throw new InvalidInputException();
            }
            this.field1 = field1;
            this.field2 = field2;
        }

        public int getField1() {
            return field1;
        }

        public void setField1(int field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) throws InvalidInputException {
            if (!field2.matches("\\d+")) {
                throw new InvalidInputException();
            }
            this.field2 = field2;
        }
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException() {
            super("Inputul introdus nu este valid. Introduceți numere întregi.");
        }
    }
}
class Stiva extends JFrame implements ActionListener {
    private RecordStack stack;
    private DefaultListModel<String> listModel;
    private JList<String> recordList;
    private JTextField field1Text;
    private JTextField field2Text;

    public Stiva() {
        super("Laboratorul nr 5");

        // Initialize the stack
        stack = new RecordStack();

        // Create the list model and JList
        listModel = new DefaultListModel<String>();
        recordList = new JList<String>(listModel);

        // Creaza câmpurile de text pentru adăugarea înregistrărilor
        field1Text = new JTextField(5);
        field2Text = new JTextField(5);

        // Creaza butoanele pentru adăugarea și eliminarea înregistrărilor
        JButton addButton = new JButton("Adaugă");
        JButton removeButton = new JButton("Șterge");

        // Adaugă ascultători de acțiune la butoane
        addButton.addActionListener(this);
        removeButton.addActionListener(this);

        // Aspectul interfeței
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adauga componentele
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Câmpul 1: "));
        inputPanel.add(field1Text);
        inputPanel.add(new JLabel("Câmpul 2: "));
        inputPanel.add(field2Text);
        inputPanel.add(addButton);
        inputPanel.add(removeButton);

        contentPane.add(inputPanel, BorderLayout.NORTH);
        contentPane.add(new JScrollPane(recordList), BorderLayout.CENTER);

        // Adaugarea contentului
        setContentPane(contentPane);

        // Adaugarea ferestrei
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();

        // Vizualizarea ferestrei
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Adaugă")) {
            // Adăugarea o înregistrare în stivă și listă
            try {
                RecordStack.Record record = new RecordStack.Record(
                        Integer.parseInt(field1Text.getText()),
                        field2Text.getText());
                stack.push(record);
                listModel.addElement(record.getField1() + ", " + record.getField2());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input invalid. Introduceti o valoare de tip intreg.");
            } catch (RecordStack.InvalidInputException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else if (e.getActionCommand().equals("Șterge")) {
            // Elimina înregistrarea de sus din stivă și listă
            try {
                RecordStack.Record record = stack.pop();
                listModel.remove(listModel.getSize() - 1);
            } catch (EmptyStackException ex) {
                JOptionPane.showMessageDialog(this, "Stiva este goală.");
            }
        }
    }

    public static void main(String[] args) {
        new Stiva();}}
