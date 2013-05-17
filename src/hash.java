
public class hash {

	private int[] K;	//input
	private int m;		//input size - first level
	private int a;		//random a - first level
	private int b;		//random b - first level
	private int p;		//prime
	
							//helper first level
	private int[][] t;		//double to save collision per cell
	private int[][][] T;	//result jagged array
	
	//statistics values
	private int collision;	//collisions counter
	private int totalSize;	//total table size
	private double minCol, maxCol, avgCol;
	
	
	public hash(int[] K, int a, int b, int p) {
		this.K = K;
		this.m = K.length;
		this.a = a;
		this.b = b;
		this.p = p;
		
		collision = 0;
		totalSize = 0;
		minCol = -1;
		maxCol = -1;
		avgCol = -1;
	}
	
	public void run() {
		
		//first level
		t = new int[m][2];
		for (int k=0; k<m; k++)
		{
			t[ h(K[k],a,b,p,m) ][0]++;
		}
		//System.out.println(Arrays.toString(t));

		
		//before second level make sizes
		T = new int[m][][];	//jagged array
		for (int j=0; j<m; j++)
		{
			if (t[j][0] > 0){
				T[j] = new int[2][];
				T[j][0] = new int[3];
				T[j][0][0] = ( t[j][0]*t[j][0] );	//m
				T[j][0][1] = random(1, p-1);		//a
				T[j][0][2] = random(1, p-1);		//b
				T[j][1] = new int[ T[j][0][0] ];	//array
				
				totalSize += T[j][0][0];			//save total size
			}
		}

		
		//second level
		for (int k=0; k<m; k++)
		{
			int index=h(K[k],a,b,p,m);
			
			
			//check if there are collisions
			boolean inner_col = false;
			int h = h(K[k], T[index][0][1], T[index][0][2], p, T[index][0][0]);
			int val = T[index][1][h];
			while ( (inner_col) || (val > 0 && val != K[k]) )
			{
				inner_col = false;	//reset inner collision in case there were already an inner collision
				
				//collision found
				t[index][1]++;	//count it per cell
				collision++;	//count it as total

				//reRandomize a & b to this cell
				T[index][0][1] = random(1, p-1);	//a
				T[index][0][2] = random(0, p-1);	//b

				//reHash them (only the items we hashed in this cell)
				int[] sec = new int[ T[index][0][0] ];
				for (int i=0; i<T[index][0][0]; i++)
				{
					//T[ index ][1][ h(K[k], T[index][0][1], T[index][0][2], p, T[index][0][0]) ]
					
					//if key hashed then reHash it
					if ( T[index][1][i] > 0 ) {
						int inner_index = h(T[index][1][i], T[index][0][1], T[index][0][2], p, T[index][0][0]);
						if (sec[inner_index] > 0)
						{	//...LOOP
							inner_col = true;
							break;
						}
						
						sec[inner_index] = T[index][1][i];	
					}
				}

				//if no collisions then update the array and continue
				if (!inner_col) T[index][1] = sec;

				//update val
				h = h(K[k], T[index][0][1], T[index][0][2], p, T[index][0][0]);
				val = T[index][1][h];
			}
			
			//System.out.print(T[ index ][1][ h(K[k], T[index][0][1], T[index][0][2], p, T[index][0][0]) ] + " - ");
			T[index][1][ h(K[k], T[index][0][1], T[index][0][2], p, T[index][0][0]) ] = K[k];
			//System.out.println(T[ index ][1][ h(K[k], T[index][0][1], T[index][0][2], p, T[index][0][0]) ]);
		}
	}
	
	public int[] getK() {
		return K;
	}

	public void setK(int[] k) {
		K = k;
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public void setCollision(int collision) {
		this.collision = collision;
	}

	//get & set
	public int getCollision() {return collision;}
	public int getTotalSize() {return totalSize;}
	public double getMinCol() {return minCol;}
	public double getMaxCol() {return maxCol;}
	public double getAvgCol() {return avgCol;}
	
	
	//count collisions
	public void collisionsStats()
	{
		int f = 0;
		for (int k=0; k<m; k++)
		{
			if (t[k][0] > 0 && t[k][1] > 0)
			{
				f++;
				
				if (t[k][1] > maxCol)
					maxCol = t[k][1];
				
				if (minCol<0 || t[k][1] < minCol)
					minCol = t[k][1];
			}
		}
		avgCol = collision/f;
	}
	
	public String getDetailedCollision()
	{
		String result = "Collisions:\n";
		for (int i=0; i<t.length; i++) {
			if (t[i][0] > 0)
				result += i+". ["+t[i][0]+"]["+t[i][1]+"]\n";
		}
		return result;
	}
	
	public String toString() {
		String result;
		
		//print the array
		result = "\n[T]|[m ][a ][b ]|[....\n";
		for (int i=0; i<T.length; i++) {
			if (t[i][0] > 0){
				for (int j=0; j<T[i].length; j++) {
					if (j==0) result += "["+i+"]";
					for (int k=0; k<T[i][j].length; k++) {
						if (k==0) result += "|";
						result += "["+ (T[i][j][k]<10?"0":"") + T[i][j][k] +"]";
					}
						
				}
				result += "\n";
			}
		}
		
		return result;
	}
	
	
	
	private int random(int min, int max){
		
		return min + (int)(Math.random() * ((max - min) + 1));		
	}
	
	private int h(int k, int a, int b, int p, int m) {
		//mult must be long otherwise it will get overflow
		long mult = (long)a*(long)k;
		return ((int)((mult + b) % p)) % m;
	}
	
	
	
}
