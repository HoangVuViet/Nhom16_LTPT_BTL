package format;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.Timer;

public class AnimationButton extends JButton implements ActionListener{
	
	private Color color1 =  Color.decode("#111111");
	private Color color2 =  Color.decode("#111111");
	
	private AnimationButtonInside c;
	
	private String status = "close";
	
	private int xlocate = 0;
	
	private Timer t1;
	
	public Color getColor1() {
		return color1;
	}

	public void setColor1(Color color1) {
		this.color1 = color1;
	}

	public Color getColor2() {
		return color2;
	}

	public void setColor2(Color color2) {
		this.color2 = color2;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AnimationButton() {
		setBackground(null);
		setBorder(null);
		setOpaque(false); //xoá nền mặc định của Button
		setLayout(null);
		setContentAreaFilled(false); //làm nền của Button trong suốt 
		
		//add Panel Childrendoc vào button
		c = new AnimationButtonInside();
		c.setBounds(xlocate,0,40,40);
		add(c);
		
		//sự kiện
		addActionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int w = getWidth(), h = getHeight();
		GradientPaint gp =  new GradientPaint(0, 0, color1, w , h, color2);
		g2d.setPaint(gp);
		g2d.fillRoundRect(0, 0, w, h, 40, 40);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(this)) {
			if(status.equals("close")) {
				status = "open";
				setColor1(Color.decode("#FDC830"));
				setColor2(Color.decode("#F37335"));
				t1 = new Timer(1, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(xlocate < 40) {
							xlocate+=2;
							c.setBounds(xlocate,0,40,40);
							invalidate();
							validate();
							repaint();
						}
						else {
							xlocate = 40;
							t1.stop();
						}
					}
				});
				t1.start();
			}
			else {
				status = "close";
				setColor1(Color.decode("#000000"));
				setColor2(Color.decode("#000000"));
				t1 = new Timer(1, new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(xlocate > 0) {
							xlocate-=2;
							c.setBounds(xlocate,0,40,40);
							invalidate();
							validate();
							repaint();
						}
						else {
							xlocate = 0;
							t1.stop();
						}				
					}
				});
				t1.start();
			}
		}
		
	}
}
