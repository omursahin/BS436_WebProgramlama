package org.webp.intro.exercises.quizgame.selenium;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.webp.intro.exercises.quizgame.backend.service.QuizService;
import org.webp.intro.exercises.quizgame.selenium.po.IndexPO;
import org.webp.intro.exercises.quizgame.selenium.po.SignUpPO;
import org.webp.intro.exercises.quizgame.selenium.po.ui.MatchPO;
import org.webp.intro.exercises.quizgame.selenium.po.ui.ResultPO;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();


    @Autowired
    private QuizService quizService;

    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement();
    }


    private IndexPO home;


    private IndexPO createNewUser(String username, String password) {

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(username, password);
        assertNotNull(indexPO);

        return indexPO;
    }

    @BeforeEach
    public void initTest() {

        /*
            Yeni bir session sahibi olmak istiyoruz yoksa test'ler aynı session bean'lerini
            paylaşırlar
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String password = "123456789";
        home = createNewUser(username, password);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(username));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(username));
    }

    @Test
    public void testNewMatch() {

        createNewUser(getUniqueId(), "123");

        MatchPO po = home.startNewMatch();
        assertTrue(po.canSelectCategory());
    }

    @Test
    public void testFirstQuiz() {

        createNewUser(getUniqueId(), "123");

        MatchPO po = home.startNewMatch();
        String ctgId = po.getCategoryIds().get(0);

        assertTrue(po.canSelectCategory());
        assertFalse(po.isQuestionDisplayed());

        po.chooseCategory(ctgId);
        assertFalse(po.canSelectCategory());
        assertTrue(po.isQuestionDisplayed());

        assertEquals(1, po.getQuestionCounter());
    }

    @Test
    public void testWrongAnswer() {

        createNewUser(getUniqueId(), "123");

        MatchPO matchPO = home.startNewMatch();
        String ctgId = matchPO.getCategoryIds().get(0);

        matchPO.chooseCategory(ctgId);

        long quizId = matchPO.getQuizId();

        int rightAnswer = quizService.getQuiz(quizId).getIndexOfCorrectAnswer();
        int wrongAnswer = (rightAnswer + 1) % 4;

        ResultPO resultPO = matchPO.answerQuestion(wrongAnswer);
        assertNotNull(resultPO);

        assertTrue(resultPO.haveLost());
        assertFalse(resultPO.haveWon());
    }

    @Test
    public void testWinAMatch() {

        createNewUser(getUniqueId(), "123");

        MatchPO matchPO = home.startNewMatch();
        String ctgId = matchPO.getCategoryIds().get(0);
        matchPO.chooseCategory(ctgId);

        ResultPO resultPO = null;

        for (int i = 1; i <= 5; i++) {
            assertTrue(matchPO.isQuestionDisplayed());
            assertEquals(i, matchPO.getQuestionCounter());

            long quizId = matchPO.getQuizId();
            int rightAnswer = quizService.getQuiz(quizId).getIndexOfCorrectAnswer();

            resultPO = matchPO.answerQuestion(rightAnswer);

            if (i != 5) {
                assertNull(resultPO);
            }
        }

        assertTrue(resultPO.haveWon());
        assertFalse(resultPO.haveLost());
    }
}
