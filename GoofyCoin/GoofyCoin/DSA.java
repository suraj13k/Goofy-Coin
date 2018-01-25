import java.io.*;
import java.security.*;
class DSA
{
	KeyPairGenerator keyGen;
	SecureRandom secRandom;
	KeyPair keyPair;
	PublicKey pubKey;
	PrivateKey privKey;
	Signature dsaSign;
	DSA()throws Exception
	{
		keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		secRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
		keyGen.initialize(512, secRandom);
		keyPair = keyGen.generateKeyPair();
		privKey = keyPair.getPrivate();
		pubKey = keyPair.getPublic();
		dsaSign = Signature.getInstance("SHA1withDSA", "SUN");
	}


	public byte[] sign(String message)throws Exception
	{
		dsaSign.initSign(privKey);
		dsaSign.update(message.getBytes());
		byte[] realSign = dsaSign.sign();
		return realSign;
	}

	public boolean Verify(PublicKey pubKey,String message,byte[] sign)throws Exception
	{
		dsaSign.initVerify(pubKey);
		dsaSign.update(message.getBytes());
		boolean verifies = dsaSign.verify(sign);
		return verifies;
	}
}
