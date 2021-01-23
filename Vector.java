package linalg;

/*** A class that represents a multidimensional real-valued (double) vector
 *   and supports various vector computations required in linear algebra.
 *   
 *   Class and method comments are in JavaDoc: https://en.wikipedia.org/wiki/Javadoc
 *
 */
public class Vector {

	private int _nDim;       // Dimension of the Vector; nomenclature: _ for data member, n for integer
	private double[] _adVal; // Contents of the Vector; nomenclature: _ for data member, a for array, d for double

/**Constructor: allocates space for a new vector of dimension dim.
 *
 * @param dim
 * throws LinAlgException if vector dimension is < 1
 * */
	public Vector(int dim) throws LinAlgException {
		if (dim <= 0)
			throw new LinAlgException("Vector dimension " + dim + " cannot be less than 1");
		_nDim = dim;
		_adVal = new double[dim]; // Entries will be automatically initialized to 0.0
	}
	
/**Copy constructor: makes a new copy of an existing Vector v
 *
//@param v
 */
	public Vector(Vector v) {
		_nDim = v._nDim;
		_adVal = new double[_nDim]; // This allocates an array of size _nDim
		for (int index = 0; index < _nDim; index++)
			_adVal[index] = v._adVal[index];
	}

/**Constructor: creates a new Vector with dimension and values given by init
 *
//@param init: a String formatted like "[ -1.2 2.0 3.1 5.8 ]" (must start with [ and end with ])
//@throws LinAlgException if init is not properly formatted (missing [ or ], or improperly formatted number)
*/	
	public Vector(String init) throws LinAlgException {
		
		// The following says split init on whitespace (\\s) into an array of Strings
		String[] split = init.split("\\s");  
		//Uncomment the following to see what split produces
		//for (int i = 0; i < split.length; i++) 
		//System.out.println(i + ". " + split[i]);

		if (!split[0].equals("[") || !split[split.length-1].equals("]"))
			throw new LinAlgException("Malformed vector initialization: missing [ or ] in " + init);

		// We don't count the [ and ] in the dimensionality
		_nDim = split.length - 2;
		_adVal = new double[_nDim];
		
		// Parse each number from init and add it to the Vector in order (note the +1 offset to account for [)
		for (int index = 0; index < _nDim; index++) {
			try {
				set(index, Double.parseDouble(split[index + 1]));
			} catch (NumberFormatException e) {
				throw new LinAlgException("Malformed vector initialization: could not parse " + split[index + 1] + " in " + init);
			}
		}
	}

/**Overrides method toString() on Object: converts the class to a human readable String
 *
 *@Override
**/	
	public String toString() {
		// We could just repeatedly append to an existing String, but that copies the String each
		// time, whereas a StringBuilder simply appends new characters to the end of the String
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < _nDim; i++)
			sb.append(String.format(" %6.3f ", _adVal[i])); // Append each vector value in order
		sb.append(" ]");
		return sb.toString();
	}

/** Overrides address equality check on Object: allows semantic equality testing of vectors,
 *  we define two objects are equal iff they have the same dimensions and values match at all indices
 *
 * @param o the object to compare to
 */
//@Override // optional annotation to tell Java we expect this overrides a parent method -- compiler will warn if not
	public boolean equals(Object o) {
		if (o instanceof Vector) {
			Vector v = (Vector)o;

			if (_nDim != v._nDim)
				return false; // Two Vectors cannot be equal if they don't have the same dimension
			for (int index = 0; index < _nDim; index++)
				if (_adVal[index] != v._adVal[index])
					return false; // If two Vectors mismatch at any index, they are not equal
			return true; // Everything matched... objects are equal!
		} else // if we get here "(o instanceof Vector)" was false
			return false; // Two objects cannot be equal if they don't have the same class type
	}
	
/**Get the dimension of this vector, and return the dimensionality of this Vector
 */
	public int getDim() { 
		return _nDim;
	}

/**Returns the value of this vector at the given index
 *
 *@param index
 *@return
 *@throws LinAlgException if array index is out of bounds (see throw examples above)
*/	
	public double get(int index) throws LinAlgException {
		
		if (index < 0 || index >= _nDim) {
			//LinAlgException is a class, when we throw LinAlgException, we can't throw the concept,rather,we have to throw the instance of the concept-"LinAlgException".That's why we use "new" to create an instance.
			//Think of LinAlgException (or class)as the concept of rock. And think of "new LinAlgException" as the actual rock. Throw the actual rock, not the concept of rock.
			throw new LinAlgException("Index " + index + " is out of bounds [0, " + _nDim + "]"); 

		}
		return _adVal[index];
	}

/** Set the value val of the vector at the given index (remember: array indices start at 0)
 * 
 * @param index
 * @param val
 * @throws LinAlgException if array index is out of bounds (see throw examples above)
 */
	public void set(int index, double val) throws LinAlgException {
		if (index < 0 || index >= _nDim) {
			throw new LinAlgException("Index " + index + " is out of bounds [0, " + _nDim + "]");
		}
		else {
			_adVal[index] = val;
		}
	return;
	}
	
/** Change the dimension of this Vector by *reallocating array storage* and copying content over
 *  ... if new dim is larger than current dim then the additional indices take value 0.0
 *  ... if new dim is smaller than current dim then any indices in current vector beyond current
 *      dim are simply lost
 * 
 * @param new_dim
 * @throws LinAlgException if vector dimension is < 1
 */
	public void changeDim(int new_dim) throws LinAlgException {
		if (new_dim < 1) {
			throw new LinAlgException("Vector dimension " + new_dim + " cannot be less than 1");	
		}
		
		else if (new_dim > _nDim) {
			double [] new_adVal = new double[new_dim];
			for(int i = 0; i < _nDim; i++) {
				//set(i,_adVal[i]);
				new_adVal[i] = _adVal[i];
			}
			_adVal = new_adVal;
			_nDim = new_dim;
			}
		
		else if (new_dim < _nDim) {
			double [] new_adVal = new double[new_dim];
			for(int i = 0; i < new_dim; i++) {
				new_adVal[i] = _adVal[i];
			}
			_adVal = new_adVal;
			_nDim = new_dim;
		}	
	return;
	}
			
				
/** This adds a scalar d to all elements of *this* Vector
 *  (should modify *this*)
 * @param d
 */
	public void scalarAddInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
			_adVal[index] += d;
		return;
	}
	
/** This creates a new Vector, adds a scalar d to it, and returns it
 *  (should not modify *this*)
 * @param d
 * @return new Vector after scalar addition
 */
	public Vector scalarAdd(double d) {
		Vector newVector = new Vector(this);//What does "this" do? This is like self
		for (int index=0; index < _nDim; index++) {
			newVector._adVal[index] += d;
		}
		return newVector ;
	}
	
/** This multiplies a scalar d by all elements of *this* Vector
 *  (should modify *this*) 
 * @param d
 */
	public void scalarMultInPlace(double d) {
		for (int index = 0; index < _nDim; index++)
			_adVal[index] *= d;
		return;
	}
	
/** This creates a new Vector, multiplies it by a scalar d, and returns it
 *  (should not modify *this*)
 * 
 * @param d
 * @return new Vector after scalar addition
 */
	public Vector scalarMult(double d) {
		Vector newVector = new Vector(this);
		for (int index=0; index < newVector._nDim; index++) {
			newVector._adVal[index] *= d;
		}
		return newVector;
	}
	

/** Performs an elementwise addition of v to *this*, modifies *this*
 * 
 * @param v
 * @throws LinAlgException if dimensions of the two operand vectors do not match
 */
	public void elementwiseAddInPlace(Vector v) throws LinAlgException {
		if (v._nDim != _nDim) {
			throw new LinAlgException("Cannot elementWiseAdd vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		else {
			for (int i = 0; i < _nDim; i++)
				_adVal[i] += v._adVal[i];
		}
		return;
	}

/** Performs an elementwise addition of *this* and v and returns a new Vector with result
 * 
 * @param v
 * @return
 * @throws LinAlgException if dimensions of the two operand vectors do not match
 */
	public Vector elementwiseAdd(Vector v) throws LinAlgException {
		Vector newVector = new Vector(this);
		if (v._nDim != newVector._nDim) {
			throw new LinAlgException("Cannot elementWiseAdd vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
	
		else {
			for (int i = 0; i < newVector._nDim; i++)
				newVector._adVal[i] += v._adVal[i];
			}
		return newVector;
	}
	
/** Performs an elementwise multiplication of v and *this*, modifies *this*
 * 
 * @param v
 * @throws LinAlgException if dimensions of the two operand vectors do not match
 */
	public void elementwiseMultInPlace(Vector v) throws LinAlgException {
		if (v._nDim != _nDim) {
			throw new LinAlgException("Cannot elementWiseMult vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		else {
			for (int i = 0; i < _nDim; i++)
				_adVal[i] *= v._adVal[i];
		}
		return;
	}

/** Performs an elementwise multiplication of *this* and v and returns a new Vector with result
 * 
 * @param v
 * @return
 * @throws LinAlgException if dimensions of the two operand vectors do not match
 */
	public Vector elementwiseMult(Vector v) throws LinAlgException {
		Vector newVector = new Vector(this);
		if (v._nDim != newVector._nDim) {
			throw new LinAlgException("Cannot elementWiseMult vectors of different dimensions " + _nDim + " and " + v._nDim);
		}
		else {
			for (int i = 0; i < newVector._nDim; i++)
				newVector._adVal[i] *= v._adVal[i];
			}
		return newVector;
	}

/** Performs an inner product of Vectors v1 and v2 and returns the scalar result
 * 
 * @param v1
 * @param v2
 * @return
 * @throws LinAlgException
 */
	public static double InnerProd(Vector v1, Vector v2) throws LinAlgException {
		double inner_prod = 0;
		if (v1._nDim != v2._nDim) {
			throw new LinAlgException("Cannot innerProd vectors of different dimensions " + v1._nDim + " and " + v2._nDim);
		}
		else {
			for (int i = 0; i < v1._nDim; i++)
			inner_prod += v1._adVal[i]*v2._adVal[i];
		}
		return inner_prod;
	}
}
