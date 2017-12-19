package socketserver;

import java.io.IOException;
import java.io.InputStream;

public class Request {
	private InputStream input;
	private String uri;
	private boolean ispost;
	private String searchterm;
	private String searchquery;	

	public Request(InputStream input) {
		this.input = input;
	}

	public void parse() {
		// Read a set of characters from the socket
		StringBuffer request = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		try {
			i = input.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
//		System.out.print(request.toString());
		
		if(request.indexOf("POST")!=-1){
			System.out.println("fileContent : "+request);
			ispost = true;
			String[] posts = request.toString().split("\n");
			String RawSearchQuery = posts[posts.length-1];
			String SearchQuery = RawSearchQuery.substring(RawSearchQuery.indexOf("=")+1, RawSearchQuery.lastIndexOf("&"));
			System.out.println(SearchQuery);
			searchquery = SearchQuery;
//			int startbody = request.indexOf("WebKitFormBoundary");
//			int endbody = request.lastIndexOf("WebKitFormBoundary");
//			String postbody = request.substring(startbody, endbody);
//			String[] posts = postbody.split("\n");
//			String name = posts[6].substring(posts[6].indexOf("\"")+1, posts[6].lastIndexOf("\""));
//			//System.out.println(name);
//			//System.out.println(posts[8]);
//			searchterm = name;
//			searchquery = posts[8];
		}
		uri = parseUri(request.toString());
		System.out.println("uri is :" + uri);
	}

	private String parseUri(String requestString) {
		int index1, index2;
		index1 = requestString.indexOf(' ');
		if (index1 != -1) {
			index2 = requestString.indexOf(' ', index1 + 1);
			if (index2 > index1)
				return requestString.substring(index1 + 1, index2);
		}
		return null;
	}

	public String getUri() {
		return uri;
	}

	public boolean isIspost() {
		return ispost;
	}

	public void setIspost(boolean ispost) {
		this.ispost = ispost;
	}

	public String getSearchterm() {
		return searchterm;
	}

	public void setSearchterm(String searchterm) {
		this.searchterm = searchterm;
	}

	public String getSearchquery() {
		return searchquery;
	}

	public void setSearchquery(String searchquery) {
		this.searchquery = searchquery;
	}
}
