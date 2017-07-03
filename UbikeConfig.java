import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 共通のセレクター設定
 */
public class UbikeConfig{
    /** サイトの名前 */
    private String siteName;
    public String getSiteName(){
        return this.siteName;
    }

    /** ホームURL */
    private String homeUrl;
    public String getHomeUrl(){
        return this.homeUrl;
    }

    /** リストページのリスト */
    private List<String> listPagePathList;
    public List<String> getListPagePathList(){
        return this.listPagePathList;
    }

    /** 次のページへのセレクター */
    private String nextListPageSelector;
    public String getNextListPageSelector(){
        return this.nextListPageSelector;
    }

    /** 詳細ページへのセレクター */
    private String detailListPageSelector;
    public String getDetailListPageSelector(){
        return this.detailListPageSelector;
    }

    /** タイトル１ */
    private String title1Selector;
    public String getTitle1Selector(){
        return this.title1Selector;
    }

    /** コンテント１ */
    private String content1Selector;
    public String getContent1Selector(){
        return this.content1Selector;
    }

    /** 場所 */
    private String locationSelector;
    public String getLocationSelector(){
        return this.locationSelector;
    }

    /** 価格 */
    private String priceSelector;
    public String getPriceSelector(){
        return this.priceSelector;
    }

    public UbikeConfig(String confName.String siteName){
        Config config = ConfigFactory.load("site/"+confName).getConfig(siteName);
        this.siteName = config.getString("siteName");
        this.homeUrl = config.getString("homeUrl");
        this.listPagePathList = config.getStringList("listPagePath");
        this.nextListPageSelector = config.getString("nextListPageSelector");
        this.detailListPageSelector = config.getString("detailPageSelector");
        this.title1Selector = config.getString("title1Selector");
        this.content1Selector = config.getString("content1Selector");
        this.locationSelector = config.getString("locationSelector");
        this.priceSelector = config.getString("priceSelector")
    }
}
