 /**
  Function to take input string of valid numerical and arithmetic operator entries 
  and evaluate and return the results. Should also check for errors.
  The operations are as follows: Recursively
  Evaluate operations in brackets
  At the end of this, the string has no brackets
  Then recursively do the arithmetic on this string
  Order of operations: brackets, division, multiplication, addition/subtraction
 */
 
import java.lang.*;

public class Oprtr {

	  private String valid_chars;    
	  private String left_braces; 
	  private String right_braces; 
	  private char [] braces;      //a stack ADT for all left braces found; each left brace must have a matching right brace in the string
	  private int top;   
	  private String stCalc, x;  
	   /*
	     stCalc and x are string variables to be computed recursively. See below 
	    */

	  	  
	   public Oprtr(String s)
	   {
	       valid_chars = s;
	       left_braces = "{[("; 
	       right_braces = "}])";
	       braces = new char[10];
	       for(int j=0; j< 10; j++)
	       {
	        braces[j]=' ';   
	        }
	       top = -1;
	       stCalc = " ";
	       x = " ";
	       
	    }
	    
	    public void pushstack(char c)
	    {
	         top++;
	         braces[top] = c; 
	        
	    }
	    
	    public int popstack()
	    {
	        if(top == -1)
	        {
	            System.out.println("Error: braces stack is empty");
	            return 0;
	        }
	        braces[top] = ' ';
	        top--;
	        return 0;
	    }
	    
	    public boolean is_empty()
	    {
	        if(top <= -1)
	           {return true;}
	           else {return false;}
	    }
	    
	    public char read_top()
	    {
	        if(top == -1)
	        {
	            System.out.println("Error: braces stack is empty");
	            return ' ';
	        }
	      return braces[top];  
	    }
	   
	   	    
	    public int calc(String st)
	    {
	        st = st.replaceAll("\\s","");
	        if(st.charAt(0)== '-')
	        {
	            st = ("0").concat(x);
	        }
	        
	          x = st.replaceAll("-","+-");
	       
	         
	        char[] carr = x.toCharArray();
	        
	        int len = carr.length, i = 0;
	        String symbol = "";
	        
	        int indicate = -5;
	        while(i < len)
	        {
	            
	            int m = valid_chars.indexOf(carr[i]);
	            int b = left_braces.indexOf(carr[i]);
	            if(m == -1)
	            {
	                System.out.println("Error: "+i+"-th element is invalid");
	                return 0;
	            }
	            if(b != -1)
	            {
	                pushstack(carr[i]);
	                
	            }
	            
	            i++;
	        }
	        int left = 0, right=carr.length;
	      
	      char tps =' ', right_tps= ' ';
	      int match_index = -5;
	      
	        String [] opString = {"/", "*", "+"};       
	        
	        
	        while(is_empty()==false){    // Recursively go through string and do all bracket operations
	          
	           carr = x.toCharArray();
	          
	           left= findLeft(carr);
	                       
	            tps = read_top();
	            match_index = left_braces.indexOf(tps);
	            right_tps = right_braces.charAt(match_index);                     
	            right = x.indexOf(right_tps);	                     
	            stCalc = x.substring(left+1,right);
	          
	           for(int j =0; j < opString.length; j++)
	           {             
	              symbol = opString[j];	                          
	              indicate = stCalc.indexOf(symbol);
	              stCalc = rep(symbol, x, indicate);	              	             	              
	           }
	                      
	           x = x.substring(0,left) + stCalc + x.substring(right+1);
	           carr = x.toCharArray();
	           left = findLeft(carr);           
	           right_tps = right_braces.charAt(match_index);
	           right = x.indexOf(right_tps);
	           popstack();   	          
	        }
	        stCalc = x;
	                
	        for(int j = 0; j< opString.length; j++)
	        {
	           symbol = opString[j];	          
	           indicate = stCalc.indexOf(symbol);
	           boolean flag = true;
	           
	           while(indicate != -1){
	        	    indicate = munger(indicate, symbol, flag);             	                            
	            }	          
	        }	        
	        int calculation = Integer.parseInt(stCalc);	           
	        return calculation;
	    }
	    
	    public String rep(String symbol, String x, int indicate)
	    {    
	          boolean flag = false;
	          indicate = stCalc.indexOf(symbol);
	          while(indicate != -1){	              
	              indicate = munger(indicate, symbol, flag);                          	           
	          }  
	          
	          if(stCalc == "N"){
	             return " ";
	            }	        
	          return stCalc;
	    }
	    
	    
	    public int munger(int indicate, String symbol, boolean flag)
	    {    		    	
            int i=0;     
            String s1 = stCalc.substring(0,indicate);                    
            String [] items1 = s1.split("\\+|\\*|\\/");                     
            float [] results1 = new float[items1.length];
             for (i = 0; i < items1.length; i++) {                 
                results1[i] = Float.valueOf(items1[i]);
             }
            
            float c1 = results1[results1.length-1];                        
            String s2 = stCalc.substring(indicate+1);           
            String [] items2 = s2.split("\\+|\\*|\\/");                                  
            float[] results2 = new float[items2.length];
             for (i = 0; i < items2.length; i++) {
                results2[i] = Float.parseFloat(items2[i]);
             }
                       
            float c2 = results2[0];                     
            int intc1 = (int)c1;
            int intc2 = (int)c2;            
            String sc1 = Integer.toString(intc1); 
            String sc2 = Integer.toString(intc2);           
            int len_sc1 = sc1.length();
            int len_sc2 = sc2.length();           
            stCalc = calcNoBrace(intc1, intc2, symbol);
            
            if(flag == true){
         	    String left_side = x.substring(0,indicate- len_sc1);
                String right_side = x.substring(indicate + len_sc2 +1 );                                                              
                x = left_side + stCalc + right_side;               
                stCalc = x;                          
              }                                                         
            indicate = stCalc.indexOf(symbol);             	
	    	            	    	
	    	return indicate;
	    }
	    
	   	    
	    public int findLeft( char[] carr)
	    {
	        
	       int left = -1;
	       for( int i = 0; i< carr.length; i++)
	        {	         
	          int b = left_braces.indexOf(carr[i]);
	          if(b != -1)
	             left = i;
	         	                   
	        }  
	        return left;
	    }
	    
	   
	   	      
	    public String calcNoBrace(int calc1, int calc2, String symbol)
	    {
	           int res = 0;	            
	            if(symbol == "/")
	            {
	                 if(calc2 == 0){
	                  System.out.println("Error: division by 0 condition");
	                  return " ";
	                 }
	                 if(calc1 ==0 && calc2 == 0)
	                 {
	                  System.out.println("Error: undefined condition");
	                  return " ";
	                 }  
	                 
	                res = calc1/calc2;
	            }
	            
	            if(symbol == "*")
	            {
	             res = calc1*calc2;   
	            }
	              
	            if(symbol == "+")
	            {
	              res =  calc1 + calc2;
	            }	                       	           
	            String resSt = Integer.toString(res);
	            
	            return resSt;
	    }
	    
	    public void test1()
	    {
	        String str = "2 + 5 + (3*4)"; 
	        int ans = calc(str);
	        if(ans== 19)
	        {
	            System.out.println("Test 1 passed");	           
	        }
	        else{
	            System.out.println("Test 1 failed");
	        }
	    }
	    
	    public void test2()
	    {
	        String str = "5 - 1/1 + 4"; 
	        int ans = calc(str);
	        if(ans == 8)
	        {
	            System.out.println("Test 2 passed");	           
	        }
	        else{
	            System.out.println("Test 2 failed");
	        }
	    }
	          
	    public  static void main(String [] args)
	   {
	        String s ="0123456789+-/*(){}[]";   // Set what entities are to be valid	              
	        Oprtr ob = new Oprtr(s);
	      
	        ob.test1();
	        ob.test2();	     	      
	    }
			
}
