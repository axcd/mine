package com.mao.mine;
import android.widget.*;
import android.content.*;
import android.util.*;

public class BlockView extends TextView
{
	private int num;
	private int i;
	private int j;
	private boolean opened;
	private boolean flag;
	
	public BlockView(Context context)
	{
		super(context);
	}
	
	public BlockView(Context context,AttributeSet attr)
	{
		super(context,attr);
	}

	public void setFlag(boolean flag)
	{
		this.flag = flag;
	}

	public boolean isFlag()
	{
		return flag;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public int getNum()
	{
		return num;
	}

	public void setI(int i)
	{
		this.i = i;
	}

	public int getI()
	{
		return i;
	}

	public void setJ(int j)
	{
		this.j = j;
	}

	public int getJ()
	{
		return j;
	}

	public void setOpened(boolean opened)
	{
		this.opened = opened;
	}

	public boolean isOpened()
	{
		return opened;
	}
}
