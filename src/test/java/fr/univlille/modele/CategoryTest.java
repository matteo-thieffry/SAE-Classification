package fr.univlille.modele;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest {

    @Test
    public void testAddCategory() {
        Category.addCategory("Iris");
        assertEquals(2, Category.getCategories().size());
    }

    @Test
    public void testGetCategory(){
        Category crododile = new Category("crocodile");
        assertEquals(crododile,Category.getCategory("crocodile"));
    }

    @Test
    public void testCategoryEquals(){
        Category iris1 = new Category("Iris1");
        Category croco = new Category("croco");
        //ItelliJ dit de mettre ça en assertEquals mais cette méthode test le equals fait dans la fonction
        assertTrue(iris1.equals(Category.getCategory("Iris1")));
        assertFalse(croco.equals(Category.getCategory("crocodile")));
    }
}
