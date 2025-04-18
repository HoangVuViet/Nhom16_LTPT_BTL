package format;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import javax.swing.text.Style;

public class PanelTextField extends JPanel {
	
	private JLabel lblUser;
    private JTextField txtUser;
	private JLabel lblIcon;
    
	public PanelTextField(String title, String url ) {
		setOpaque(false);
		setLayout(new BorderLayout());
		setBorder(new MatteBorder(0,0,1,0, Color.BLACK));
		
		lblUser = new JLabel(title);
		lblUser.setFont(new Font(lblUser.getFont().getName(),Font.ITALIC , 20));
		add(lblUser, BorderLayout.NORTH);
		
		txtUser = new JTextField();
		txtUser.setBackground(null);
		txtUser.setOpaque(false);
		txtUser.setBorder(null);
		txtUser.setFont(new Font(lblUser.getFont().getName(),Font.PLAIN , 18));
		add(txtUser, BorderLayout.CENTER);
	
		lblIcon = new JLabel();
		lblIcon.setIcon(new ImageIconCustom(url, 30, 30));
		add(lblIcon, BorderLayout.EAST);
	}
	public JTextField getTxtUser() {
		return txtUser;
	}
	public void setText(String text) {
		txtUser.setText(text);
	}
}
