package format;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JPanel implements TableCellRenderer {
    private JButton increaseButton;
    private JButton decreaseButton;

    public ButtonRenderer() {
        setOpaque(true);
        setLayout(new BorderLayout());

        increaseButton = new JButton("+");
        decreaseButton = new JButton("-");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}