/*
This program prompts the user for a sequence and an order. The program then calculates x,y-tuples, the 
magnitude of which depends on m. Then, for each x,y-tuple, the program generates a sequence which it then
checks with the inputted sequence. If the sequences match, the program returns the x,y-tuples. If not, 
a new x,y-tuple is generated.

This process repeats over and over, printing its progress as it goes. If it finds an x,y-tuple that generates
the correct Motzkin Triangle (and its first column) then the program finishes and prints the tuple. Otherwise,
it informs the user that no such x,y-tuple exists given the inputted parameters.

Author: Isaac DeJager
Date: 6/17/19
*/
import java.util.*;
import java.lang.*;

public class ColorCalculator {

   public static int[] sequence = new int[8];
   public static int[] firstCol = new int[8];
   public static int[] xValues;
   public static int[] yValues;
   public static int[] values;
   
   //This value informs the user of what percentage of combinations have yet to be checked
   public static int percent = 0;
   
   public static int[][] triangle = new int[10][10];
   
   public static void main(String[] args) {

      Scanner input = new Scanner(System.in);
      
      //User inputs sequence
      System.out.println("Input the sequence (8 terms).");
      for(int i = 0; i < 8; i++) {
      
         sequence[i] = input.nextInt();
      
      }
   
      //User inputs the order
      System.out.println("Input a value for m.");
      int m = input.nextInt();
      int l = m + 1;
      
      xValues = new int[m];
      yValues = new int[m];
      
      values = new int[2 * m];
      
      int count = -1;
      double temp = Math.pow(2, xValues.length + 1) + 1;
      int max = (int)temp;
      
      temp = Math.pow(max, 2 * m);
      int maxComb = (int)temp;
      
      //Calling the recursive function
      generateXY(l, m, count, max, maxComb);
      
   }
   
   public static boolean check = false;
   public static void generateXY(int l, int m, int count, int max, int maxComb) {
      
      count++;
      
      for(int i = 0; i < max; i++) {
         
         //If the correct x's and y's have been found, exit the loop
         if(check == true) {
            break;
         }
         
         //This line of code fills the array that holds the values for x and y
         //The first half of the array is the x-tuple, the second have is the y-tuple
         values[count] = i;
         
         //If not all x's and y's have been chosen, call the function again
         if(count < (2 * m - 1))  {
            generateXY(l, m, count, max, maxComb);
         }
         
         else {
            
            for(int j = 0; j < m; j++) {
               xValues[j] = values[j];
            }
            
            for(int j = 0; j < m; j++) {
               yValues[j] = values[m + j];
            }
            
            //These two functions calculate the sequence the x,y-tuples generate
            fillTriangle(m);
            findFirstCol();
            
            //If the sequences match, print the x,y-tuples
            if(sequenceCheck(maxComb) == true) {

               check = true;
               
               for(int j = 0; j < m; j++) {
                  System.out.println("x" + j + " = " + xValues[j]);
               }
               for(int j = 0; j < m; j++) {
                  System.out.println("y" + j + " = " + yValues[j]);
               }
            }
            
            if(percent == maxComb && check != true) {
               System.out.println("No combination found.");
               System.exit(0);
            }
            
            emptyTriangle();
            
         }
      
      }
      
   }
               
   //The program uses the x,y-tuples to fill a 2-D array that is called a Motzkin Triangle
   public static void fillTriangle(int m) {
   
      triangle[0][0] = 1;
   
      for(int n = 1; n < triangle.length; n++) {
         
         for(int k = 0; k < n + 1; k++) {
            
            if(k == 0) {

               for(int i = 0; i < m && i < triangle.length; i++) {
                  
                  triangle[n][k] += (xValues[i] * triangle[n - 1][i]);
                  
               }
               
               if(m < triangle.length) {
               
                  triangle[n][k] += triangle[n - 1][m];
                  
               }
               
            }
            
            else {

               triangle[n][k] = triangle[n - 1][k - 1];
               
               for(int i = 0; i < m && (k + i) < triangle.length; i++) {
               
                  triangle[n][k] += (yValues[i] * triangle[n - 1][k + i]);
               
               }
               
               if((k + m) < triangle.length) {
               
                  triangle[n][k] += triangle[n - 1][k + m];
                  
               }
            
            }
         
         }
         
      }
   
   }
   
   public static void emptyTriangle() {
   
      for(int i = 0; i < triangle.length; i++) {
         
         for(int j = 0; j < triangle.length; j++) {
            
            triangle[i][j] = 0;
            
         }
         
      }
   
   }
   
   //The first column of the triangle is the sequence that is checked with the inputted sequence
   public static void findFirstCol() {
   
      for(int i = 0; i < 8; i++) {
      
         firstCol[i] = triangle[i][0];
         
      }
   
   }
   
   public static boolean sequenceCheck(int max) {
      
      percent++;
      
      if((max / 5) == percent) {
         System.out.println("80% of possible combinations remain");
      }
      
      if((2 * max / 5) == percent) {
         System.out.println("60% of possible combinations remain");
      }
      
      if((3 * max / 5) == percent) {
         System.out.println("40% of possible combinations remain");
      }
      
      if((4 * max / 5) == percent) {
         System.out.println("20% of possible combinations remain");
      }
      
      if(max == percent) {
         System.out.println("0% of possible combinations remain");
      }
      
      return Arrays.equals(firstCol, sequence);
      
   }
     
}