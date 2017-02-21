package com.example.pingpong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;

public class Home extends Activity implements OnClickListener {
	
	private Button startBtn;
	private Button settingsBtn;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        
        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
        
        settingsBtn = (Button) findViewById(R.id.settings_btn);
        settingsBtn.setOnClickListener(this);
        
    } 
    
    public void onClick(View v) {
    	if (v == startBtn){
    		startBtn.setBackgroundResource(R.drawable.startbutton_pushed);
    		startActivity(new Intent(Home.this, PingPong.class));
    		startBtn.setBackgroundResource(R.drawable.startbutton);
    	} else if (v == settingsBtn){
    		startActivity(new Intent(Home.this, PingPong.class));
    	} 
    }
//    public boolean onTouch(View v, MotionEvent event) {
//        if(event.getAction()==MotionEvent.ACTION_DOWN &&  v == startBtn)
//            startBtn.setBackgroundResource(R.drawable.startbutton_pushed);
//            startActivity(new Intent(Home.this, PingPong.class));
//        if(event.getAction()==MotionEvent.ACTION_UP &&  v == startBtn )
//        	 startBtn.setBackgroundResource(R.drawable.startbutton);        
//        return false;
//    }

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub	
//	}
}