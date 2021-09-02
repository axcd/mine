package com.mao.mine;
import android.view.*;
import android.content.*;
import android.util.*;
import android.graphics.*;

public class MapView extends ViewGroup
{
	private BlockView[][] blocks;
	private int width;
	private int height;
	private int margin;
	private int nos;
	private int landmines;
	private int column;
	private int row;
	private boolean gameover;
	//private int childCount;
	
	public MapView(Context context)
	{
		super(context);
	}
	
	public MapView(Context context, AttributeSet attr)
	{
		super(context, attr);
		init();
	}

	public void setGameover(boolean gameover)
	{
		this.gameover = gameover;
	}

	public boolean isGameover()
	{
		return gameover;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		//获取最大宽度和
		int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
		width = (maxWidth-margin)/column-margin;
		int cell = (maxWidth-margin)/12-margin;
		width = width<cell? cell:width;
		height = width;
		maxWidth = (margin+width)*column+margin;
		int maxHeight = (height+margin)*row+margin;

		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				final View child = blocks[i][j];
				int wm = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
				int hm = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
				//设置子类所需宽度和高度
				child.measure(wm, hm);
			}
		}

		// 设置容器所需的宽度和高度
		setMeasuredDimension(maxWidth, maxHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		for (int i = 0; i < row; i++)
		{
			for (int j = 0; j < column; j++)
			{
				View child = blocks[i][j];
				if (child.getVisibility() != View.GONE)
				{
					child.layout(j * (width + margin) + margin, i * (height + margin) + margin, (j +1)* (width + margin), (i + 1) * (height + margin));	
				}
			}
		}
	}
	
	//初始化
	public void init()
	{
		column = MainActivity.n;
		row = MainActivity.m;
		margin = 6;
		blocks = new BlockView[row][column];
		setGameover(false);
		
		getMines();
		setNumber();
		addListener();
		//childCount = getChildCount();
	}
	
	//获取雷以及数量
	public void getMines()
	{
		landmines = 0;
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				blocks[i][j] = (BlockView)LayoutInflater.from(this.getContext()).inflate(R.layout.view, null, false);
				blocks[i][j].setNum(0);
				blocks[i][j].setI(i);
				blocks[i][j].setJ(j);
				if(MainActivity.t+2 > Math.random()*30)
				{
					blocks[i][j].setNum(-1);
					landmines++;
				}
				this.addView(blocks[i][j]);
			}
		}	
	}
	
	//设置block的数字
	public void setNumber()
	{
		nos = row*column-landmines;
		
		int[][] z = {  
		    {-1,-1},{-1,0},{-1,1},
			{0,-1},         {0,1},
			{1,-1}, {1,0}, {1,1}};
		
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				if (blocks[i][j].getNum() == -1)
				{
					for (int k=0;k < z.length;k++)
					{
						int x = i + z[k][0];
						int y = j + z[k][1];

						if (x >= 0 && y >= 0 && y < column && x < row)
						{
							if (blocks[x][y].getNum() != -1)
								blocks[x][y].setNum(blocks[x][y].getNum() + 1);
						}
					}
				}
			}
		}
	}

	//增加监听
	public void addListener()
	{
		for(int i=0;i<row;i++)
		{
			for(int j=0;j<column;j++)
			{
				final BlockView bv = blocks[i][j];
				bv.setOnClickListener(new View.OnClickListener()
				{
						//private boolean flag = true;

						public void onClick(View view) 
						{
							int x = bv.getNum();

							//是否结束
							if (!isGameover())
							{
								//翻开方块
								if (!MainActivity.flag)
								{
									//判断是雷
									if (x==-1)
									{
										bv.setBackgroundResource(R.drawable.lei);
										//点击到雷结束
										setGameover(true);
										//对话框
										Intent intent = new Intent();
										intent.putExtra("result", "失败");
										intent.setClass(MapView.this.getContext(), DialogActivity.class);
										intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
										MapView.this.getContext().startActivity(intent);
									}else{
										open(bv);
									}
									//旗子
								}else{
									//放置旗子
									if(!bv.isFlag())
									{
										bv.setBackgroundResource(R.drawable.flag);
										landmines--;
										bv.setFlag(true);
										testResult();
									}
									//取消旗子
									else
									{
										bv.setBackgroundColor(Color.parseColor("#F0AAAAAA"));
										landmines++;
										bv.setFlag(false);
									}	
								}
							} 
						}
					});
			}
		}
	}

	public void open(BlockView bv)
	{
		int num = bv.getNum();
  
		bv.setTextColor(Color.parseColor("#FF0000FF"));
		bv.setBackgroundColor(Color.parseColor("#3000F0F0"));
		bv.setOnClickListener(null);
		bv.setOpened(true);
		
		nos--;
		if(bv.isFlag())landmines++;
		
		testResult();

		if (bv.getNum() == 0)
		{
			bv.setText("");
			discover(bv);
		}else{
			bv.setText(num+"");
		}
	}
	
	public void discover(BlockView bv)
	{
		int[][] z = {  
		    {-1,-1},{-1,0},{-1,1},
			{0,-1},         {0,1},
			{1,-1}, {1,0}, {1,1}};

		for(int i=0;i<z.length;i++)
		{
			int x = bv.getI()+z[i][0];
			int y = bv.getJ()+z[i][1];

			if(y>=0&&x>=0&&y<column&&x<row)
			{
				BlockView t = (BlockView)blocks[x][y];
				if(!t.isOpened())open(t);
			}
		}
	}

	public void testResult(){

		if (nos==0&&landmines==0)
		{
			//恭喜过关
			Intent intent = new Intent();
			intent.putExtra("result", "成功");
			intent.setClass(MapView.this.getContext(), DialogActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			this.getContext().startActivity(intent);
			if(MainActivity.t<10)MainActivity.t++;
		}
	}
	
	public void update()
	{
		removeAllViews();
		init();
		invalidate();
	}
	
}
