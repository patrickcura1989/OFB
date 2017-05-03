import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class Common {

    public static String randomStringNumber(int len)
    {          
        String seed = "0123456789";        
        return generateRandomString (seed, len);
    }
    
    public static String randomString(int len)
    {        
        String seed = "abcdefghijklmnopqrstuvwzyzABCDEFGHIJKLMNOPQRSTUVWXYZ";        
        return generateRandomString (seed, len);
    }
               
    private static String generateRandomString (String seed , int length){
    	Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length)
        {
            int next = rnd.nextInt(seed.length());
            sb.append(seed.charAt(next));
        }
        return sb.toString();
    }

    public static String randomStringNumber_NoRepeat(int len){    	
    	return shuffle(len);    
    }
    
    
    public static String shuffle (int n){
		int [] seed = {0,1,2,3,4,5,6,7,8,9};
		n = n >= seed.length ? seed.length : n ;		
		int [] tmp = new int [n + 1];
		int max = n >= 9 ? 9 : n ;
		Random rand = new Random ();
		for (int i = 0 ; i < n ; ++i) {
			tmp[i] = seed[i];
		}			
		tmp[n] = max + 1 ;					
		while (max > 0) {
			int r = rand.nextInt(max + 1) ;
			swap (tmp, r, max) ;
			max--;
		}					
		StringBuilder sb = new StringBuilder ();		
		for (int i = 0 ; i < n ; ++i) {
		    if (tmp[i] == 10) {
		    	swap (tmp, i, n) ;
		    }
			sb.append(tmp[i]);
		}
		return sb.toString();
	}
	
	private static void swap (int [] a , int i, int j){
		int tmp = a[i] + a[j] ;
		a[i] = tmp - a[i];
		a[j] = tmp - a[i];		
	}

    
    
    public static String generateRandomString_NoRepeat (int len){
    	   String seed = "abcdefghijklmnopqrstuvwzyzABCDEFGHIJKLMNOPQRSTUVWXYZ";   
    	   return randomNoRepeat (seed, len) ;
    }
    
    
    private static String randomNoRepeat (String seed , int len){
    	Random random = new Random ();
    	int actual_len = seed.length() < len ? seed.length() : len ;
    	HashSet<Integer> set = new HashSet<Integer> ();
    	StringBuilder sb = new StringBuilder();
    	while (sb.length() < len) {
    		int cur = random.nextInt(len) ;
    		while (!set.add(cur)) {
    			cur = random.nextInt(len) ;
    		}
    		sb.append(seed.charAt(cur)) ;
    	}
    	return sb.toString();
    }
    
    public static void sleep (long timeout, TimeUnit unit){
    	 try {
			unit.sleep(timeout) ;
    	 } catch (InterruptedException ignore) {}		    	 
    }
    
	public static boolean checkProcessRunning(String processName){
		processName = processName.toLowerCase() ;
		BufferedReader input = null ;
		try {
			String findProcess = processName;
			String filenameFilter = "/nh /fi \"Imagename eq "+findProcess+"\"";
			String tasksCmd = System.getenv("windir") +"/system32/tasklist.exe "+filenameFilter;
			Process p = Runtime.getRuntime().exec(tasksCmd);
			input = new BufferedReader(new InputStreamReader(p.getInputStream()));			
			String line = null;
			boolean found = false;
			while ((line = input.readLine().trim().toLowerCase()) != null && !(found = line.contains(findProcess)));			
			return found ;			
		} catch(IOException io) {			
			return false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception ignore) {}
			}
		}
	}
	
}
