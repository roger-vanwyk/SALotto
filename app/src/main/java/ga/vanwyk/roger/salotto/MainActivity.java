package ga.vanwyk.roger.salotto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private HashMap<String, Object> map = new HashMap<>();
	private double position = 0;
	private String numbers = "";
	private double click_exit = 0;
	private boolean bcv = false;
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear1;
	
	private SharedPreferences sp;
	private Intent i = new Intent();
	private AlertDialog.Builder dialog;
	private TimerTask TimerTask;
	private TimerTask t;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
	}
	private void initializeLogic() {
		/**
*Created by Roger Van Wyk on 27/07/2020;
*Generating random numbers for the SA lottery;
*/
		//Setup object animation
		PlanetView bcv = new PlanetView(this);
		linear1.addView(bcv);
		t = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						i.setClass(getApplicationContext(), LottoActivity.class);
						startActivity(i);
					}
				});
			}
		};
		_timer.schedule(t, (int)(1800));
		//Setup screen name
		setTitle("");
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		TimerTask = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						finish();
					}
				});
			}
		};
		_timer.schedule(TimerTask, (int)(1600));
	}
	
	@Override
	public void onBackPressed() {
		finishAffinity();
		int pid = android.os.Process.myPid();
		android.os.Process.killProcess(pid);
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
	private void _extra () {
		//Set up launcher screen animation
	} 
	
	final class PlanetView extends View { 
		
		private double angle=0;
		private Paint myPaint; 
		public PlanetView(Context context){ 
			super(context); 
			myPaint = new Paint(); 
			angle = 0; 
		} 
		
		@Override protected void onDraw(Canvas canvas) { 
			
			int viewWidth = this.getMeasuredWidth(); 
			int viewHeight = this.getMeasuredHeight(); angle = (angle + 0.001)%360; 
			
			float x = Math.round(260*Math.sin(Math.toDegrees(angle))); 
			float y = Math.round(110*Math.cos(Math.toDegrees(angle))); 
			float x2 = Math.round(120*Math.sin(90+Math.toDegrees(angle))); 
			float y2 = Math.round(290*Math.cos(90+Math.toDegrees(angle))); 
			float x3 = Math.round(130*Math.sin(180+Math.toDegrees(angle))); 
			float y3 = Math.round(230*Math.cos(180+Math.toDegrees(angle)));
			float x4 = Math.round(120*Math.sin(270+Math.toDegrees(angle))); 
			float y4 = Math.round(250*Math.cos(270+Math.toDegrees(angle)));
			
			
			 myPaint.setStyle(android.graphics.Paint.Style.FILL); 
			
			myPaint.setColor(Color.parseColor("#eceff1")); 
			canvas.drawCircle(viewWidth/2, viewHeight/2, (int)(x*1.5), myPaint);
			myPaint.setColor(Color.parseColor("#cfd8dc")); 
			canvas.drawCircle(viewWidth/2, viewHeight/2, (int)(x*1.5)-25, myPaint);
			
			myPaint.setColor(Color.parseColor("#275080")); canvas.drawCircle(viewWidth/2 + x, viewHeight/2 + y, 15, myPaint);
			
			myPaint.setColor(Color.parseColor("#275080")); canvas.drawCircle(viewWidth/2 + x2, viewHeight/2 + y2, 20, myPaint);
			
			myPaint.setColor(Color.parseColor("#275080")); canvas.drawCircle(viewWidth/2 + x3, viewHeight/2 + y3, 30, myPaint);
			
			myPaint.setColor(Color.parseColor("#275080")); canvas.drawCircle(viewWidth/2 + x4, viewHeight/2 + y4, 10, myPaint);
			
			
			
			android.graphics.drawable.Drawable d = getResources().getDrawable(R.drawable.earth, null);
			d.setBounds((int)((viewWidth/2)-80-x/11), (int)((viewHeight/2)-80-x/11), (int)((viewWidth/2)+80+x/11), (int)((viewHeight/2)+80+x/11)); 
			d.draw(canvas);
			
			
			invalidate(); 
			
		}
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
