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
public class ReadQuery implements Serializable, Read{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	public List<AccessControl> processFile(InputStream in) throws MatrixException {
		List<AccessControl>result = new ArrayList<AccessControl>();

		  Map<String,String> querys = new TreeMap<String,String>();
		  StringTokenizer st = null;
		try {
			final File tempFile = File.createTempFile("stream2file", ".tmp");
		    tempFile.deleteOnExit();

		    try (FileOutputStream out = new FileOutputStream(tempFile)) {
		        IOUtils.copy(in, out);
		    }
			BufferedReader br = new BufferedReader(new FileReader(tempFile));
			try {

				String strLine = "";
				
				// read comma separated file line by line
				while ((strLine = br.readLine()) != null) {
					// break comma separated line using ","
					st = new StringTokenizer(strLine, "\t, ");
					ArrayList<String> list = new ArrayList<String>();
				    int count=0;
				    String key="";
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						if (count==1 || count==2){
							 if (count==2){
								 key+="|";
							 }
							key+=token;
						}
						if (count==3){
							if (querys.containsKey(key)){
								token +=" "+querys.get(key);
							}
							querys.put(key, token);
						}
						++count;

					}

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AccessControl accessControl=null;
		for (Map.Entry<String, String> entry : querys.entrySet()) {
			accessControl = new AccessControl();
			 st = new StringTokenizer(entry.getKey(),"|");;
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
		Collections.sort(result);
		return result;
	}



	public Map<String,String> processFileMap(InputStream in) throws MatrixException {
		 Map<String,String> querys = new TreeMap<String,String>();
		  StringTokenizer st = null;
		try {
			final File tempFile = File.createTempFile("stream2file", ".tmp");
		    tempFile.deleteOnExit();

		    try (FileOutputStream out = new FileOutputStream(tempFile)) {
		        IOUtils.copy(in, out);
		    }
			BufferedReader br = new BufferedReader(new FileReader(tempFile));
			try {

				String strLine = "";
				
				// read comma separated file line by line
				while ((strLine = br.readLine()) != null) {
					// break comma separated line using ","
					st = new StringTokenizer(strLine, "\t, ");
					ArrayList<String> list = new ArrayList<String>();
				    int count=0;
				    String key="";
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						if (count==1 || count==2){
							 if (count==2){
								 key+="|";
							 }
							key+=token;
						}
						if (count==3){
							if (querys.containsKey(key)){
								token +=querys.get(key);
							}
							querys.put(key, token);
						}
						++count;

					}

				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return querys;
	}
	
	
	
}
