/*
    Copyright (C) 2014  André Horst Lippok (Cherubin7th@yahoo.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.Cherubin7th.presentationremote;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import de.Cherubin7th.blackscreenpresentationremote.R;
 
public class MainActivity extends Activity {
	private MediaPlayer mp;
	private OffScreenAction offS;//Hier ist der "VolumeButtonListener" um die Lauter/Leiser Tasten zu hören
	//auch bei ausgeschaltetem Bildschirm.
	public static String ip;
	private boolean isRunning=false;
	private EditText editTextt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		mp=MediaPlayer.create(this, R.raw.dummymusic);//The MediaPlayer gets a mp3 that has no sound
		mp.setLooping(true); //The let this app work as long as the user wants
		//Get saved ip if exists so that the user dosnt has to retype everything
		SharedPreferences settings = getSharedPreferences("Settings",0);
		String ipaddr=settings.getString("IpSettings", "---.---.--.--");
		 editTextt=(EditText)findViewById(R.id.editIp);
		 editTextt.setText(ipaddr);
		//Log.i("myLog", "init finish");
	//	mp.setWakeMode(this, PowerManager.PARTIAL_WAKE_LOCK);
		
	}
    
	
	public void onStart(View w){
		if(w.getId() == R.id.start1){
			EditText e1=(EditText)findViewById(R.id.editIp);
			ip=e1.getText().toString();
			
			SharedPreferences settings = getSharedPreferences("Settings",0);
		
			if(!isRunning){
				//Save the ip address so that the user dosnt has to retype every time
				
				SharedPreferences.Editor editt=settings.edit();
				editt.putString("IpSettings", editTextt.getText().toString());	
				editt.commit();
				
				mp.start();
				isRunning=true;
				((Button)w).setText("Stop");
				offS=new OffScreenAction(this,settings.getString("PortSettings", "11110"));
				offS.start();
			//	Log.i("myLog", "clicked start.");
			}
			else{
				isRunning=false;
				OffScreenAction.kill();
				mp.pause();
				((Button)w).setText("Start");
			//	Log.i("myLog", "clicked stop");
			}
		}
	}
	
	public void onDestroy(){
		mp.stop();
		mp.release();
		OffScreenAction.kill();
		mp=null;
		super.onDestroy();
		
	}
	
	public void onAbout(View in){
		if(in.getId() == R.id.about){
			Intent intent=new Intent(this,About.class);
			startActivity(intent);
		}
	}
	
	//To block the VolumeKeys when the OffScreenAction Backgroundthread is not running
	public boolean dispatchKeyEvent(KeyEvent ev){
		if(!isRunning&&(KeyEvent.KEYCODE_VOLUME_UP == ev.getKeyCode()|| KeyEvent.KEYCODE_VOLUME_DOWN==ev.getKeyCode())){
			return true;//Blockes while it is not Running(isRunning=flase)
		}
		return super.dispatchKeyEvent(ev);
	}
	
	
	
	
}