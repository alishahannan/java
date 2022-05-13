/**
 * Alisha Hannan
 * COP2805C
 * Program to calculate factors of an integer and
 * the smallest factors that compose said integer
 */

package cop2805;

import java.util.*;


public class hw6Hannan 
{
	//method for returning all factors of a number
	public static ArrayList<Integer> allFactors(int input)
	{
		ArrayList<Integer> factors = new ArrayList<Integer>();
		for(int i = 1; i <= input; i++)
		{
			if(input%i == 0)
			{
				factors.add(i);
			}
		}
		return factors;
	}
	
	//method to find the least amount of factors of a number
	public static ArrayList<Integer> smallestFactors(ArrayList<Integer> factors, int n)
	{
		ArrayList<Integer> smallest = new ArrayList<Integer>();
		while(n!= 1)
		{
			int i = 1;
			boolean nextDivFound = false;
			while(nextDivFound == false)
			{
				if (n%(factors.get(i)) == 0)
				{
					nextDivFound=true;
					smallest.add(factors.get(i));
					n = n/(factors.get(i));
				}
				else
					i++;
			}
		}
		return smallest;
	}
	
	//method to print factor lists
	public static void printLists(ArrayList<Integer> arrList)
	{
		for(Iterator iterator = arrList.iterator(); iterator.hasNext();)
		{
			Object obj = iterator.next();
			System.out.print(" " + obj);
		}
	}

	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter n: ");
		int n = scan.nextInt();

		ArrayList<Integer> output = allFactors(n);
		System.out.print("Results:");
		printLists(output);
		
		System.out.println();

		ArrayList<Integer> least = smallestFactors(output,n);
		System.out.print("Least Factors:");
		printLists(least);
	}
}
