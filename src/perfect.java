public class perfect {

	public static void main(String[] args) {

		
		int[] K = {10, 22, 37, 40, 52, 60, 70, 72, 75};
		int m = 9;
		int a = 3;
		int b = 42;
		int p = 101;
		hash h;

		
		//randomize
		p = 2147483647;	//maximum value of 32 bit || Integer.MAX_VALUE || and it's a prime!
		int avgCol=0, maxCol=0, minCol=-1, avgRun=0;
		long maxRun=0, minRun=-1;
		int s = 100;
		long startTime, endTime, runTime;
		for (int i=0; i<s; i++)
		{
			m = random(10000, 100000);
			K = getRandomArr(1, 1000, p, m);
			a = random(1, p-1);	//a
			b = random(0, p-1);	//b
			
			h = new hash(K, a, b, p);
			startTime = System.currentTimeMillis();	//System.nanoTime(); //
			h.run();
			endTime = System.currentTimeMillis();	//System.nanoTime(); //
			runTime = Math.abs(endTime - startTime);
			
			
			//statistics
			avgCol += h.getCollision();
			avgRun += runTime;
			
			if (runTime > maxRun)
				maxRun = runTime;
			
			if (minRun<0 || runTime < minRun)
				minRun = runTime;
			
			if (h.getCollision() > maxCol)
				maxCol = h.getCollision();
			
			if (minCol<0 || h.getCollision() < minCol)
				minCol = h.getCollision();
			
			
			//System.out.println(runTime);
			//System.out.println(h.toString());
			//System.out.println(h.getDetailedCollision());
			//System.out.println("Total collisions: " + h.getCollision());
		}
		
		//print statistics results
		System.out.println("Statics calculate with "+s+" arrays\n");
		System.out.println("Collisions:");
		System.out.println("- Total: " + avgCol);
		System.out.println("- Min: " + minCol);
		System.out.println("- Max: " + maxCol);
		System.out.println("- Average: " + avgCol/s);
		System.out.println("\nrun time (ms):");
		System.out.println("- Total: " + avgRun);
		System.out.println("- Min: " + minRun);
		System.out.println("- Max: " + maxRun);
		System.out.println("- Average: " + avgRun/s);
		
	}
	

	
	
	static int[] getRandomArr(int min, int max, int prime, int len) {
		int[] arr = new int[len];
		int step = 0;
		for (int i=0; i<len; i++)
		{
			if (step > prime + max)
				step = 0;
			
			int t = random(min, max);
			step += t;
			arr[i] = prime - step;
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
