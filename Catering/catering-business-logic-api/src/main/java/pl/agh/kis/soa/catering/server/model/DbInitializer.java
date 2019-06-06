package pl.agh.kis.soa.catering.server.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbInitializer {
    private static DbInitializer _instance = null;
    private EntityManagerFactory factory = Persistence.createEntityManagerFactory("Catering");

    private DbInitializer() {}

    public static DbInitializer getInstance() {
        if(_instance == null) {
            _instance = new DbInitializer();
            _instance.seed();
        }

        return _instance;
    }

    public EntityManagerFactory getEntityManagerFactory() { return factory; }

    private void seed() {
        EntityManager em = factory.createEntityManager();

        seedMenuCategoryTable(em);
//        seedClientTable(em);
        seedUserAccountAndRoleTables(em);
    }

    private void seedMenuCategoryTable(EntityManager em) {
        MenuCategory menuCategory1 = new MenuCategory();
        menuCategory1.setName("śniadania");
        MenuCategory menuCategory2 = new MenuCategory();
        menuCategory2.setName("dania mięsne");
        MenuCategory menuCategory3 = new MenuCategory();
        menuCategory3.setName("dania jarskie");
        MenuCategory menuCategory4 = new MenuCategory();
        menuCategory4.setName("desery");
        MenuCategory menuCategory5 = new MenuCategory();
        menuCategory5.setName("dania dnia");

        em.getTransaction().begin();
        em.persist(menuCategory1);
        em.persist(menuCategory2);
        em.persist(menuCategory3);
        em.persist(menuCategory4);
        em.getTransaction().commit();
    }

    private void seedClientTable(EntityManager em) {
        UserRole userRole = new UserRole("Client");
        Client client1 = new Client();
        client1.setName("Mariusz");
        client1.setSurname("Pudzianowski");
        UserAccount userAccount1 = new UserAccount();
        userAccount1.setUsername("mpudzianowski");
        userAccount1.setPassword("pudzian123");
        userAccount1.setUserRole(userRole);
        client1.setUserAccount(userAccount1);

        Client client2 = new Client();
        client2.setName("Robert");
        client2.setSurname("Lewandowski");
        UserAccount userAccount2 = new UserAccount();
        userAccount2.setUsername("rlewandowski");
        userAccount2.setPassword("lewy123");
        userAccount2.setUserRole(userRole);
        client2.setUserAccount(userAccount2);

        em.getTransaction().begin();
        em.persist(userRole);
        em.persist(client1);
        em.persist(client2);
        em.getTransaction().commit();
    }

    private void seedUserAccountAndRoleTables(EntityManager em) {
        UserAccount admin = new UserAccount();
        admin.setUsername("admin");
        admin.setPassword("admin");
        UserRole userRole = new UserRole("Admin");
        admin.setUserRole(userRole);
        em.getTransaction().begin();
        em.persist(userRole);
        em.persist(admin);
        em.getTransaction().commit();
    }
}
