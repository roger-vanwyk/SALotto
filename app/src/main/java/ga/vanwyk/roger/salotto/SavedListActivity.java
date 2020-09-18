package ga.vanwyk.roger.salotto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import androidx.appcompat.widget.Toolbar;
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
import java.util.ArrayList;
import java.util.HashMap;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class SavedListActivity extends AppCompatActivity {
	
	
	private Toolbar _toolbar;
	
	private ArrayList<HashMap<String, Object>> listmap = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear25;
	private LinearLayout linear31;
	private ListView listview1;
	private TextView text_json;
	private TextView text_add_new;
	
	private Intent i = new Intent();
	private SharedPreferences sp;
	private AlertDialog.Builder dialog;
	private Intent in = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.saved_list);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED
		|| ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear25 = (LinearLayout) findViewById(R.id.linear25);
		linear31 = (LinearLayout) findViewById(R.id.linear31);
		listview1 = (ListView) findViewById(R.id.listview1);
		text_json = (TextView) findViewById(R.id.text_json);
		text_add_new = (TextView) findViewById(R.id.text_add_new);
		sp = getSharedPreferences("sp", Activity.MODE_PRIVATE);
		dialog = new AlertDialog.Builder(this);
		
		listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				//What happens when item gets clicked
				QueryUtil.showMessage(getApplicationContext(), "Edit");
				sp.edit().putString("pos", String.valueOf((long)(_position))).commit();
				in.setClass(getApplicationContext(), SavedListActivity.class);
				startActivity(in);
			}
		});
		
		listview1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				//Setup what happens when long clicked on an item
				dialog.setTitle("Delete this item?");
				dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						listmap.remove((int)(_position));
						sp.edit().putString("allnotes", new Gson().toJson(listmap)).commit();
						((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
					}
				});
				dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface _dialog, int _which) {
						
					}
				});
				dialog.create().show();
				return true;
			}
		});
		
		text_json.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/myjsondata.json"), new Gson().toJson(listmap));
			}
		});
		
		text_add_new.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				//Setup what happens on button click
				i.setClass(getApplicationContext(), LottoActivity.class);
				startActivity(i);
			}
		});
	}
	private void initializeLogic() {
		setTitle("Lotto results");
		//What happens when activity becomes visible
		sp.edit().putString("pos", "").commit();
		if (!sp.getString("allnotes", "").equals("")) {
			listmap = new Gson().fromJson(sp.getString("allnotes", ""), new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
			listview1.setAdapter(new Listview1Adapter(listmap));
			((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
		}
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
		finish();
	}
	public class Listview1Adapter extends BaseAdapter {
		ArrayList<HashMap<String, Object>> _data;
		public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public int getCount() {
			return _data.size();
		}
		
		@Override
		public HashMap<String, Object> getItem(int _index) {
			return _data.get(_index);
		}
		
		@Override
		public long getItemId(int _index) {
			return _index;
		}
		@Override
		public View getView(final int _position, View _view, ViewGroup _viewGroup) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _view;
			if (_v == null) {
				_v = _inflater.inflate(R.layout.custom_view, null);
			}
			
			
			final LinearLayout linear3 = (LinearLayout) _v.findViewById(R.id.linear3);
			final LinearLayout linear1 = (LinearLayout) _v.findViewById(R.id.linear1);
			final LinearLayout linear2 = (LinearLayout) _v.findViewById(R.id.linear2);
			final TextView number_1 = (TextView) _v.findViewById(R.id.number_1);
			final TextView text_number_1 = (TextView) _v.findViewById(R.id.text_number_1);
			final TextView number_2 = (TextView) _v.findViewById(R.id.number_2);
			final TextView text_number_2 = (TextView) _v.findViewById(R.id.text_number_2);
			final TextView number_3 = (TextView) _v.findViewById(R.id.number_3);
			final TextView text_number_3 = (TextView) _v.findViewById(R.id.text_number_3);
			final TextView number_4 = (TextView) _v.findViewById(R.id.number_4);
			final TextView text_number_4 = (TextView) _v.findViewById(R.id.text_number_4);
			final TextView number_5 = (TextView) _v.findViewById(R.id.number_5);
			final TextView text_number_5 = (TextView) _v.findViewById(R.id.text_number_5);
			final TextView number_6 = (TextView) _v.findViewById(R.id.number_6);
			
			//Setup on bind custom view
			number_1.setText(listmap.get((int)_position).get("number_1").toString());
			number_2.setText(listmap.get((int)_position).get("number_2").toString());
			number_3.setText(listmap.get((int)_position).get("number_3").toString());
			number_4.setText(listmap.get((int)_position).get("number_4").toString());
			number_5.setText(listmap.get((int)_position).get("number_5").toString());
			number_6.setText(listmap.get((int)_position).get("number_6").toString());
			
			return _v;
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
