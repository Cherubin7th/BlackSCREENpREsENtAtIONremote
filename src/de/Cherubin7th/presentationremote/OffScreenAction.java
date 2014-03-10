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


import android.content.Context;
import android.media.AudioManager;
//import android.util.Log;
 
public class OffScreenAction {
    private AudioManager audi;
    private Thread th;
    private long time;
    private int port;
    private static  boolean  run=false;
	public OffScreenAction(Context context,String portIn){
		audi=(AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		port=Integer.parseInt(portIn);
		time=System.currentTimeMillis();
		//-------------------------------
		th=new Thread(new Runnable() { 
            public void run(){
            	/*To get the port the server uses, it will block as long as
            	the server did not answer*/
    //        	int port = WifiConnect.getPort(MainActivity.ip); 

            	
        
            	//Confirms that this app is now starting
            	send(port,OffScreenAction.CONFIRM);
            	while(run){
            		
            		if(System.currentTimeMillis()<time+250){
            			audi.setStreamVolume(AudioManager.STREAM_MUSIC, 2, 0);
            		}
            		
            		
            			if(audi.getStreamVolume(AudioManager.STREAM_MUSIC)<2){
            				send(port,OffScreenAction.LASTPAGE);
            				audi.setStreamVolume(AudioManager.STREAM_MUSIC, 2, 0);
            				time=System.currentTimeMillis();
            			}else
            			if(audi.getStreamVolume(AudioManager.STREAM_MUSIC)>2){
            				send(port,OffScreenAction.NEXTPAGE);
            				audi.setStreamVolume(AudioManager.STREAM_MUSIC, 2, 0);
            				time=System.currentTimeMillis();
            			}
            		
            		
            		try{if(run)Thread.sleep(200);}catch(Exception e){}//Pause
            	}
            	send(port,OffScreenAction.STOP_INTERACTION);
            	
            }
        }
		);
	}
	
	
	
	public void start(){
		//Log.i("myLog", "Start OffScreenAction");
		run=true;//Allow to run
		th.start();
	}
	
	
//-----Send-----------------------------------------------------------------------------
	private static String NEXTPAGE          = "351827_COM_NP";
	private static String LASTPAGE          = "351827_COM_LP";
//	private static String ASK               = "351827INI_ASK";
//	private static String STEP2             = "351827INI_ST2";
	private static String CONFIRM           = "351827INI_CMF";
	private static String STOP_INTERACTION  = "351827_C_STOP";
	
	public void send(int port,String in){
		try{ 
		Thread tr=new Thread(new WifiConnect(MainActivity.ip,port,in));
		//Log.i("myLog", "Try to send: "+ in);
		tr.start();
		}catch(Exception e){
			//Log.i("myLog", "SendError: "+ e.toString());
			}
	}
	
	public static void kill(){
		run=false;	
		
	}
	

}