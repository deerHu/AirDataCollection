package hu;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main implements ActionListener {
	JFrame frame;
	JButton button;
	JPanel panel;
	JTable4Data table;
	JTextField testField;

	public Main() {
		frame = new JFrame("test");
		frame.setSize(500, 500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));

		testField = new JTextField();
		testField.setText("sth");
		panel.add(testField);

		button = new JButton("see it");
		panel.add(button);

		frame.add(panel);

		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// frame.setVisible(false);
				frame.dispose();

				new Test();
				// table = new JTable4Data();
				// table.setVisible(true); // 必须，否则不可见
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new Main();
	}
}