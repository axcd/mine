package com.mao.mine;

import android.os.*;
import android.support.v7.app.*;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.app.*;
import android.content.*;
import java.util.*;
import java.lang.reflect.*;
import android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity
{
	public static boolean flag;
	public static MapView mv;
	
	public static int m = 22;
	public static int n = 12;
	public static int t = 1;
	
	private Spinner column;
	private Spinner row;
	private Spinner radom;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		mv = (MapView)findViewById(R.id.MapView);
		column = (Spinner)findViewById(R.id.column);
		row = (Spinner)findViewById(R.id.row);
		radom = (Spinner)findViewById(R.id.radom);
		
		setSpinner(column,12,40,"n");
		setSpinner(row,22,50, "m");
		setSpinner(radom,1,6, "t");
    }
	
	public void set(final Spinner spinner, final String type)
	{
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
			{
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) 
				{
					int tmp = Integer.parseInt(spinner.getSelectedItem().toString());
					if(type.equals("m")) m = tmp;
					else if(type.equals("n")) n = tmp;
					else if(type.equals("t")) t = tmp;
				}

				public void onNothingSelected(AdapterView<?> arg){
					;
				}
			});
	}

	public void setSpinner(Spinner spinner, int start, int end, String key)
	{
		ArrayList<String> al = new ArrayList<String>();
		for(int i=start;i<=end;i++)
		{
			al.add(i+"");
		}

		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,al);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		try {
			Field popup = Spinner.class.getDeclaredField("mPopup");
			popup.setAccessible(true);
			android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);
			popupWindow.setHeight(500);
		}
		catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
			
		}
		
		spinner.setAdapter(aa);
		set(spinner,key);
	}
	
	public void change(View view)
	{
		flag = !flag;
	}
	
	public void update(View view)
	{
		mv.update();
	}

}
