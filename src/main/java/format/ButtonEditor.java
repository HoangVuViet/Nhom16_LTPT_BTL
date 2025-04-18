package format;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ButtonEditor extends DefaultCellEditor {
    private JPanel panel;
    private JButton increaseButton;
    private JButton decreaseButton;
    private JTable table;
    private int column;

    public ButtonEditor(JTextField textField) {
        super(textField);

        increaseButton = new JButton("+");
        decreaseButton = new JButton("-");

        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(decreaseButton);
        panel.add(increaseButton);
        

        increaseButton.addActionListener(e -> fireEditingStopped());
        decreaseButton.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        this.table = table;
        this.column = column;
        return panel;
    }

    public Object getCellEditorValue() {
        return null;
    }

    public boolean stopCellEditing() {
        return super.stopCellEditing();
    }

    protected void fireEditingStopped() {
        super.fireEditingStopped();

        int row = table.getSelectedRow();
        Object quantity = table.getValueAt(row, 3);
        int currentQuantity = Integer.parseInt(quantity.toString());

        if (increaseButton.hasFocus()) {
            // Tăng giá trị số lượng
            int newQuantity = currentQuantity + 1;
            table.setValueAt(newQuantity, row, column);
        } else if (decreaseButton.hasFocus()) {
            // Giảm giá trị số lượng
            if (currentQuantity > 0) {
                int newQuantity = currentQuantity - 1;
                table.setValueAt(newQuantity, row, column);
            }
        }
    }
}