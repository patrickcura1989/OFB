import java.io.File;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.auth.SPNegoSchemeFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Set;



public class tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
	    File file = new File("C:/Automation_Oracle/OFT/OracleFinancials/OracleFinancialsLibrary/jar/automation.jks");
	    System.setProperty("javax.net.ssl.trustStore", file.getAbsolutePath());
	    File config = new File ("E:/oats_automation_scott/OracleFinancialsLibrary/jar/login.config");
	    System.setProperty("java.security.auth.login.config", config.getAbsolutePath());
        System.setProperty("java.security.krb5.conf", "krb5.conf");
        System.setProperty("javax.security.auth.useSubjectCredsOnly", "false");
        System.setProperty("sun.security.krb5.debug", "true");

	    
	    String username = "wangsc";
	    String password = "Spirent818";
	    final String url = "https://app-layer-dirs-oracleebs-test.ds.acc.co.nz/d12fin/ACCINT_TOP/statements_ce_interface/inbound/" ;	    
	    
	    final tester t = new tester ();
	    Subject token = t.login(username, password).getSubject() ;	   
	    PrivilegedAction<String> sendAction_getDir = new PrivilegedAction<String>() {

            @Override
            public String run() {
                try {
                    System.out.println(t.call(url)) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
               return "" ;
            }
        };
        
        Subject.doAs(token, sendAction_getDir) ;
        
        PrivilegedAction<String> sendAction_getfile = new PrivilegedAction<String>() {

            @Override
            public String run() {
                try {
                    System.out.println(t.call(url + "/WPRem05Dec16.txt")) ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
               return "" ;
            }
        };
        
        Subject.doAs(token, sendAction_getfile) ;


	}
	
	public void httpReq (String user, String password, final String url){
		try {

            LoginContext loginCOntext = new LoginContext("KrbLogin", new KerberosCallBackHandler(user, password));
            loginCOntext.login();            

            PrivilegedAction<String> sendAction = new PrivilegedAction<String>() {

                @Override
                public String run() {
                    try {
                        System.out.println(call(url)) ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                   return "" ;
                }
            };

            Subject.doAs(loginCOntext.getSubject(), sendAction);

        } catch (LoginException le) {
            le.printStackTrace();            
        }
		
		
	}
	
	public LoginContext login (String user, String password) throws LoginException{
		try {

            LoginContext loginCOntext = new LoginContext("KrbLogin", new KerberosCallBackHandler(user, password));
            loginCOntext.login();            
            return loginCOntext ;
        } catch (LoginException le) {
            throw le;            
        }
	}

	
	public String call(String url) throws IOException {
        HttpClient httpclient = getHttpClient();        
        try {
            HttpUriRequest request = new HttpGet(url);        
        	return httpclient.execute(request, new BasicResponseHandler());
        } finally {        	        	
        }
    }

    private  HttpClient getHttpClient() {

        Credentials use_jaas_creds = new Credentials() {
            public String getPassword() {
                return null;
            }

            public Principal getUserPrincipal() {
                return null;
            }
        };

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(new AuthScope(null, -1, null), use_jaas_creds);
        Registry<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder.<AuthSchemeProvider>create().register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory(true)).build();
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultAuthSchemeRegistry(authSchemeRegistry).setDefaultCredentialsProvider(credsProvider).build();

        return httpclient;
    }

    class KerberosCallBackHandler implements CallbackHandler {

        private final String user;
        private final String password;

        public KerberosCallBackHandler(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {

            for (Callback callback : callbacks) {

                if (callback instanceof NameCallback) {
                    NameCallback nc = (NameCallback) callback;
                    nc.setName(user);
                } else if (callback instanceof PasswordCallback) {
                    PasswordCallback pc = (PasswordCallback) callback;
                    pc.setPassword(password.toCharArray());
                } else {
                    throw new UnsupportedCallbackException(callback, "Unknown Callback");
                }

            }
        }
    }


}
