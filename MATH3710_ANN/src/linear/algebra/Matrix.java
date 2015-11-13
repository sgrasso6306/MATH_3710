package linear.algebra;

public class Matrix {

	private double[][] _matrix;
	
	
	public Matrix(int rows, int cols) {
		_matrix = new double[rows][cols];
	}
	
	public Matrix(double[][] matrix) {
		_matrix = matrix;
	}
	
	
	
	// get and set matrix and elements
	public void setMatrix(double[][] matrix) {
		_matrix = matrix;
	}
	
	public void setElement(int row, int col, double element) {
		_matrix[row][col] = element;
	}
	
	public double getElement(int row, int col) {
		return _matrix[row][col];
	}	
	
	
	// get row and column dimensions
	public int rowCount() {
		return _matrix.length;
	}
	
	public int colCount() {
		return _matrix[0].length;
	}	
	
	
	// return one of the matrix's columns as a vector
	public Vector getColVector(int col) {
		double[] colVec = new double[rowCount()];
		
		for (int i=0; i<rowCount(); i++) {
			colVec[i] = _matrix[i][col];
		}
		
		return new Vector(colVec);
	}
	
	// return one of the matrix's rows as a vector
	public Vector getRowVector(int row) {
		double[] rowVec = _matrix[row];
		
		return new Vector(rowVec);
	}
	
	
	// transpose the matrix
	public Matrix transpose() {
		double[][] Mt = new double[colCount()][rowCount()];
		
		for (int row=0; row<colCount(); row++) {
			for (int col=0; col<rowCount(); col++) {
				Mt[row][col] = _matrix[col][row];
			}
		}
		
		return new Matrix(Mt);
	}
	
	
	// print matrix for debug
	public void printMatrix() {
		for (int row=0; row<rowCount(); row++) {
			for (int col=0; col<colCount(); col++) {
				System.out.print(_matrix[row][col]+ " ");
			}
			System.out.println();
		}
	}
	
}
