package format;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

public class ButtonLogin extends JButton implements MouseListener {
    private JLabel lblName;
    public ButtonLogin(String nameButton) {

    	setBorder(new EmptyBorder(15, 125, 15, 125));
        setContentAreaFilled(false);
        
        lblName = new JLabel(nameButton);
        lblName.setForeground(Color.white);
        lblName.setFont(new Font(getFont().getName(), Font.PLAIN, 20));
        
        addMouseListener(this);
        add(lblName);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, Color.decode("#111111"), w, h, Color.decode("#111111"));
        g2d.setPaint(gp);
        g2d.fillRoundRect(0, 0, w, h, 40, 40);
    }

	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		lblName.setBorder(new MatteBorder(0, 0, 1, 0, Color.decode("#34e065")));
		lblName.setForeground(Color.decode("#34e065"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		lblName.setBorder(null);
		lblName.setForeground(Color.white);
	}
}