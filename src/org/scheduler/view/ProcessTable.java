package org.scheduler.view;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class ProcessTable extends AbstractTableModel {

    // Satisfying the linter 
    private static final long serialVersionUID = 1L;

    // TER stands for tempo executável restante
    private final String columns[] = {"PID", "Nome", "TER", "Prioridade"};
    private Vector<Object[]> data = new Vector<>();

    /**
     * 
     * @param data
     */
    public void push(Object data[]) {
        this.data.add(data);
        fireTableRowsInserted(this.data.size(), this.data.size()+1);
    }

    /**
     * 
     * @param row
     */
    public void remove(int row) {
        data.remove(row);
        fireTableRowsDeleted(row, row);
    }

    public void remove(Object data[]) {
        this.data.remove(data);
        fireTableRowsDeleted(0, this.data.size());
    }

    public void update() {
        fireTableRowsUpdated(0, data.size());
    }

    /**
     * @brief Clears the entire table
     */
    public void clear() {
        this.data.clear();
        fireTableRowsDeleted(0, data.size());
    }

    /**
     * 
     * @return
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * 
     * @return
     */
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * 
     */
    @Override
    public String getColumnName(int i) {
        return columns[i];
    }

    /**
     * 
     */
    public Object getValueAt(int row, int column) {
        return data.get(row)[column];
    }

    /**
     * 
     * @param row
     * @return
     */
    public Object[] getValue(int row) {
        return data.get(row);
    }
}