import oracle.stellent.ridc.IdcClient;
import oracle.stellent.ridc.IdcClientException;
import oracle.stellent.ridc.IdcClientManager;
import oracle.stellent.ridc.IdcContext;
import oracle.stellent.ridc.model.DataBinder;
import oracle.stellent.ridc.model.DataObject;
import oracle.stellent.ridc.model.DataResultSet;
import oracle.stellent.ridc.protocol.ServiceResponse;

public class SearchUCM {
	public static void main(String args[]) throws IdcClientException {

		IdcClientManager myIdcClientManager = new IdcClientManager();
		@SuppressWarnings("rawtypes")
		IdcClient myIdcClient = myIdcClientManager
				.createClient("idc://localhost:4444");
		IdcContext myIdcContext = new IdcContext("weblogic", "weblogic");
		ServiceResponse myServiceResponse = null;

		try {
			DataBinder myRequestDataBinder = myIdcClient.createBinder();
			myRequestDataBinder.putLocal("IdcService", "GET_SEARCH_RESULTS");
			myRequestDataBinder
					.putLocal("QueryText",
							"dDocTitle <matches> `test` <OR> dDocName <matches> `test`");
			myRequestDataBinder.putLocal("ResultCount", "400");
			myRequestDataBinder.putLocal("SortField", "dInDate");
			myRequestDataBinder.putLocal("SortOrder", "Desc");
			myServiceResponse = myIdcClient.sendRequest(myIdcContext,
					myRequestDataBinder);

			DataBinder myResponseDataBinder = myServiceResponse
					.getResponseAsBinder();
			DataResultSet myDataResultSet = myResponseDataBinder
					.getResultSet("SearchResults");
			int count = 0;
			System.out.println("Printing file details...");
			for (DataObject myDataObject : myDataResultSet.getRows()) {
				System.out.println("Id: " + myDataObject.get("dID"));
				System.out
						.println("ContentId: " + myDataObject.get("dDocName"));
				System.out.println("Title of content: "
						+ myDataObject.get("dDocTitle"));

				count = count + 1;
				System.out.println("\n");
			}
			System.out.println(count);
		} catch (IdcClientException idcce) {
			System.out
					.println("IDC Client Exception occurred. Unable to retrieve files list. Message: "
							+ idcce.getMessage() + ", Stack trace: ");
			idcce.printStackTrace();
		} catch (Exception e) {
			System.out
					.println("Exception occurred. Unable to retrieve files list. Message: "
							+ e.getMessage() + ", Stack trace: ");
			e.printStackTrace();
		} finally {
			if (myServiceResponse != null) {
				myServiceResponse.close();
			}
		}

	}
}
