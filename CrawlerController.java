import java.util.List;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class CrawlerController{
	//.confファイルの読み込み
	final static Config config = ConfigFactory.load("spplication");
	final static List<String> confNameList = config.getStringList("ConfNameList");
	final static String path = "site/";
	
	public static void main(String[] args){
		for (String confName : confNameList){
			List<String> siteNameList = ConfigFactory.load(path + confName).getStringList("siteNameList");
			for(String siteName : siteNameList){
				CrawlTaskThread crawlTaskThread = new CrawlTaskThread(confName,siteName);
				crawlTaskThread.setName("thread-"+siteName);
				crawlTaskThread.start();
				System.out.println(siteName);
			}
		}
	}
}