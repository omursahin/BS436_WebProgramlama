package org.webp.intro.jee.ejb.time.businesslayer;


import org.webp.intro.jee.ejb.time.datalayer.News;

import javax.ejb.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Singleton
@Startup //hızlıca başlangıçta başlat, ilk erişimi bekleme
public class CommentatorBot {

    public final static String COMMENTATOR = "commentator";

    private final AtomicInteger counter = new AtomicInteger(0);

    @EJB
    private NewsEJB newsEJB;

    @EJB
    private CommentEJB commentEJB;

    @Schedule(second = "*/2", minute="*", hour="*", persistent=false)
    public void commentator(){
        if(counter.get() > 5){
            return;
        }
        counter.incrementAndGet();

        List<News> news = newsEJB.getAllNews();
        news.stream().forEach(n -> {
            commentEJB.save(n.getId(),COMMENTATOR, "A comment made by commentator()");
        });
    }

}
