package ro.ionescu.radu;

import com.sun.org.apache.bcel.internal.classfile.Utility;

public class HashValue implements Cloneable
{
	private byte[] hash;
	private String algorithm;

	public HashValue(byte[] hash, String algorithm)
	{
		this.algorithm = algorithm;
		this.hash = hash.clone();
	}

	@Override
	public String toString()
	{
		return "The hash value is " + Utility.toHexString(this.hash) + " and the function is " + this.algorithm;
	}

	public byte[] getHash()
	{
		return hash;
	}

	public String getAlgorithm()
	{
		return algorithm;
	}

	@Override
	protected Object clone()
	{
		HashValue copy = new HashValue(this.hash,this.algorithm);
		return copy;
	}
}
