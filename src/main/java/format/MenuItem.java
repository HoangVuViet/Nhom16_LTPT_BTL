package format;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuItem extends JPanel{
	
	private JLabel lblIcon;
	private JLabel lblName;
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}

	private boolean selected;

	public MenuItem(Model_Menu data) {
		setLayout(new FlowLayout(FlowLayout.LEADING, 20, 0));
		
		lblIcon = new JLabel("");
		lblName = new JLabel("Menu Name");
		lblIcon.setForeground(new Color(255, 255, 255));
		lblName.setForeground(new Color(255, 255, 255));
		
		add(lblIcon);
		add(lblName);
		
		setOpaque(false);
		if(data.getType() == Model_Menu.MenuType.MENU) {
			lblIcon.setIcon(data.toIcon());
			lblName.setText(data.getName());
		}
		else if(data.getType() == Model_Menu.MenuType.TITLE) {
			lblIcon.setText(data.getName());
			lblIcon.setFont(new Font("sansserif", 1, 12));
			lblName.setVisible(false);
		}
		else
		{
			lblName.setText("");
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		if(selected) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //làm mịn cạnh tránh răng cưa
			g2d.setColor(new Color(255,255,255,80));
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(),5,5); //bo góc, kích thích = kích thước của component hiện tại
			
		}
		super.paintComponent(g);
	}
}
