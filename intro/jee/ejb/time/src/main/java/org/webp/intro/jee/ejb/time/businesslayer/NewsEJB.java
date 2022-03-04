package org.webp.intro.jee.ejb.time.businesslayer;


import org.webp.intro.jee.ejb.time.datalayer.News;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class NewsEJB {

    @PersistenceContext
    private EntityManager em;

    public void createNews(String author, String text){
        News news = new News(null, text, author);
        em.persist(news);
    }

    public List<News> getAllNews(){
        return em.createQuery("SELECT n FROM News n", News.class).getResultList();
    }

}
