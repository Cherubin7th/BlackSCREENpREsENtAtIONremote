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
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.util.Log;
 
public class WifiConnect implements Runnable{
	public static boolean connected=false;
    private String ip;
    private int port;
    private String out;
	public WifiConnect(String ipIn,int portIn,String outIn){
		ip=ipIn;
		port=portIn;
		out=outIn;
	}
	@Override
	public void run() {
		sendIt(ip,port,out);
		
	}
	
	private  String sendIt(String ipb,int portb,String in){
		try{
			Socket socket = new Socket(ipb, portb);
		//Send:
			PrintWriter printer = 
					new PrintWriter(
					 new BufferedWriter(
					  new OutputStreamWriter(
					    socket.getOutputStream()
						 )
						), 
					   true
					  );
			printer.println(in);
		//Receive reply
			BufferedReader inS = new BufferedReader(
					                      	new InputStreamReader(
					                      		socket.getInputStream()
					                      	  )
					                       );   
			
			String reply = inS.readLine();
			if(!socket.isClosed())socket.close();
			return reply;
		}catch(Exception e){
			Log.e("myLog", e.toString()); 
			return null;
		}	
	}


}