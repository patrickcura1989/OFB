import java.util.concurrent.TimeUnit;

import oracle.oats.scripting.modules.basic.api.IteratingVUserScript;
import oracle.oats.scripting.modules.basic.api.Logger;
import oracle.oats.scripting.modules.basic.api.exceptions.AbstractScriptException;
import oracle.oats.scripting.modules.formsFT.common.api.elements.AbstractWindow;
public class Wait{

	private Condition condition;
	
	private long timeout ;		
	private long interval ;	
	
    private AbstractWindow win;
    
    private boolean status ;
    
    private static IteratingVUserScript script ;
    
    private static Logger logger ;
    
	
	private Wait (){
		
	}
	
	public static Wait await (IteratingVUserScript script){		
		logger = script.getLogger() ;
		return new Wait();
	}
	
	public Wait window (AbstractWindow win){
		this.win = win ;
		return this ;
	}
	
	public Wait atMost (long timeout){
	    this.timeout = timeout;	    
	    return this ;
	}
	
	public Wait Condition (Condition condition){
		this.condition = condition ;
		return this ;
	}
	
	public Wait interval (long interval){
		this.interval = interval ;
		return this ;
	}
	
	public Wait untilExists () throws AbstractScriptException {
		logger.info("untilExists running") ;
	    if (!wait (true)) throw new TimeOutException();
	    return this ;
	}
	
	public Wait untilNotExists () throws AbstractScriptException {
		logger.info("untilNotExists running") ;
	   	if (!wait(false)) throw new TimeOutException();
	   	return this ;
	}
	
	public void waitForConditionReady (){
		  logger.info("waitForConditionReady running") ;
		  if (condition == null) throw new NullPointerException("Condition is null");
		  boolean cur = false ;
		  long timeoutExpiredMs = System.currentTimeMillis()  + timeout;
		  while (!(cur = condition.isReady()) && System.currentTimeMillis() < timeoutExpiredMs) {		   
			   sleep(interval);		   	   
			   logger.info("current wait time : " + timeout) ;
		   }
		  if (!cur) throw new TimeOutException();
		  logger.info("waitForConditionReady completed") ;
	}
	
	private boolean wait (boolean status) throws AbstractScriptException{
	   boolean cur = !status ; 	   
	   long timeoutExpiredMs = System.currentTimeMillis()  + timeout;
	   while ((cur = win.isShowing()) != status && System.currentTimeMillis() < timeoutExpiredMs) {		   
		   sleep(interval);		   	   
		   logger.info("current wait time : " + timeout) ;
	   }
	   return cur == status ;
	}
	
	private void sleep (long sleep){
	   try {
		  TimeUnit.MILLISECONDS.sleep(sleep) ;
	   } catch (Exception e) {}
	}
	
}
