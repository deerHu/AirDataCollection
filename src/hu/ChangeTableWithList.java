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

public class ChangeTableWithList extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Provide a fixed column in a table.
	 * 
	 * <code><p>public boolean isCellEditable(int row, int column) {<p>
	        return getModel().isCellEditable(convertRowIndexToModel(row),<p>
	                                         convertColumnIndexToModel(column));<p>
	    }<p>
	    </code> so we can also directly rewrite the isCellEditable() in the table
	 * model.
	 * 
	 * @author Gaowen
	 * 
	 */

	public ChangeTableWithList() {
		super();
		setTitle("Data List");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<String> columnNameV = new ArrayList<>();
		columnNameV.add("日期"); // 日期作为第一列
		for (int i = 1; i < 21; i++) { // 从1开始，加上日期，共2列
			columnNameV.add("商品" + i);
		}
		List<List<Object>> tableValueV = new ArrayList<>();
		for (int row = 1; row < 31; row++) { // 总行数
			List<Object> rowV = new ArrayList<>();
			rowV.add(row); // 此列为日期列
			for (int col = 0; col < 20; col++) { // 总列数； 从0开始，共20列
				rowV.add((int) (Math.random() * 1000));
			}
			tableValueV.add(rowV);
		}
		final TTT panel = new TTT(columnNameV, tableValueV, 1);

		getContentPane().add(panel, BorderLayout.CENTER);

	}

	// public static void main(String[] args) {
	// JTable4Data frame = new JTable4Data();
	// frame.setVisible(true);
	// }
}

class TTT extends JPanel {
	/**
	 * <pre>
	 * public boolean isCellEditable(int row, int column) {
	 * 	return getModel().isCellEditable(convertRowIndexToModel(row),
	 * 			convertColumnIndexToModel(column));
	 * }
	 * </pre>
	 * 
	 * so we can also directly rewrite the isCellEditable() in the table model.
	 * 
	 * @author HAN
	 * 
	 */
	private static final long serialVersionUID = -8001758880985479654L;
	private List<String> columnNameV; // declare the table column name vector
	private List<List<Object>> tableValueV;
	private int fixedColumn = 1; // the fixed column number
	private JTable fixedColumnTable;
	private FixedColumnTableModel fixedColumnTableModel;
	private JTable floatingColumnTable;
	private FloatingColumnTableModel floatingColumnTableModel;

	private class FixedColumnTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 3935656415101599023L;

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
		private static final long serialVersionUID = -2481466672947191281L;

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

	public TTT(List<String> columnNameV2, List<List<Object>> tableValueV2,
			int fixedColumn) {
		super();
		setLayout(new BorderLayout());
		this.columnNameV = columnNameV2;
		this.tableValueV = tableValueV2;
		this.fixedColumn = fixedColumn;
		// create fixedColumnTable
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
		scrollPane.setRowHeaderView(viewport); // viewport 视口
		scrollPane.setViewportView(floatingColumnTable);
		add(scrollPane, BorderLayout.CENTER);
	}
}