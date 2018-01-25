import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;
class BlockChain
{
  int index;
  List<Object> chain = new ArrayList<Object>();
  HashMap<String, DSA> dsa;
  BlockChain(HashMap<String, DSA> dsa)throws Exception
  {
    this.index=0;
    this.dsa=dsa;
    chain.add(createGenisis());
  }

  Block createGenisis()throws Exception
  {
    Block newBlock = new Block(index++,null,null,0,null,-1);
    newBlock.hash=newBlock.calculateHash();
    return newBlock;
  }

  Block getLatestBlock()throws Exception
  {
    return (Block)chain.get(chain.size()-1);
  }

  void addBlock(Block newBlock)throws Exception
  {
    newBlock.prevHash=getLatestBlock().hash;
    newBlock.hash=newBlock.calculateHash();
    chain.add(newBlock);
  }

  boolean isChainValid()throws Exception
  {
    for(int i= 1;i<chain.size();i++)
    {
      Block currentBlock=(Block)chain.get(i);
      Block prevBlock=(Block)chain.get(i-1);

      if(currentBlock.prevHash!=prevBlock.hash)
        return false;
    }
    return true;
  }


  void MakeCoin(String name,int money)throws Exception
  {
    if(name=="goofy")
     {
       DSA en=(DSA)dsa.get("goofy");
        addBlock(new Block(index++,en.pubKey,en.pubKey,money,en.sign(""+(index-1)+(en.pubKey).toString()+money),-1));
        System.out.println("Added");
     }
    else
      System.out.println(name+" is not the miner.");
  }


  void ValidateTransaction(String sender,String reciever,int amount)throws Exception
   {
     boolean ans;
     int flag;
     Block currentBlock;
     DSA en=(DSA)dsa.get(sender);
     DSA ef=(DSA)dsa.get(reciever);
     for(int i= chain.size()-1;i>=0;i--)
     {
       currentBlock=(Block)chain.get(i);
       if(currentBlock.reciever==en.pubKey)
         if(amount<=currentBlock.amount  && currentBlock.spent==false)
         {
           flag=0;
           while(currentBlock.index>0)
           {
            if(en.Verify(currentBlock.reciever,""+currentBlock.index+(currentBlock.reciever).toString()+currentBlock.amount,currentBlock.signature))
            {
             if(currentBlock.prevPointer!=-1)
                currentBlock=(Block)chain.get(currentBlock.prevPointer);
             else
                break;
            }
            else
            {
             flag=1;
             break;
            }
           }
           if(flag==0)
           {
             currentBlock=(Block)chain.get(i);
             currentBlock.spent=true;
             if(currentBlock.amount-amount>0)
                 addBlock(new Block(index++,en.pubKey,en.pubKey,currentBlock.amount-amount,en.sign(""+(index-1)+(en.pubKey).toString()+(currentBlock.amount-amount)),i));
             addBlock(new Block(index++,en.pubKey,ef.pubKey,amount,ef.sign(""+(index-1)+(ef.pubKey).toString()+(amount)),i));
             System.out.println("Transaction Done");
             return;
           }
         }
     }
     System.out.println(sender+" do not have that much money");
   }

   void ValidateBlock(int no)throws Exception
    {
      int flag=0;
      Block currentBlock;
      DSA en=new DSA();
      while(no!=-1)
      {
        currentBlock=(Block)chain.get(no);
        if(en.Verify(currentBlock.reciever,""+currentBlock.index+(currentBlock.reciever).toString()+currentBlock.amount,currentBlock.signature))
        {
          printBlock(currentBlock);
          no=currentBlock.prevPointer;
        }
        else
        {
          flag=1;
          break;
        }
      }
      if(flag==0)
        System.out.println("Valid");
      else
        System.out.println("NOT Valid");
    }



  void  printBlock(Block newBlock)throws Exception
  {
    System.out.println("{");
    System.out.println(" Index : "+newBlock.index);
    System.out.println(" sender's Public key : "+newBlock.sender);
    System.out.println(" reciever's Public Key : "+newBlock.reciever);
    System.out.println(" amount : "+newBlock.amount);
    System.out.println(" prevHash : "+newBlock.prevHash);
    System.out.println(" hash : "+newBlock.hash);
    System.out.println(" Signed : "+newBlock.signature);
    System.out.println(" prevPointer : "+newBlock.prevPointer);
    System.out.println(" Spent : "+newBlock.spent);
    System.out.println("}");
  }

  void printList()throws Exception
	{
		for(int i=0;i<chain.size();i++)
			{
				  Block newBlock = (Block)chain.get(i);
					System.out.println("{");
          System.out.println(" Index : "+newBlock.index);
					System.out.println(" sender's Public key : "+newBlock.sender);
					System.out.println(" reciever's Public Key : "+newBlock.reciever);
					System.out.println(" amount : "+newBlock.amount);
					System.out.println(" prevHash : "+newBlock.prevHash);
					System.out.println(" hash : "+newBlock.hash);
          System.out.println(" Signed : "+newBlock.signature);
          System.out.println(" prevPointer : "+newBlock.prevPointer);
          System.out.println(" Spent : "+newBlock.spent);
					System.out.println("}");
			}
	}
}
