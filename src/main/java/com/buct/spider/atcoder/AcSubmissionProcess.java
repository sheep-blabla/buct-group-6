package com.buct.spider.atcoder;

import com.buct.spider.entity.Acrating;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;


@Component
public class AcSubmissionProcess implements PageProcessor {


    private static final Site SITE = Site.me()
            // 设置字符编码集
            .setCharset("UTF-8")
            // 设置Http连接重试次数
            .setRetryTimes(30)
            // 设置线程休眠时间
            .setSleepTime(1000)
            .addCookie("_ga_RC512FD18N", "GS1.1.1689242151.16.1.1689245038.0.0.0")
            .addCookie("_ga", "GA1.1.482933223.1688819308")
            .addCookie("REVEL_SESSION", "40c7841a1995e8a5b942a130590664b8128d3fed-%00_TS%3A1704797037%00%00SessionKey%3A9f15cf5380976e9cfbf43b10efdc05dde1a519e3cdbf22033cefa3a140aa7d7bb72210b48e5efd-ae5cc835ea1d3d325575c5cfaaafda3138b41b8516e50a9a0d5e6ad685d98bc1%00%00UserScreenName%3AZXhope%00%00UserName%3AZXhope%00%00a%3Afalse%00%00w%3Afalse%00%00csrf_token%3ACP1iMzN0TL8IHazF3gCbKoJfmkq0WUFM2oKbETZrGj4%3D%00");

    @Override
    public void process(Page page) {
        Selectable table = page.getHtml().xpath("//*[@id=\"main-container\"]/div[1]/div[3]/div[1]/div[2]/table");
        //    System.out.println(page.getHtml());
        List<String> tr = table.css("tbody > tr").all();

        int acSum = 0;
        String contestName = page.getHtml().xpath("/html/body/div[3]/nav/div/div[2]/ul[1]/li/a/text()").get();
        //  System.out.println("===============================>>>>>>>>>>>"+contestName);
        String userId = page.getHtml().xpath("/html/body/div[3]/div/div[1]/div[3]/div[1]/div[2]/table/tbody/tr[1]/td[3]/a[1]/text()").get();
        if (userId == null) userId = page.getHtml().xpath("//*[@id=\"input-user\"]/@value").get();
        //    System.out.println("===============================>>>>>>>>>>>"+userId);

        for (int i = 1; i < tr.size(); i++) {
            String status = page.getHtml().xpath("//*[@id=\"main-container\"]/div[1]/div[3]/div[1]/div[2]/table/tbody/tr["+i+"]/td[7]/span/text()").get();
            if(status.equals("AC")) {
                acSum++;
            }
        }
        Acrating acrating = new Acrating();
        acrating.setAcAcNumber(acSum);
        acrating.setAcUserId(userId);
        acrating.setAcContestName(contestName);

        page.putField("data",acrating);
    }
    @Override
    public Site getSite() {
        return SITE;
    }

    public static void main(String[] args) {
        int a= 1000*60*60*24*7;
        System.out.println(a);
    }
}
