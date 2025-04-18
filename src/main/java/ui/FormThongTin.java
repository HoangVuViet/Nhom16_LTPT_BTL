package ui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import format.ImageIconCustom;
import format.PanelBorder;

public class FormThongTin extends PanelBorder{
	
	public FormThongTin() {
		setLayout(new BorderLayout());
		JPanel pnlTop = new JPanel();
		add(pnlTop, BorderLayout.NORTH);
		JLabel lblTitle = new JLabel("PHẦN MỀM QUẢN LÝ HIỆU THUỐC HMEDICINE");
		lblTitle.setFont(new Font(lblTitle.getName(), Font.BOLD, 40)); 
		pnlTop.add(lblTitle);
		
		JPanel pnlCen = new JPanel();
		ImageIcon icon = new ImageIconCustom("/images/nhaThuoc.jpg", 1000, 490);
		
        // Tạo một JLabel để hiển thị hình ảnh
        JLabel lblImage = new JLabel(icon);
        pnlCen.add(lblImage);
		add(pnlCen, BorderLayout.CENTER);
		
		
	}
}
