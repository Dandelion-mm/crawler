public class CrawlTaskThred extends Thread{
	private StringBuffer siteName;
	private StringBuffer confName;
	
	public CrawlTaskThread(String confName, String siteName){
		this.siteName = new StringBuffer(siteName);
		this.confName = new StringBuffer(confName);
	}
	
	/**
	 * クローラーを作成する
	 * @param confName 設定サイト
	 * @param siteName サイト名
	 * @return クローラーオブジェクト
	 */
	 public CrawlerService createCrawler(String confName, string siteName){
	 	CrawlerService crawlService = null;
	 	crawlService = new CrawlerService(confName, siteName);
	 	return crawlService;
	 }
	 
	 /**
	  * クローラーの作成
	  */
	  public void run(){
	  	try{
	  		CrawlerService crawlService = createCrawler(confName.toString(), siteName.toString());
	  		crawlService.execute();
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  }
}