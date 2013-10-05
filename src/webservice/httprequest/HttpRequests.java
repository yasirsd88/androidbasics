package webservice.httprequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class HttpRequests {
	private static String WEBSERVICE_URL = "SERVICE URL";
	private HttpClient httpClient;
	private HttpPost postRequest;
	private int serverResponseCode = 0;
	private static String MGM_HEADER = "HEADER API KEY";
	private static String X_HTTP_METHOD_OVERRIDE = "X_HTTP_METHOD_OVERRIDE";
	private static String X_MGM_API_KEY = "HEADER KEY VALUE";

	public static String getWEBSERVICE_URL() {
		return WEBSERVICE_URL;
	}

	public static void setWEBSERVICE_URL(String wEBSERVICE_URL) {
		WEBSERVICE_URL = wEBSERVICE_URL;
	}

	public String GET(String url) {
		String HttpResponse = "";
		httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(WEBSERVICE_URL + url);
		try {
			getRequest.setHeader(MGM_HEADER, X_MGM_API_KEY);
			HttpResponse response = httpClient.execute(getRequest);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				HttpResponse += output;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpClient.getConnectionManager().shutdown();
		return HttpResponse;
	}

	public String GET_Override(String url) {
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(WEBSERVICE_URL + url);
		String HttpResponse = "";

		postRequest.setHeader(MGM_HEADER, X_MGM_API_KEY);
		postRequest.setHeader(X_HTTP_METHOD_OVERRIDE, "GET");
		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				HttpResponse += output;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpClient.getConnectionManager().shutdown();
		return HttpResponse;
	}

	public String POST(String url, Map<String, String> paramsMap) {
		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(WEBSERVICE_URL + url);
		String HttpResponse = "";
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(
				paramsMap.size());
		for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
			nameValuePair.add(new BasicNameValuePair(entry.getKey(), entry
					.getValue()));
		}
		try {
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePair));
			postRequest.setHeader(MGM_HEADER, X_MGM_API_KEY);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// Making HTTP Request
		try {
			HttpResponse response = httpClient.execute(postRequest);
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));
			String output;
			while ((output = br.readLine()) != null) {
				HttpResponse += output;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		httpClient.getConnectionManager().shutdown();
		return HttpResponse;
	}

	public String POST_With_File(String url, String filename,
			Map<String, String> paramsMap) {
		MultipartEntity reqEntity;
		File file;
		FileBody fileBody;
		String HttpResponse = "";

		httpClient = new DefaultHttpClient();
		postRequest = new HttpPost(WEBSERVICE_URL + url);
		// Indicate that this information comes in parts (text and file)
		reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

		file = new File(filename);
		fileBody = new FileBody(file, "images/jpeg");
		reqEntity.addPart("image", fileBody);

		try {
			for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
				reqEntity.addPart(entry.getKey(),
						new StringBody(entry.getValue()));
			}
			// Prepare to ship it!
			postRequest.setEntity(reqEntity);
			postRequest.setHeader(MGM_HEADER, X_MGM_API_KEY);
			HttpResponse response1 = httpClient.execute(postRequest);
			HttpEntity resEntity = response1.getEntity();
			HttpResponse = EntityUtils.toString(resEntity);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return HttpResponse;
	}

	public int POST_Image(String sourceFileUri, String Uri) {
		String upLoadServerUri = WEBSERVICE_URL + Uri;
		String fileName = sourceFileUri;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 2 * 1024 * 1024;
		File sourceFile = new File(sourceFileUri);
		if (!sourceFile.isFile()) {
			return 0;
		}
		try { // open a URL connection to the Servlet
			FileInputStream fileInputStream = new FileInputStream(sourceFile);
			URL url = new URL(upLoadServerUri);
			conn = (HttpURLConnection) url.openConnection(); // Open a HTTP
																// connection to
																// the URL
			conn.setDoInput(true); // Allow Inputs
			conn.setDoOutput(true); // Allow Outputs
			conn.setUseCaches(false); // Don't use a Cached Copy
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("ENCTYPE", "multipart/form-data");
			conn.setRequestProperty(MGM_HEADER, X_MGM_API_KEY);
			conn.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			conn.setRequestProperty("uploaded_file", fileName);
			dos = new DataOutputStream(conn.getOutputStream());

			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
					+ fileName + "\"" + lineEnd);
			dos.writeBytes(lineEnd);

			bytesAvailable = fileInputStream.available(); // create a buffer of
															// maximum size

			bufferSize = Math.min(bytesAvailable, maxBufferSize);
			buffer = new byte[bufferSize];

			// read file and write it into form...
			bytesRead = fileInputStream.read(buffer, 0, bufferSize);

			while (bytesRead > 0) {
				dos.write(buffer, 0, bufferSize);
				bytesAvailable = fileInputStream.available();
				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);
			}

			// send multipart form data necesssary after file data...
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

			// Responses from the server (code and message)
			serverResponseCode = conn.getResponseCode();

			String serverResponseMessage = conn.getResponseMessage();

			Log.e("uploadFile", "HTTP Response is : " + serverResponseMessage
					+ ": " + serverResponseCode);
			if (serverResponseCode == 200) {
				Log.e("success", "successd");
			}

			// close the streams //
			fileInputStream.close();
			dos.flush();
			dos.close();

		} catch (MalformedURLException ex) {
			Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
		} catch (Exception e) {
			Log.e("Upload file to server Exception",
					"Exception : " + e.getMessage(), e);
		}
		return serverResponseCode;
	}
}
