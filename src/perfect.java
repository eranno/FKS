import java.util.Arrays;


public class perfect {

	public static void main(String[] args) {

		
		int[] K = {10, 22, 37, 40, 52, 60, 70, 72, 75};
		int m = 9;
		int a = 3;
		int b = 42;
		int p = 101;
		
		K = getRandomArr();
		m = K.length;
		hash h = new hash(K, m, a, b, p);
		h.run();
		//System.out.println(h.toString());
		System.out.println(h.getDetailedCollision());
		System.out.println("Total collisions: " + h.getCollision());
	}
	

	
	
	static int[] getRandomArr() {
		int m = 10;//random(1, 100);
		int[] arr = new int[m];
		int min = 1000000;
		int max = 10000000;
		for (int i=0; i<m; i++)
		{
			int t = random(min, max);
			//while ( !(Arrays.binarySearch(arr, t)>=0) )
			//	t = random(min, max);
			
			arr[i] = t;
		}
		return arr;
	}
	
	
	


	
	static void h1() {
		int m = 101;	//prime
		String a = "12731465";
		int b[] = new int[a.length()];
		int temp = 0;
		int j=0;
		
		//cut a to pieces < m
		for (int i=0; i<a.length(); i++)
		{
			if (temp*10 + a.charAt(i) - 48 > m)
			{
				//System.out.println(temp);
				b[j++] = temp;
				temp = 0;
			}
			temp *= 10;
			temp += a.charAt(i) - 48;
		}
		b[j++] = temp;
		
		
		//randomize k
//		int c[] = {17,38,12,62};
		int c[] = new int[j];
		for (int i=0; i<j; i++)
		{
			//int rnd = min + (int)(Math.random() * ((max - min) + 1));
			int rnd = 1 + (int)(Math.random() * ((m - 1) + 1));
			c[i] = rnd;
		}
		
		System.out.println(hash(b,c,m));
	}
	
	private static int random(int min, int max){
		return min + (int)(Math.random() * ((max - min) + 1));
	}
	
	
	//sigma[a(i)*k(i)]mod m 
	static int hash(int[] a, int[] k, int m) {
		int h = 0;
		for (int i=0; i<k.length; i++)
			//{System.out.println("("+a[i]+" * "+k[i]+")");
			h += a[i] * k[i];
		
		h %= m;
		return h;
	}
	
	//checks whether an int is prime or not.
	boolean isPrime(int n) {
	    for(int i=2;i<n;i++) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}
	
	//2
	boolean isPrime2(int n) {
	    for(int i=2;2*i<n;i++) {
	        if(n%i==0)
	            return false;
	    }
	    return true;
	}

}
