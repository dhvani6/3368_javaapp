package edu.uh.tech.cis3368.finalexam;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // causes each test to clean up after itself
public class FinalExamApplicationTests {

    final String CAT_NAME = "Frodo";
    final String FAV_TOY = "bell";

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private MouseRepository mouseRepository;

    @Test
    public void a_testCreatingAProfessor(){
        Cat cat = new Cat();
        cat.setName(CAT_NAME);
        cat.setFavoriteToy(FAV_TOY);
        catRepository.save(cat);
        // should be only 1 in the database
        assertEquals(1, catRepository.count());
    }


    @Test
    public void b_testAProfCanHaveInterests(){
        Cat myCat = new Cat();
        myCat.setName(CAT_NAME);
        myCat.setFavoriteToy(FAV_TOY);

        // add a research interest
        Mouse mouse = new Mouse();
        mouse.setName("Machine Learning");
        mouse.setCat(myCat);
        myCat.addMouse(mouse);
        mouseRepository.save(mouse);
        assertEquals("the professor should have one interest",1, myCat.getMice().size());

    }

    @Test
    public void c_testAnInterestCanBeRemoved(){

        Cat myCat = new Cat();
        myCat.setName(CAT_NAME);
        myCat.setFavoriteToy(FAV_TOY);

        // add an interest
        Mouse mouse = new Mouse();
        mouse.setName("Mini");
        mouse.setCat(myCat);
        myCat.addMouse(mouse);
        mouseRepository.save(mouse);

        // add a second interest
        Mouse mouse2 = new Mouse();
        mouse.setName("Mickey");
        mouse.setCat(myCat);
        myCat.addMouse(mouse2);
        mouseRepository.save(mouse2);

        // remove one interest
        Cat catReloaded = catRepository.findByName(CAT_NAME);
        catReloaded.removeMouse(mouse2);
        catRepository.save(catReloaded);

        // load from db just to make sure
        Cat catReloadedAgain = catRepository.findByName(CAT_NAME);
        assertNotNull("cat not found", catReloadedAgain);
        assertEquals("cat should have one mouse, not two",1, catReloadedAgain.getMice().size());
    }

    @Test
    public void d_testAProfWithInterestsCanHaveInterestsRemoved(){
        Cat myCat = new Cat();
        myCat.setName(CAT_NAME);
        myCat.setFavoriteToy(FAV_TOY);

        Mouse mouse = new Mouse();
        mouse.setName("Minny");
        mouse.setCat(myCat);
        myCat.addMouse(mouse);
        mouseRepository.save(mouse);


        Mouse mouse2 = new Mouse();
        mouse.setName("Mickey");
        mouse.setCat(myCat);
        myCat.addMouse(mouse2);
        mouseRepository.save(mouse2);

        // check number of interests
        assertEquals(2, myCat.getMice().size());
        myCat.dropMice();
        catRepository.save(myCat);
        assertEquals("the cat should have no mice",0, myCat.getMice().size());

        // insure that no mice are assigned to the cat
        assertEquals("there should be no interests tied to the prof",
                0, mouseRepository.findAllByCatId(myCat.getId()).size());
    }

}
