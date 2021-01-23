package linalg;

/*** A class that represents a two dimensional real-valued (double) matrix
 *   and supports various matrix computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 *
 */
public class Matrix {
	private int _nRows; // Number of rows in this matrix; nomenclature: _ for data member, n for integer
	private int _nCols;// Number of columns in this matrix; nomenclature: _ for data member, n for integer
	private double [][] _d2dVal;//Use a 2D array to represent a matrix; nomenclature: _ for data member, d for double
	
	
	
/** Allocates a new matrix of the given row and column dimensions
 * 
 * @param rows
 * @param cols
 * @throws LinAlgException if either rows or cols is <= 0
 */
	public Matrix(int rows, int cols) throws LinAlgException {
		if (rows <= 0 || cols <= 0)
			throw new LinAlgException("Both dimensions (" + rows +","+ cols+ ") must be greater than 0");
		_nRows = rows;
		_nCols = cols;
		_d2dVal = new double[rows][cols];
	}
	
/** Copy constructor: makes a new copy of an existing Matrix m
 * 
 * @param m
 */
	public Matrix(Matrix m) {
		_nRows = m._nRows;
		_nCols = m._nCols;
		_d2dVal = new double [_nRows][_nCols];//This allocate a _nRows by _nCols 2D array/matrix
		for (int i = 0; i < _nRows; i++) {
			for(int j = 0; j < _nCols; j++) {
			_d2dVal[i][j]=m._d2dVal[i][j];
			}
		}
	}

/** Constructs a String representation of this Matrix
 * 
 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < _nRows; i++) {//Append _nRows rows
			sb.append("[");
			for (int j = 0; j < _nCols; j++)
				sb.append(String.format(" %6.3f ", _d2dVal[i][j])); // Append to columns (append actual value to each row vectors)
			sb.append(" ]\n");
		}
		return sb.toString();
	}

/** Tests whether another Object o (most often a matrix) is a equal to *this*
 *  (i.e., are the dimensions the same and all elements equal each other?)
 * 
 * @param o the object to compare to
 */
	public boolean equals(Object o) {
		//First, check if input object o is the same type as Matrix class
		if (o instanceof Matrix) { 
			Matrix m = (Matrix) o; //What does this line do?
			
		//Second, check if _nRows and _nCols equal
			if (_nRows != m._nRows || _nCols != m._nCols)
				return false;
		//Third, check if content of _d2dVal equal
			for (int i = 0; i < _nRows; i++) {
				for (int j = 0; j < _nCols; j++) {
					if (_d2dVal[i][j] != m._d2dVal[i][j])
						return false;// If two Vectors mismatch at any index, they are not equal
				}
			}
			return true;// Everything matched... objects are equal!
		}
		else
			return false;
	}	
	
/** Return the number of rows in this matrix
 *   
 * @return 
 */
	public int getNumRows() {
		return _nRows;
	}

/** Return the number of columns in this matrix
 *   
 * @return 
 */
	public int getNumCols() {
		return _nCols;
	}

/** Return the scalar value at the given row and column of the matrix
 * 
 * @param row
 * @param col
 * @return
 * @throws LinAlgException if row or col indices are out of bounds
 */
	public double get(int row, int col) throws LinAlgException {
		if ((row < 0 || row >= _nRows)||(col < 0 || col >= _nCols)) {
			throw new LinAlgException("One or both indices (" + row + ", " + col +") are out of bounds ([0, " + _nRows + "],[0, " + _nCols + "])");
		}
		else {
			return _d2dVal[row][col];	
		}
	}
	
/** Return the Vector of numbers corresponding to the provided row index
 * 
 * @param row
 * @return
 * @throws LinAlgException if row is out of bounds
 * method 1: create a new vector, assign the row array and _nDim to the new vector, then return the new vector.
 */
	public Vector getRow(int row) throws LinAlgException {
		if (row < 0 || row >= _nRows)
			throw new LinAlgException("Row index (" + row + ") out of bounds [0, " + _nRows + "])");
		
		else {
			Vector newVector = new Vector(_nCols);
			newVector.changeDim(_nCols);
			for (int j = 0; j < _nCols; j++) 
				newVector.set(j, _d2dVal[row][j]);
			return newVector;	
		}
	}

/** Set the row and col of this matrix to the provided val
 * 
 * @param row
 * @param col
 * @param val
 * @throws LinAlgException if row or col indices are out of bounds
 */
// TODO
	public void set(int row, int col, double val) throws LinAlgException {
		if ((row < 0 || row >= _nRows)||(col < 0 || col >= _nCols)) {
			throw new LinAlgException("One or both indices (" + row + ", " + col +") are out of bounds ([0, " + _nRows + "],[0, " + _nCols + "])");
		}
		
		else {
			_d2dVal[row][col] = val;
		}
	}
	
/** Return a new Matrix that is the transpose of *this*
 * 
 * @return
 * @throws LinAlgException
 */
	public Matrix transpose() throws LinAlgException {
		Matrix transpose = new Matrix(_nCols, _nRows);
		for (int row = 0; row < _nRows; row++) {
			for (int col = 0; col < _nCols; col++) {
				transpose.set(col, row, get(row,col));
			}
		}
		return transpose;
	}

/** Return a new Matrix that is the square identity matrix (1's on diagonal, 0's elsewhere) 
 *  with the number of rows, cols given by dim.  E.g., if dim = 3 then the returned matrix
 *  would be the following:
 *  
 *  [ 1 0 0 ]
 *  [ 0 1 0 ]
 *  [ 0 0 1 ]
 * 
 * @param dim
 * @return
 * @throws LinAlgException if the dim is <= 0
 * Logic: 1. create a square matrix
 * 		  2. for
 */
	public static Matrix GetIdentity(int dim) throws LinAlgException {
		if (dim <= 0)
			throw new LinAlgException("Size " + dim + " must be greater than 0");
		else {
			Matrix identity_Matrix = new Matrix(dim,dim);
			for (int diag = 0; diag < dim; diag++) {
				identity_Matrix.set(diag, diag, 1);
			}
			return identity_Matrix;
		}		
	}
	
/** Returns the Matrix result of multiplying Matrix m1 and m2
 * 
 * @param m1
 * @param m2
 * @return
 * @throws LinAlgException if m1 columns do not match the size of m2 rows
 * Logic: 1. throw LinAlgException m1._nCols  != m2._nRows
 *        2. get Vector row_vec, get Vector col_vec
 *        3. for (int i = 0; i < product._nRows; i++){
 *        		for (int j = 0; j < product._nCols; j++){
 *              	for(int counter = 0; counter < row_vec.length; counter++){
 *        				product[0][0] = row_vec[counter]*col_vec[counter];
 *Logic_v2: 1.
 */
	public static Matrix Multiply(Matrix m1, Matrix m2) throws LinAlgException {
		if (m1._nCols != m2._nRows)
			throw new LinAlgException("Cannot multiply matrix m1 having " + m1._nCols + " columns with matrix m2 having " + m2._nRows + " rows");
		
		else {
			Matrix product = new Matrix(m1._nRows,m2._nCols);
			for (int i = 0; i < m1._nRows; i++){
				for (int j = 0; j < m2._nCols; j++) {
					for (int count = 0; count < m1._nCols; count++) {
						product._d2dVal[i][j] += m1._d2dVal[i][count] * m2._d2dVal[count][j];		
					}
				}
			}
			return product;
		}
	}
		
	/** Returns the Vector result of multiplying Matrix m by Vector v (assuming v is a column vector)
	 * 
	 * @param m
	 * @param v
	 * @return
	 * @throws LinAlgException if m columns do match the size of v
	 */
	public static Vector Multiply(Matrix m, Vector v) throws LinAlgException {
		int v_nDim = v.getDim();
		if (m._nCols != v_nDim)
			throw new LinAlgException("Cannot multiply matrix with " + m._nCols + " columns with a vector of dimension " + v_nDim);
		
		else {
			Vector product = new Vector(m._nRows);
			double[] temp = new double[m._nRows];
			for (int i = 0; i < m._nRows; i++) {
				for (int j = 0; j < m._nCols; j++) {
					temp[i] += m._d2dVal[i][j] * v.get(j);
				}
			}
			for (int c = 0; c < temp.length; c++) {
				product.set(c, temp[c]);
			}
			return product;
		}
		
	}

}
