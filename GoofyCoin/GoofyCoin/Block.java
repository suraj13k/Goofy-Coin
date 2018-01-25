import java.io.*;
import java.net.*;
import java.util.*;
import java.security.*;
public class Block
{
  int index,amount,prevPointer;
  PublicKey sender,reciever;
  byte[] signature,hash,prevHash;
  boolean spent;
  Block(int index,PublicKey sender, PublicKey reciever,int amount,byte[] signature,int prevPointer)
  {
    this.index=index;
    this.sender=sender;
    this.reciever=reciever;
    this.amount=amount;
    this.signature=signature;
    this.prevHash=null;
    this.hash=null;
    this.prevPointer=prevPointer;
    this.spent=false;
  }
  byte[] calculateHash()throws Exception
  {
    MessageDigest digest = MessageDigest.getInstance("SHA-256");
    String data=""+index+amount+prevHash+signature;
	  byte[] hash = digest.digest(data.getBytes("UTF-8"));
    return hash;
  }
}
