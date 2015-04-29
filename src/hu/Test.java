package hu;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Test extends JFrame implements ActionListener {
	JFrame frame;
	JButton button;
	JPanel panel;
	JTable4Data table;
	JTextField textField;

	JDialog d;

	public Test() {
		frame = new JFrame("it is a test");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		panel = new JPanel();
		panel.setLayout(new GridLayout());

		textField = new JTextField();
		panel.add(textField);

		button = new JButton("see it");
		panel.add(button);

		frame.add(panel);

		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				frame.dispose();

				String str = textField.getText();

				table = new JTable4Data();
				table.setVisible(true); 
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		new Test();
	}

}