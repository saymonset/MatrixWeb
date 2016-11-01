package com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.inject.Named;

import org.apache.commons.io.IOUtils;

import com.exception.MatrixException;

@Named
public class ReadMatrix implements Serializable, Read{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 
	
	public List<AccessControl> processFile(InputStream in) throws MatrixException {
		List<AccessControl>result = new ArrayList<AccessControl>();
		String[] objects=null;
		  Map<String,String> subjects = new TreeMap<String,String>();
		try {
		
			boolean isObject=true;
			boolean isSubject=true;
			final File tempFile = File.createTempFile("stream2file", ".tmp");
		    tempFile.deleteOnExit();

		    try (FileOutputStream out = new FileOutputStream(tempFile)) {
		        IOUtils.copy(in, out);
		    }

			BufferedReader br = new BufferedReader(new FileReader(tempFile));
			try {

				String strLine = "";
				StringTokenizer st = null;
				// read comma separated file line by line
				while ((strLine = br.readLine()) != null) {
					// break comma separated line using ","
					st = new StringTokenizer(strLine,"\t");
                    ArrayList<String> list= new ArrayList<String>();
                    isSubject=true;
                    String subjectKey="";
                    int objectNumber= 0;
					while (st.hasMoreTokens()) {
	                    String token = st.nextToken();
						
						if (isObject){
							list.add(token);	
						}else if (!isObject){
							if (isSubject){
								subjectKey=token;
								isSubject=false;
							}else{
								String privileges=token;
								//Alice Program1
								String key=subjectKey+"|"+objects[objectNumber++];
								subjects.put(key,privileges);
							}
						}

					}
					
					//first line are objects
					if (isObject){
						objects=list.toArray(new String[list.size()]);
					} 
					isObject=false;
				}
				
			
				AccessControl accessControl=null;
				for (Map.Entry<String, String> entry : subjects.entrySet()) {
					accessControl = new AccessControl();
					 st = new StringTokenizer(entry.getKey(),"|");;
					//String[] parts = entry.getKey().split("|");
					accessControl.setSubject(st.nextToken());
					accessControl.setObject(st.nextToken());
					List<String> privileges= new ArrayList<String>();
					for (int x=0;x<entry.getValue().length();x++){
						String priv=entry.getValue().charAt(x)+"";
						if (!" ".equalsIgnoreCase(priv)){
							privileges.add(priv);	
						}
						
					}
					Collections.sort(privileges);
					accessControl.setPrivileges(privileges);
					result.add(accessControl);					
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(result);
		return result;
	}



	public Map<String,String> processFileMap(InputStream in) throws MatrixException {
		 
		String[] objects=null;
		  Map<String,String> subjects = new TreeMap<String,String>();
		try {
		
			boolean isObject=true;
			boolean isSubject=true;
			final File tempFile = File.createTempFile("stream2file", ".tmp");
		    tempFile.deleteOnExit();

		    try (FileOutputStream out = new FileOutputStream(tempFile)) {
		        IOUtils.copy(in, out);
		    }

			BufferedReader br = new BufferedReader(new FileReader(tempFile));
			try {

				String strLine = "";
				StringTokenizer st = null;
				// read comma separated file line by line
				while ((strLine = br.readLine()) != null) {
					// break comma separated line using ","
					st = new StringTokenizer(strLine,"\t");
                    ArrayList<String> list= new ArrayList<String>();
                    isSubject=true;
                    String subjectKey="";
                    int objectNumber= 0;
					while (st.hasMoreTokens()) {
	                    String token = st.nextToken();
						
						if (isObject){
							list.add(token);	
						}else if (!isObject){
							if (isSubject){
								subjectKey=token;
								isSubject=false;
							}else{
								String privileges=token;
								//Alice Program1
								String key=subjectKey+"|"+objects[objectNumber++];
								subjects.put(key,privileges);
							}
						}
					}
					//first line are objects
					if (isObject){
						objects=list.toArray(new String[list.size()]);
					} 
					isObject=false;
				}
				
		 

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return subjects;
	 
	}
}
