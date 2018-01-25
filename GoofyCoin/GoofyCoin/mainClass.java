import java.io.*;
import java.net.*;
import java.util.*;

public class mainClass
{
	public static void main(String[] args)throws Exception
	{
		Scanner sc=new Scanner(System.in);
		HashMap<String, DSA> dsa = new HashMap<String, DSA>();
		System.out.println("Enter no. of people : ");
		int n=sc.nextInt();
		for(int i=0;i<n;i++)
		{
			System.out.println("Enter name : ");
			String name=sc.next();
			dsa.put(name, new DSA());
		}

		BlockChain surCoin = new BlockChain(dsa);


		String menu="Enter the no. for the following : \n"
		+"1 : MakeCoin\n"
		+"2 : Do transactions\n"
		+"3 : Print Chain\n"
		+"4 : Validate Transaction\n"
		+"5 : Validate BlockChain\n"
		+"6 : Exit";

		n=7;
		while(n!=6)
		{
			System.out.print("Menu :\n"+menu+"Enter : ");
			n=sc.nextInt();
			switch(n)
			{
			case 1:
				System.out.println("Enter amount : ");
				int amount=sc.nextInt();
				surCoin.MakeCoin("goofy",amount);
				break;
			case 2:
				System.out.println("Enter sender name,reciever name and amount : ");
				String sender=sc.next();
				String reciever=sc.next();
				amount=sc.nextInt();
				surCoin.ValidateTransaction(sender,reciever,amount);
				break;
			case 3:
				surCoin.printList();
				break;
		 case 4:
		 		System.out.println("Enter Block no. : ");
		 		int i=sc.nextInt();
		 		surCoin.ValidateBlock(i);
		 		break;
		 case 5:
		 		System.out.println("iS BlockChain Valid : "+surCoin.isChainValid());
				break;
		 case 6:
		 		System.out.println("Thank You ");
				break;
		 default:
		 		System.out.println("Invalid Choice");
		}
	 }
	}

}
