package hu;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class JTable4Data extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Provide a fixed column in a table.
	 */
	Util util;

	public JTable4Data(List<List<Object>> tableValueGList) {
		super();
		showData(tableValueGList);
	}

	public void showData(List<List<Object>> tableValueGList) {
		setTitle("Data List");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<String> columnNameList = new ArrayList<>(); // ��������ʾ�б����֣���
		columnNameList.add("���");
		for (int i = 1; i < 22; i++) { // ��1��ʼ���������ڣ���2��
			columnNameList.add(Util.columnStr[i - 1]);
		}

		JTableUtil panel = new JTableUtil(columnNameList, tableValueGList, 1);
		getContentPane().add(panel, BorderLayout.CENTER);
	}

}

class JTableUtil extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> columnNameV; // declare the table column name vector
	private List<List<Object>> tableValueV;
	private int fixedColumn = 1; // the fixed column number
	private JTable fixedColumnTable;
	private FixedColumnTableModel fixedColumnTableModel;
	private JTable floatingColumnTable;
	private FloatingColumnTableModel floatingColumnTableModel;

	private class FixedColumnTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public int getRowCount() {
			return tableValueV.size();
		}

		@Override
		public int getColumnCount() {
			return fixedColumn;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return tableValueV.get(rowIndex).get(columnIndex);
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNameV.get(columnIndex);
		}
	}

	private class FloatingColumnTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return true;
		}

		@Override
		public int getRowCount() {
			return tableValueV.size();
		}

		@Override
		public int getColumnCount() {
			return columnNameV.size() - fixedColumn;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return tableValueV.get(rowIndex).get(columnIndex + fixedColumn);
		}

		@Override
		public String getColumnName(int columnIndex) {
			return columnNameV.get(columnIndex + fixedColumn);
		}
	}

	private class MListSelectionListener implements ListSelectionListener {
		boolean isFixedColumnTable = true;

		public MListSelectionListener(boolean isFixedColumnTable) {
			this.isFixedColumnTable = isFixedColumnTable;
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if (isFixedColumnTable) {
				int row = fixedColumnTable.getSelectedRow();
				floatingColumnTable.setRowSelectionInterval(row, row);
			} else {
				int row = floatingColumnTable.getSelectedRow();
				fixedColumnTable.setRowSelectionInterval(row, row);
			}
		}
	}

	public JTableUtil(List<String> columnNameV2,
			List<List<Object>> tableValueV2, int fixedColumn) {
		super();
		setLayout(new BorderLayout());
		this.columnNameV = columnNameV2;
		this.tableValueV = tableValueV2;
		this.fixedColumn = fixedColumn;
		// create fixedColumnTablez
		fixedColumnTableModel = new FixedColumnTableModel();
		fixedColumnTable = new JTable(fixedColumnTableModel);
		ListSelectionModel fixed = fixedColumnTable.getSelectionModel();
		fixed.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fixed.addListSelectionListener(new MListSelectionListener(true));
		// create floatingColumnTable
		floatingColumnTableModel = new FloatingColumnTableModel();
		floatingColumnTable = new JTable(floatingColumnTableModel);
		floatingColumnTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ListSelectionModel floating = floatingColumnTable.getSelectionModel();
		floating.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		floating.addListSelectionListener(new MListSelectionListener(false));
		// create scrollPane
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER,
				fixedColumnTable.getTableHeader());
		JViewport viewport = new JViewport();
		viewport.setView(fixedColumnTable);
		viewport.setPreferredSize(fixedColumnTable.getPreferredSize());
		scrollPane.setRowHeaderView(viewport); // viewport �ӿ�
		scrollPane.setViewportView(floatingColumnTable);
		add(scrollPane, BorderLayout.CENTER);
	}
}