package format;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PanelBorder extends JPanel {
	
	public PanelBorder() {
		setOpaque(false);
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //lam min canh
		g2d.setColor(getBackground());
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(),15,15); // vẽ hcn, 0,0 là toạ độ góc trái, bo góc 15 pixel
		super.paintComponent(g);
		
	}
}
