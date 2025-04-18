package format;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.event.*;

public class PrintPreviewDialog extends JDialog {
    private JEditorPane editorPane;
    private PrinterJob printerJob;

    public PrintPreviewDialog(JEditorPane editorPane, PrinterJob printerJob) {
        this.editorPane = editorPane;
        this.printerJob = printerJob;

        initComponents();
    }

    private void initComponents() {
        setTitle("Xem Trước In");
        setModal(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        editorPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(editorPane);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        JButton printButton = new JButton("In");
        printButton.addActionListener(new ActionListener() {
			
        	@Override
            public void actionPerformed(ActionEvent e) {
                try {
                    printerJob.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
		});
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }
}