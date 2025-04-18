package format;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    private JPanel panel;
    private JButton btnGiam,btnTang;
    private JTextField textField;

    public CustomCellRenderer() {
        panel = new JPanel();
//        panel.setSize(WIDTH,20);
        btnGiam = new JButton("-");
        textField = new JTextField(2);
        textField.setText("1");
        btnTang=new JButton("+");
        
        btnGiam.setPreferredSize(new Dimension(20,20)); // Thay đổi kích thước tùy ý
        textField.setPreferredSize(new Dimension(20, 20)); // Thay đổi kích thước tùy ý
        btnTang.setPreferredSize(new Dimension(20, 20)); // Thay đổi kích thước tùy ý
        
        panel.add(btnGiam);
        panel.add(textField);
        panel.add(btnTang);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return panel;
    }

	public JPanel getPanel() {
		return panel;
	}

	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

	public JButton getBtnGiam() {
		return btnGiam;
	}

	public void setBtnGiam(JButton btnGiam) {
		this.btnGiam = btnGiam;
	}

	public JButton getBtnTang() {
		return btnTang;
	}

	public void setBtnTang(JButton btnTang) {
		this.btnTang = btnTang;
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public boolean btnGiamSelected() {
		return btnGiam.isSelected();
	}
    
	public boolean btnTangSelected() {
		return btnTang.isSelected();
	}
}