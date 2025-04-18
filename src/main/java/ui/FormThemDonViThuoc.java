package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connect.ConnectDB;
import dao.DonViTinhDao;
import entity.DonViTinh;
import entity.LoaiThuoc;
import entity.Thuoc;

public class FormThemDonViThuoc extends JFrame implements ActionListener, MouseListener{
	private DonViTinhDao donviDao;
	private JTextField txtMa;
	private JTextField txtTen;
	private JButton btnThem;
	private DefaultTableModel tblModel;
	private JTable tblDonvi;
	private FormNhapThuoc frmNhapthuoc;
	

	public FormThemDonViThuoc(FormNhapThuoc frmNhapThuoc) throws SQLException {
		this.frmNhapthuoc = frmNhapThuoc;
		
		try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Kết nối thành công SQL với FormThuoc");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		setSize(500,400);
		setLocationRelativeTo(null);
		donviDao = new DonViTinhDao();
		
		setLayout(new BorderLayout());
		
		Box b,b1,b2,b3;
        b = Box.createVerticalBox();

		add(b,BorderLayout.NORTH);
        b.add(b1 = Box.createHorizontalBox());
        b.add(b2 = Box.createHorizontalBox());
        b.add(b3 = Box.createHorizontalBox());
        
		JLabel lblMa = new JLabel("Mã đơn vị: ");
        JLabel lblTen = new JLabel("Tên đơn vi: ");
        txtMa = new JTextField();
        txtTen = new JTextField();
        b1.add(lblMa); b1.add(txtMa); txtMa.setEditable(false);
        b2.add(lblTen); b2.add(txtTen);
        
        btnThem = new JButton("Thêm");
        b3.add(btnThem);
        
        String[] header = {"Mã đơn vị","Tên đơn vị"};
        tblModel = new DefaultTableModel(header,0);
        tblDonvi = new JTable(tblModel);
        JScrollPane scrDonvi = new JScrollPane(tblDonvi);
        add(scrDonvi, BorderLayout.CENTER);
	
        btnThem.addActionListener(this);
        tblDonvi.addMouseListener(this);
        
        xoaDataTable();
        nhapDuLieuSQLLenTable();
	}
	public void xoaDataTable() {
		DefaultTableModel modelTbl = (DefaultTableModel) tblDonvi.getModel();
		modelTbl.getDataVector().removeAllElements();
	}
	public void nhapDuLieuSQLLenTable() throws SQLException {
		List<DonViTinh> lstDonvi = donviDao.timTatCaDonViThuoc();
		for(DonViTinh d : lstDonvi) {
			tblModel.addRow(new Object[] {
				d.getMaDonVi(), d.getTenDonVi()
			});
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o =e.getSource();
		if(o.equals(btnThem)) {
			String ten = txtTen.getText().trim();
			DonViTinh donvi = new DonViTinh(ten);
			
			for(int i=0; i<tblDonvi.getRowCount();i++) {
				if(ten.equalsIgnoreCase(tblDonvi.getValueAt(i, 1).toString())) {
					JOptionPane.showMessageDialog(this, "bạn đã nhập đơn vị này rồi!");
					txtTen.requestFocus();
					txtTen.selectAll();
					return;
				}
			}
			try {
				donviDao.taoDonvi(donvi);
				xoaDataTable();
				nhapDuLieuSQLLenTable();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Thêm Đơn vị Mới Thành Công!");
			frmNhapthuoc.getCboDonvi().addItem(donvi.getTenDonVi());
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int r = tblDonvi.getSelectedRow();
		txtMa.setText(tblDonvi.getValueAt(r, 0).toString());
		txtTen.setText(tblDonvi.getValueAt(r, 1).toString());
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
