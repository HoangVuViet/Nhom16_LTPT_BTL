package format;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import format.Model_Menu.MenuType;

public class PanelMenuQLy extends JPanel{

	private int x; //lay vi tri cua JFrame
	private int y;
	
	private ListMenu lstMenu;
	private JPanel pnlSubMenu1;
	private JPanel pnlCN;

	private EventedMenuSelected event;
	
	public void addEventMenuSelected(EventedMenuSelected event) {
		this.event = event;
		lstMenu.addEventMenuSelected(event);
		
	}

	public PanelMenuQLy() {
		
		setOpaque(false);
		
		
		 //Panel Con của menu
        pnlSubMenu1 = new JPanel();
        pnlSubMenu1.setOpaque(false);
        this.add(pnlSubMenu1, BorderLayout.NORTH);
        JLabel lblApp = new JLabel("HMedicine");
        lblApp.setFont(new Font("sansserif", Font.BOLD, 18));
        lblApp.setForeground(Color.decode("#ffffff"));
        lblApp.setIcon(new ImageIconCustom("/images/drugstore.png",70,70));
        pnlSubMenu1.add(lblApp);
        
        lstMenu = new ListMenu<>();
		add(lstMenu, BorderLayout.CENTER);
		lstMenu.addItem(new Model_Menu("info","Giới thiệu", MenuType.MENU));
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));
		lstMenu.addItem(new Model_Menu("thuoc","Thuốc", MenuType.MENU));
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));
		lstMenu.addItem(new Model_Menu("hoaDon2", "Hoá Đơn", MenuType.MENU));
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));
		lstMenu.addItem(new Model_Menu("identity","Nhân Viên", MenuType.MENU));
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));
		lstMenu.addItem(new Model_Menu("khachhang","Khách Hàng", MenuType.MENU));
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));

		lstMenu.setOpaque(false);
		
		
		lstMenu.addItem(new Model_Menu("NCC","Nhà cung cấp", MenuType.MENU));
		
		//menu bao cao
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));
		lstMenu.addItem(new Model_Menu("analysis","Thống Kê", MenuType.MENU));
		lstMenu.addItem(new Model_Menu("","---------------------------------------------", MenuType.TITLE));
	
}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); //làm mịn cạnh tránh răng cưa
		GradientPaint gra = new GradientPaint(0,0, Color.decode("#1e3c72"),0,getHeight(), Color.decode("#2a5298"));
		g2d.setPaint(gra);
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(),15,15); //bo góc, kích thích = kích thước của component hiện tại
		g2d.fillRect(getWidth()-20, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}
    
    public void movingFrame(JFrame f) {
		pnlSubMenu1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
				
			}
		});
		pnlSubMenu1.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				f.setLocation(e.getXOnScreen()-x, e.getYOnScreen()-y); //lấy toạ độ X, Y trên màn hình trừ đi x,y trước đó để đặt vị trí mới của Frame
			}
		});
	}
}
