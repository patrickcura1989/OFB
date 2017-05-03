import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.WebProxy;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.misc.TraceFlags;
import microsoft.exchange.webservices.data.core.enumeration.notification.EventType;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.credential.WebProxyCredentials;
import microsoft.exchange.webservices.data.notification.*;
import microsoft.exchange.webservices.data.property.complex.EmailAddress;
import microsoft.exchange.webservices.data.property.complex.FolderId;

public class EmailClient {
	private String user ;
	private String pwd ;
	private String url ;
	
	private  ExchangeService service ;
	
	private StreamingSubscriptionConnection con ;
	
	private StreamingSubscription streamingsubscription ;
	
	private static final int INTERVAL = 10 ; ;
	
	public EmailClient (String user , String pwd, String url) throws Exception{
		this.user = user ;
		this.pwd = pwd;
		this.url = url ;
		connect () ;
	}
  
	
	private void connect () throws Exception{		
		service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(user, pwd);

        service.setCredentials(credentials);     
        service.setUrl(new java.net.URI(
        	url));
	}
	
	public Queue<String> getEmails (int timeout, int expectCount){
		try {
			 Folder inbox = Folder.bind(service, WellKnownFolderName.Inbox);
	         List<FolderId> folders = new ArrayList<FolderId>() ;
	         folders.add(inbox.getId()) ;
	         streamingsubscription = service.subscribeToStreamingNotifications(folders, EventType.NewMail);
	         con = new StreamingSubscriptionConnection(service, 1);
	         con.addSubscription(streamingsubscription);
	         Queue<String> mailQueue = new LinkedBlockingQueue<String>() ;
	         INotificationEventDelegateImp delegateImp = new INotificationEventDelegateImp (
	        	 streamingsubscription, mailQueue);
	         con.addOnNotificationEvent(delegateImp);
	         con.open();
	         while (mailQueue.size() < expectCount && timeout > 0) {
	        	 Common.sleep(INTERVAL, TimeUnit.SECONDS) ;
	         }
	         return mailQueue ;
		} catch (Exception e) {
			e.printStackTrace() ;
			 return new LinkedBlockingQueue<String>() ;
		} finally {
			 close () ;
		}
	}

	public void close (){
		try {
			con.close() ;
			con.clearNotificationEvent();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			service.close();
		}		
	}
	
	private  class INotificationEventDelegateImp implements  StreamingSubscriptionConnection.INotificationEventDelegate {

		private StreamingSubscription subscription ;
		private Queue<String> mailQueue ;
		public INotificationEventDelegateImp (StreamingSubscription subscription , Queue<String> mailQueue){
			this.subscription = subscription ;
			this.mailQueue = mailQueue ;
		}
		
		@Override 
		public void notificationEventDelegate(Object o, NotificationEventArgs notificationEventArgs) {		
			for (NotificationEvent event : notificationEventArgs.getEvents()) {
				  if (event.getEventType() != EventType.NewMail) continue;				  
				 try {
					 Item item = Item.bind(subscription.getService(),((ItemEvent)event).getItemId()) ;
	                 item.load();
	                 String content = item.getSubject().toLowerCase() + " : " 
	                  + item.getBody().toString().toLowerCase() ;
	                 
	                 mailQueue.offer(content);
				 } catch (Exception e) {
					 
				 }
			}
		}		
	}

  
}
