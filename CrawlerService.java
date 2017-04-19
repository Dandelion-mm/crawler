import java.lo.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsorp;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerServce{
	
	/**
	 * 詳細ページのURL配列
	 */
	private List<String> detailPageUrls = new LinkedList<String>();
	
	/**
	 * サイトの情報を取得しておくオブジェクト
	 */
	private SiteData siteData = new SiteData();
	
	UbikeConfig siteConf;
	
	/**
	 * クローラーに設定情報を渡す
	 * @param sitename application.confに設定されているキーの値
	 */
	public CrawlerService(String confName, String siteName){
		this.siteConf = new UbikeConfig(confName,siteName);
	}
	
	/**
	 * HTMLを取得する
	 * @param URL取得したいHTMLドキュメントのURL
	 * @return 取得したいHTMLドキュメント
	 */
	 public Document documentGet(String URL){
	 	Document document = null;
	 	//BOT対策の対策
	 	Wait.waitSecond(5);
	 	try{
	 		document = Jsoup.connect(URL).timeout(30 * 1000).get();
	 	}catch(IOException e){
	 		e.printStackTrace();
	 	}
	 	return document;
	 }
	 
	 /**
	  * リストページのURLを取得
	  * @return リストページのURL
	  */
	  public String getNextListPageUrl(String currentListpageUrl){
	  	Document document = null;
	  	document = documentGet(currentListPageUrl);
	  	String nextListPageUrl = null;
	  	if(siteConf.getNextListPageSelectoe().isEmpty())
	  		return null;
	  	Elements elementDetail = document.select(siteConf.getNextListPageSelector());
	  	String nextListPagePath = elementDetail.attr("href");
	  	nextListPagePath = nextListPagePath.replaceFirst("\\./", "/");
	  	if(!(nextListPagePath.isEmpty())){
	  		nextListPageUrl = siteConf.getHomeUrl() + nextListPagePath;
	  	}
	  	return nextListPageUrl;
	  }
	  
	  /**
	   * 詳細ページのURLを取得
	   * @param listUrl リストページのURL
	   * @return 詳細ページのURLのリスト
	   */
	  public List<String> getDetailPageUrls(String listUrl){
	  	List<String> detailPageUrls = new LinkedList<String>();
	  	Document document = null;
	  	document = documentGet(listUrl);
	  	Elements elementsDetail = document.select(siteConf.getDetailPagePathSelector());
	  	
	  	for(Element elementDetail : elementsDetail){
	  		String detailPath = elementDetail.attr("href");
	  		detailPageUrls.add(siteConf.getHomeUrl() + detailPath);
	  	}
	  	return detailPageUrls;
	  }
	  
	  /**
	   * 詳細ページ内の情報の取得
	   * @param detailPageUrl 詳細ページのURL
	   */
	  public void getAllselector(String detailPageUrl){
	  	Document document = null;
	  	document = documentGet(detailPageUrl);
	  	
	  	//値が存在しない場合はnullを代入
	  	if(!siteConf.getTitle1Selector().isEmpty()){
	  		siteData.setTitle1(document.select(siteConf.getTitle1Selector()).text().toString());
	  	}else{
	  		siteData.setTitle1("null");
	  	}
	  	
	  	if(!siteConf.getContent1Selector().isEmpty()){
	  		siteData.setContent1(document.select(siteConf.getContent1Selector()).text().toString());
	  	}else{
	  		siteData.setContent1("null");
	  	}
	  	
	  	if(!siteConf.getLocationSelector().isEmpty()){
	  		siteData.setLocationSelector(document.select(siteConf.getLocationSelector()).text().toString());
	  	}else{
	  		siteData.setLocationSelector("null");
	  	}
	  	
	  	if(!siteConf.getPriceSelector().isEmpty()){
	  		siteData.setPriceSelector(document.select(siteConf.getPriceSelector()).text().toString());
	  	}else{
	  		siteData.setPriceSelector("null");
	  	}
	  }
	  
	  public synchronized void execute(){
	  	Syatem.out.println(siteConf.getSiteName() + "クローラー開始");
	  	for(String listPagePath : siteConf.getListPagePathList()){
	  		String currentListPageUrl = (siteConf.getHomeUrl() + listPagePath);
	  		
	  		do{
	  			System.out.println(currentListPageUrl);
	  			this.detailPageUrls = this.getDetailPageUrls(currentListPageUrl);
	  			for(String detailPageUrl : this.detailPageUrls){
	  				setAllSelector(detailPageUrl);
	  				System.out.println("--------------------start--------------------");
	  				System.out.println("detailPageUrl");
	  				System.out.println(siteData.getTitle1());
	  				System.out.println(siteData.getContent1());
	  				System.out.println(siteData.getLocationSelector());
	  				System.out.println(siteData.getPriceSelector());
	  				System.out.println("---------------------end---------------------");
	  			}
	  			currentListPageUrl = this.getNextListPageUrl(currentListPageUrl);
	  		}whilr(currentListPageUrl != null);
	  	}
	  	System.out.println(siteData.getSiteName() + "クローラー終了");
	  }
}