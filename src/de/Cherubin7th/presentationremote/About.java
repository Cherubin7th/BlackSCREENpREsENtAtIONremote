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

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.Cherubin7th.blackscreenpresentationremote.R;
 
public class About extends Activity{


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_layout);
		setLicense();
	}
	
	private void setLicense(){
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		InputStream inputStream = getResources().openRawResource(R.raw.info);
	    
		int i;
	    try {
	    	i = inputStream.read();
	    	while (i != -1)
	    	{
	    		byteArrayOutputStream.write(i);
	    		i = inputStream.read();
	    	}
	    	inputStream.close();
	    	} catch (IOException e) {
	    		//Log.e("myLog", e.toString());
	    	}
	    TextView txt=(TextView)findViewById(R.id.licenseView);
	    txt.setText(byteArrayOutputStream.toString());
	}
	
	
public void saveSettings(View w){
	if(w.getId() == R.id.buttonsettings){
		SharedPreferences settings = getSharedPreferences("Settings",0);
		SharedPreferences.Editor editt=settings.edit();
		EditText editTextt=(EditText)findViewById(R.id.portsettings);
		editt.putString("PortSettings", editTextt.getText().toString());	
		editt.commit();
		editTextt=null;
	}
}
	
public void onContact(View w){
	Intent out=new Intent(Intent.ACTION_SEND);
	
	out.putExtra(Intent.EXTRA_EMAIL,new String[]{ "Cherubin7th@yahoo.com"});
	out.putExtra(Intent.EXTRA_SUBJECT, "BlackScreen");
	out.setType("text/plain");
	startActivity(Intent.createChooser(out, "Send Email"));
	
}


public void onResume(){
	super.onResume();
	SharedPreferences settings = getSharedPreferences("Settings",0);
	EditText editTextt=(EditText)findViewById(R.id.portsettings);
	editTextt.setText(settings.getString("PortSettings", "11110"));
	editTextt=null;
}
	

	

}