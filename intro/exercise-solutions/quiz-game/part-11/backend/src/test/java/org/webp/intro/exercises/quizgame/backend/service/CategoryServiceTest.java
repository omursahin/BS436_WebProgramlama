package org.webp.intro.exercises.quizgame.backend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.webp.intro.exercises.quizgame.backend.StubApplication;
import org.webp.intro.exercises.quizgame.backend.entity.Category;
import org.webp.intro.exercises.quizgame.backend.entity.SubCategory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CategoryServiceTest extends ServiceTestBase {

    @Autowired
    private CategoryService categoryService;


    @Test
    public void testNoCategory() {

        List<Category> list = categoryService.getAllCategories(false);
        assertEquals(0, list.size());
    }

    @Test
    public void testCreateCategory() {

        String name = "testCreateCategory";

        Long id = categoryService.createCategory(name);
        assertNotNull(id);
    }

    @Test
    public void testGetCategory() {

        String name = "testGetCategory";

        long id = categoryService.createCategory(name);

        Category ctg = categoryService.getCategory(id, false);

        assertEquals(name, ctg.getName());
    }


    @Test
    public void testCreateSubCategory() {

        String ctgName = "ctg_testCreateSubCategory";
        long ctgId = categoryService.createCategory(ctgName);

        String subName = "sub_testCreateSubCategory";
        long subId = categoryService.createSubCategory(ctgId, subName);

        SubCategory sub = categoryService.getSubCategory(subId);
        assertEquals((Long) ctgId, sub.getParent().getId());
        assertEquals(subName, sub.getName());
    }

    @Test
    public void testGetAllCategories() {

        long a = categoryService.createCategory("a");
        long b = categoryService.createCategory("b");
        long c = categoryService.createCategory("c");

        categoryService.createSubCategory(a, "1");
        categoryService.createSubCategory(b, "2");
        categoryService.createSubCategory(c, "3");


        List<Category> categories = categoryService.getAllCategories(false);
        assertEquals(3, categories.size());

        Category first = categories.get(0);
        assertThrows(Exception.class, () -> first.getSubCategories().size());

        categories = categoryService.getAllCategories(true);

        Category readBack = categories.get(0);
        assertEquals(1, readBack.getSubCategories().size());
    }

    @Test
    public void testCreateTwice() {

        String ctg = "Computer Science";

        categoryService.createCategory(ctg);

        assertThrows(Exception.class, () -> categoryService.createCategory(ctg));
    }
}