package com.mao.mine;
import android.support.v7.app.*;
import android.os.*;
import java.util.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.view.*;

public class DialogActivity extends AppCompatActivity
{
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
		
		TextView tv = (TextView)findViewById(R.id.dialog);
		String result = getIntent().getStringExtra("result");
		
		tv.setText(result);
		if(result.equals("失败"))
			tv.setTextColor(Color.parseColor("#FFFF0000"));
		else 
			tv.setTextColor(Color.parseColor("#FF000FF0"));
    }

	@Override
	public void finish()
	{
		// TODO: Implement this method
		super.finish();
		MainActivity.mv.update();
	}

}
