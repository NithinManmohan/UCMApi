package com.ucm.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class GET_FILE {
	public static void main(String args[]) throws IOException {
		// create the IDC Client Manager manager
		IdcClientManager manager = new IdcClientManager();

		// build a client that will communicate using the HTTP protocol
		IdcClient idcClient;

		try {
			// context with ucm username and password
			IdcContext user = new IdcContext("weblogic", "weblogic");
			// ucm link
			idcClient = manager.createClient("idc://localhost:4444");

			// get the binder
			DataBinder binder = idcClient.createBinder();

			// populate the binder with the parameters
			binder.putLocal("IdcService", "GET_FILE");
			binder.putLocal("dDocName", "ED32");
			binder.putLocal("RevisionSelectionMethod", "Latest");

			try {
				ServiceResponse response = idcClient.sendRequest(user, binder);

				// get the response as a string
				InputStream stream = response.getResponseStream();
				// dispaly the content of the file
				OutputStream ostream = new FileOutputStream("C:\\testexample.txt");
				int read = 0;
				byte[] bytes = new byte[1024];
				while ((read = stream.read(bytes)) != -1) {
					ostream.write(bytes, 0, read);
				}

			} catch (IdcClientException e) {
				e.printStackTrace();
			}
		} catch (IdcClientException e) {
			e.printStackTrace();
		}
	}
}
