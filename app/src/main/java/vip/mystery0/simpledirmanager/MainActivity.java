package vip.mystery0.simpledirmanager;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.util.ArrayList;

import vip.mystery0.dirManager.DirManager;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DirManager dirManager = findViewById(R.id.dirManager);
		dirManager.setCurrentPath(Environment.getExternalStoragePublicDirectory(Environment.DIR_ANDROID));
		dirManager.setRootPath(Environment.getExternalStoragePublicDirectory(Environment.DIR_ANDROID));
		dirManager.setAdapter(new MyAdapter(new ArrayList<File>()));
	}
}
