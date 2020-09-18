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
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import com.google.gson.Gson;

public class LottoActivity extends AppCompatActivity {
	
	
	private double position = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private double generatedNumbers = 0;
	private String results = "";
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private LinearLayout linear_primary;
	private LinearLayout linear50;
	private LinearLayout divider1;
	private LinearLayout linear1;
	private LinearLayout divider3;
	private Button button_generate;
	private LinearLayout linear32;
	private LinearLayout divider4;
	private LinearLayout linear31;
	private LinearLayout divider5;
	private LinearLayout signature_box;
	private TextView textview34;
	private LinearLayout linear57;
	private LinearLayout linear_results;
	private LinearLayout divider2;
	private LinearLayout linear62;
	private ImageView imageview10;
	private TextView number_2;
	private TextView number_3;
	private TextView number_1;
	private TextView number_5;
	private TextView number_6;
	private TextView number_4;
	private ImageView imageview8;
	private Button button_save;
	private Button button_view_saved;
	private LinearLayout linear42;
	private LinearLayout linear33;
	private ImageView ic_logo;
	private ImageView imageview7;
	
	private SharedPreferences sp;
	private Intent i = new Intent();
	private AlertDialog.Builder dialog;
	private Intent intent = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.lotto);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear_primary = (LinearLayout) findViewById(R.id.linear_primary);
		linear50 = (LinearLayout) findViewById(R.id.linear50);
		divider1 = (LinearLayout) findViewById(R.id.divider1);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		divider3 = (LinearLayout) findViewById(R.id.divider3);
		button_generate = (Button) findViewById(R.id.button_generate);
		linear32 = (LinearLayout) findViewById(R.id.linear32);
		divider4 = (LinearLayout) findViewById(R.id.divider4);
		linear31 = (LinearLayout) findViewById(R.id.linear31);
		divider5 = (LinearLayout) findViewById(R.id.divider5);
		signature_box = (LinearLayout) findViewById(R.id.signature_box);
		textview34 = (TextView) findViewById(R.id.textview34);
		linear57 = (LinearLayout) findViewById(R.id.linear57);
		linear_results = (LinearLayout) findViewById(R.id.linear_results);
		divider2 = (LinearLayout) findViewById(R.id.divider2);
		linear62 = (LinearLayout) findViewById(R.id.linear62);
		imageview10 = (ImageView) findViewById(R.id.imageview10);
		number_2 = (TextView) findViewById(R.id.number_2);
		number_3 = (TextView) findViewById(R.id.number_3);
		number_1 = (TextView) findViewById(R.id.number_1);
		number_5 = (TextView) findViewById(R.id.number_5);
		number_6 = (TextView) findViewById(R.id.number_6);
		number_4 = (TextView) findViewById(R.id.number_4);
		imageview8 = (ImageView) findViewById(R.id.imageview8);
		button_save = (Button) findViewById(R.id.button_save);
		button_view_saved = (Button) findViewById(R.id.button_view_saved);
		linear42 = (LinearLayout) findViewById(R.id.linear42);
		linear33 = (LinearLayout) findViewById(R.id.linear33);
		ic_logo = (ImageView) findViewById(R.id.ic_logo);
		imageview7 = (ImageView) findViewById(R.id.imageview7);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		
		button_generate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//Setup algorythm to generate six random numbers
				if (!number_1.getText().toString().concat(number_2.getText().toString().concat(number_3.getText().toString().concat(number_4.getText().toString().concat(number_5.getText().toString().concat(number_6.getText().toString()))))).equals(number_1.getText().toString().concat(number_2.getText().toString().concat(number_3.getText().toString().concat(number_4.getText().toString().concat(number_5.getText().toString().concat(number_6.getText().toString()))))))) {
					_lottoNumbers();
				}
				else {
					//Trying to make the six generated numbers unique
					if (number_1.getText().toString().concat(number_2.getText().toString().concat(number_3.getText().toString().concat(number_4.getText().toString().concat(number_5.getText().toString().concat(number_6.getText().toString()))))).contains(number_1.getText().toString().concat(number_2.getText().toString().concat(number_3.getText().toString().concat(number_4.getText().toString().concat(number_5.getText().toString().concat(number_6.getText().toString()))))))) {
						_lottoNumbers();
					}
				}
			}
		});
		
		button_save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//Setup save data on button click
				if (position == 0) {
					map = new HashMap<>();
					map.put("#1", number_1.getText().toString());
					map.put("#2", number_2.getText().toString());
					map.put("#3", number_3.getText().toString());
					map.put("#4", number_4.getText().toString());
					map.put("#5", number_5.getText().toString());
					map.put("#6", number_6.getText().toString());
					listmap.add(map);
				}
				else {
					listmap.get((int)position).put("#1", number_1.getText().toString());
					listmap.get((int)position).put("#2", number_2.getText().toString());
					listmap.get((int)position).put("#3", number_3.getText().toString());
					listmap.get((int)position).put("#4", number_4.getText().toString());
					listmap.get((int)position).put("#5", number_5.getText().toString());
					listmap.get((int)position).put("#6", number_6.getText().toString());
				}
				sp.edit().putString("allnotes", new Gson().toJson(listmap)).commit();
				QueryUtil.showMessage(getApplicationContext(), "Saved!");
			}
		});
		
		button_view_saved.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//Setup button on click event
				i.setClass(getApplicationContext(), SavedListActivity.class);
				startActivity(i);
			}
		});
		
		imageview7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//Open url to dev's play store profile
				i.setAction(Intent.ACTION_VIEW);
				i.setData(Uri.parse("https://play.google.com/store/apps/dev?id=6776131537023742340"));
				startActivity(i);
			}
		});
	}
	private void initializeLogic() {
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
	public void onBackPressed() {
		dialog.setTitle("Exit?");
		dialog.setMessage("Are you sure you want to quit application?");
		dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
				int pid = android.os.Process.myPid();
				android.os.Process.killProcess(pid);
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				startActivity(intent);
			}
		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				i.setClass(getApplicationContext(), LottoActivity.class);
				startActivity(i);
			}
		});
		dialog.create().show();
	}
	private void _lottoNumbers () {
		//Generating six rows of random numbers between 1 and 52
		number_1.setText(results.substring((int)(generatedNumbers), (int)(QueryUtil.getRandom((int)(1), (int)(52)))));
		number_2.setText(results.substring((int)(generatedNumbers), (int)(QueryUtil.getRandom((int)(1), (int)(52)))));
		number_3.setText(results.substring((int)(generatedNumbers), (int)(QueryUtil.getRandom((int)(1), (int)(52)))));
		number_4.setText(results.substring((int)(generatedNumbers), (int)(QueryUtil.getRandom((int)(1), (int)(52)))));
		number_5.setText(results.substring((int)(generatedNumbers), (int)(QueryUtil.getRandom((int)(1), (int)(52)))));
		number_6.setText(results.substring((int)(generatedNumbers), (int)(QueryUtil.getRandom((int)(1), (int)(52)))));
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
