package org.webp.intro.exercises.quizgame.selenium.po;

import org.openqa.selenium.WebDriver;
import org.webp.intro.exercises.quizgame.selenium.po.ui.MatchPO;
import org.webp.misc.testutils.selenium.PageObject;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Quiz Game");
    }

    public MatchPO startNewMatch(){

        clickAndWait("newMatchBtnId");
        MatchPO po = new MatchPO(this);
        assertTrue(po.isOnPage());

        return po;
    }
}
