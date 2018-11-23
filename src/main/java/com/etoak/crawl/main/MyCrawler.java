package com.etoak.crawl.main;

import com.etoak.crawl.link.LinkFilter;
import com.etoak.crawl.link.Links;
import com.etoak.crawl.page.Page;
import com.etoak.crawl.page.PageParserTool;
import com.etoak.crawl.page.RequestAndResponseTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyCrawler {

    /**
     * 使用种子初始化 URL 队列
     *
     * @param seeds 种子 URL
     * @return
     */
    private void initCrawlerWithSeeds(final List<String> seeds) {
        for (String seed : seeds){
            Links.addUnvisitedUrlQueue(seed);
        }
    }


    /**
     * 抓取过程
     *
     * @param seeds
     * @return
     */
    public void crawling(List<String> seeds) {

        //初始化 URL 队列
        initCrawlerWithSeeds(seeds);

        //定义过滤器，提取以 http://www.baidu.com 开头的链接
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("www.ygdy8.com"))
                    return true;
                else
                    return false;
            }
        };

        //循环条件：待抓取的链接不空且抓取的网页不多于 5000
        while (!Links.unVisitedUrlQueueIsEmpty()  && Links.getVisitedUrlNum() <= 5000) {

            //先从待访问的序列中取出第一个；
            String visitUrl = (String) Links.removeHeadOfUnVisitedUrlQueue();
            if (visitUrl == null){
                continue;
            }

            //根据URL得到page;
            Page page = RequestAndResponseTool.sendRequstAndGetResponse(visitUrl);
            if(page == null){
                continue;
            }
            //对page进行处理： 访问DOM的某个标签
//            Elements es = PageParserTool.select(page,"a[class=ulink]");
//            if(!es.isEmpty()){
//                System.out.println("下面将打印所有a标签： ");
//                System.out.println(es);
//            }
            //将保存文件
            //FileTool.saveToLocal(page);

            //将已经访问过的链接放入已访问的链接中；
            Links.addVisitedUrlSet(visitUrl);

            //得到超链接
            Set<String> links = PageParserTool.getLinks(page,"a[class=ulink]");
            for (String link : links) {
                Links.addUnvisitedUrlQueue(link);
//                System.out.println("分区地址: " + link);
            }
            List<String> es = PageParserTool.getAttrs(page,"td[bgcolor=#fdfddf]>a","href");
            if(es != null){
                for(String e : es) {
                    System.out.println(e);
                }
            }
        }
    }


    //main 方法入口
    //https://www.dytt8.net/html/gndy/dyzz/list_23_1.html
    public static void main(String[] args) {
        MyCrawler crawler = new MyCrawler();
        List<String> urls = new ArrayList<String>();
        for(int i=0;i<150;i++) {
            urls.add("www.dytt8.net/html/gndy/dyzz/list_23_" + (i + 1) + ".html");
        }
        crawler.crawling(urls);
    }

}
