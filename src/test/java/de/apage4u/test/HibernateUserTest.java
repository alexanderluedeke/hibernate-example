package de.apage4u.test;


import de.apage4u.demo.entity.UserEntity;
import de.apage4u.demo.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

public class HibernateUserTest {
  private static final int TEST_USER_ID = 1;
  private static final String TEST_USER_MAIL = "peter.alexander@mail.com";
  private static final String TEST_USER_FIRST_NAME = "peter";
  private static final String TEST_USER_LAST_NAME = "alexander";

  private static Session session;

  @Before
  public void setUp() throws Exception {
    session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
  }

  @Test
  public void shouldPersistUser() {
    UserEntity user = buildTestUser();

    session.save(user);
    session.getTransaction().commit();
    session.close();

    session = HibernateUtil.getSessionFactory().openSession();

    user = new UserEntity();
    session.load(user, TEST_USER_ID);
    Assert.that(TEST_USER_MAIL.equals(user.getEmail()), "Email is different!");
  }

  private static UserEntity buildTestUser() {
    UserEntity emp = new UserEntity();
    emp.setUserId(TEST_USER_ID);
    emp.setEmail(TEST_USER_MAIL);
    emp.setFirstName(TEST_USER_FIRST_NAME);
    emp.setLastName(TEST_USER_LAST_NAME);

    return emp;
  }

  @After
  public void tearDown() throws Exception {
    HibernateUtil.shutdown();
  }
}