package com.buct.spider.atcoder;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct.spider.entity.Accontest;
import com.buct.spider.entity.Acrating;
import com.buct.spider.mapper.AcratingMapper;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Selectable;

import javax.annotation.Resource;
import java.util.List;

@Component
public class AcContestProcess implements PageProcessor {
    @Resource
    private AcratingMapper acratingMapper;
    private static final Site SITE = Site.me()
            // 设置字符编码集
            .setCharset("UTF-8")
            // 设置Http连接重试次数
            .setRetryTimes(30)
            // 设置线程休眠时间
            .setSleepTime(1000);

    /**
     * 页面分析
     * @param page 下载结果封装成Page对象
     */
    @Override
    public void process(Page page) {
        // Html html = page.getHtml();
        // 解析页面内容

        Selectable titleLink = page.getHtml().xpath("/html/head/title/text()");
        String title = titleLink.get();

        int i = 1, j;
        // 过去的比赛
        if (title.equals("Contest Archive - AtCoder")) {
            Selectable table2 = page.getHtml().xpath("//*[@id=\"main-container\"]/div[1]/div[3]/div[2]/div/table");
            List<String> tr2 = table2.css("tbody > tr").all();

            for (i = 1; i <= tr2.size(); i++) {
                String date = table2.css("tbody > tr:nth-child(" + i + ") > td:nth-child(1) > a > time", "text").get();
                String name = table2.css("tbody > tr:nth-child(" + i + ") > td:nth-child(2) > a", "text").get();
                String durationTime = table2.css("tbody > tr:nth-child(" + i + ") > td:nth-child(3)", "text").get();
                Selectable typeLink = page.getHtml().xpath("//*[@id=\"main-container\"]/div[1]/div[3]/div[2]/div/table/tbody/tr["+i+"]/td[2]/a");
                String type = typeLink.$("a", "href").get();

                // 添加url
                List<String> nextPageUrl = page.getHtml().xpath("//*[@id=\"main-container\"]/div[1]/div[3]/div[3]/ul/li/a/@href").all();
                for (String pageurl : nextPageUrl) {
                    page.addTargetRequest(pageurl);
                }

                QueryWrapper<Acrating> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ac_contest_name", name);
                List<Acrating> particiPantList = acratingMapper.selectList(queryWrapper);
                Integer participantNumber = particiPantList.size();

                Accontest accontest = new Accontest();
                accontest.setAcContestName(name);
                accontest.setAcContestStarttimeseconds(date);
                accontest.setAcContestDurationseconds(durationTime);
                accontest.setAcContestType(type);
                accontest.setAcContestParticipantsnumber(participantNumber);
                accontest.setAcContestPhase("FINISHED");

                String keyword = "data" + i;
                page.putField(keyword, accontest);
            }
        }
        // 未来的比赛
        if (title.equals("Present Contests - AtCoder")) {
            Selectable table1 = page.getHtml().xpath("//div[@id='contest-table-upcoming']/div/div/table");
            List<String> tr1 = table1.css("tbody > tr").all();
            int k = 1;

            for (j = i + 1; j <= tr1.size() + i; j++, k++) {
                String date = table1.css("tbody > tr:nth-child(" + k + ") > td:nth-child(1) > a > time", "text").get();
                String name = table1.css("tbody > tr:nth-child(" + k + ") > td:nth-child(2) > a", "text").get();
                String durationTime = table1.css("tbody > tr:nth-child(" + k + ") > td:nth-child(3)", "text").get();
                Selectable typeLink = page.getHtml().xpath("//div[@id=\"contest-table-upcoming\"]/div/div/table/tbody/tr[" + k + "]/td[2]/a");
                String type = typeLink.$("a", "href").get();

                QueryWrapper<Acrating> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("ac_contest_name", name);
                List<Acrating> particiPantList = acratingMapper.selectList(queryWrapper);
                Integer participantNumber = particiPantList.size();

                Accontest accontest = new Accontest();
                accontest.setAcContestName(name);
                accontest.setAcContestStarttimeseconds(date);
                accontest.setAcContestDurationseconds(durationTime);
                accontest.setAcContestType(type);
                accontest.setAcContestParticipantsnumber(participantNumber);
                accontest.setAcContestPhase("BEFORE");

                // 把结果传递给pipeline
                String keyword = "data" + j;
                page.putField(keyword, accontest);
            }
        }
    }

    /**
     * 返回site对象
     * site是站点配置 使用Site，me()创建site对象
     * @return
     */
    @Override
    public Site getSite() {
        return SITE;
    }

    public static void main(String[] args) {
        int a= 1000*60*60*24*7;
        System.out.println(a);
    }
}
