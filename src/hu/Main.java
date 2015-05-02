package hu;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main {
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

		// testField = new JTextField();
		// testField.setText("sth");
		// panel.add(testField);
		button = new JButton("≤È—Ø");
		panel.add(button);

		frame.add(panel);

		button.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				// frame.setVisible(false);
				// frame.dispose();

				// new ThreadPool();
				DbHelper db = new DbHelper();
				List<List<Object>> dataFromDBList = null;
				try {
					dataFromDBList = db.getDataFromDB();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				JTable4Data frame = new JTable4Data(dataFromDBList);
				frame.setVisible(true);
			}
		});
	}
}