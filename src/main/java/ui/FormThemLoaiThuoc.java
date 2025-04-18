package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import dao.DonViTinhDao;
import dao.LoaiThuocDao;
import entity.DonViTinh;
import entity.LoaiThuoc;

public class FormThemLoaiThuoc extends JFrame implements ActionListener,MouseListener {
	private LoaiThuocDao loaithuocDao;
	private JTextField txtMa;
	private JTextField txtTen;
	private JButton btnThem;
	private DefaultTableModel tblModel;
	private JTable tblLoai;
	private FormNhapThuoc frmNhapthuoc;
	public FormThemLoaiThuoc(FormNhapThuoc frmNhapthuoc) throws SQLException {
		
		this.frmNhapthuoc = frmNhapthuoc;
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với FormThuoc");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		loaithuocDao = new LoaiThuocDao();
		
		setSize(500,400);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout());
		
		Box b,b1,b2,b3;
        b = Box.createVerticalBox();

		add(b,BorderLayout.NORTH);
        b.add(b1 = Box.createHorizontalBox());
        b.add(b2 = Box.createHorizontalBox());
        b.add(b3 = Box.createHorizontalBox());
        
		JLabel lblMa = new JLabel("Mã loại: ");
        JLabel lblTen = new JLabel("Tên loại thuốc: ");
        txtMa = new JTextField();
        txtTen = new JTextField();
        b1.add(lblMa); b1.add(txtMa); txtMa.setEditable(false);
        b2.add(lblTen); b2.add(txtTen);
        
        btnThem = new JButton("Thêm");
        b3.add(btnThem); 
        
        String[] header = {"Mã loại","Tên loại thuốc"};
        tblModel = new DefaultTableModel(header,0);
        tblLoai = new JTable(tblModel);
        JScrollPane scrDonvi = new JScrollPane(tblLoai);
        add(scrDonvi, BorderLayout.CENTER);
	
        btnThem.addActionListener(this);
        tblLoai.addMouseListener(this);
        
        xoaDataTable();
        nhapDuLieuSQLLenTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o =e.getSource();
		if(o.equals(btnThem)) {
			String ten = txtTen.getText().trim();
			LoaiThuoc l = new LoaiThuoc(ten);
			
			for(int i=0; i<tblLoai.getRowCount();i++) {
				if(ten.equalsIgnoreCase(tblLoai.getValueAt(i, 1).toString())) {
					JOptionPane.showMessageDialog(this, "bạn đã nhập loại thuốc này rồi!");
					txtTen.requestFocus();
					txtTen.selectAll();
					return;
				}
			}
			
			try {
				loaithuocDao.taoLoaiThuoc(l);
				xoaDataTable();
				nhapDuLieuSQLLenTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Thêm loại thuốc Mới Thành Công!");
			frmNhapthuoc.getCboLoaiThuoc().addItem(l.getLoaiThuoc());
		}
		
	}
	public void xoaDataTable() {
		DefaultTableModel modelTbl = (DefaultTableModel) tblLoai.getModel();
		modelTbl.getDataVector().removeAllElements();
	}
	public void nhapDuLieuSQLLenTable() throws SQLException {
		List<LoaiThuoc> lstLoai = loaithuocDao.timTatCaLoaiThuoc();
		for(LoaiThuoc d : lstLoai) {
			tblModel.addRow(new Object[] {
				d.getId(), d.getLoaiThuoc()
			});
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int r = tblLoai.getSelectedRow();
		txtMa.setText(tblLoai.getValueAt(r, 0).toString());
		txtTen.setText(tblLoai.getValueAt(r, 1).toString());
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
