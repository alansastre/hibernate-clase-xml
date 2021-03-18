package com.example.util;

import com.example.model.Company;
import com.example.model.CreditCard;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class OneToMany {


    @Test
    @DisplayName("Test name")
    public void testName() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        CreditCard creditCard1 = new CreditCard("card 1", "3344589711223344", "443", LocalDate.now());
        CreditCard creditCard2 = new CreditCard("card 2", "3344589711223344", "443", LocalDate.now());
        CreditCard creditCard3 = new CreditCard("card 3", "3344589711223344", "443", LocalDate.now());

        Company company = new Company("Empresa OneToMany","B55544433");

        session.beginTransaction(); // abrimos transaccion


        // realizar asociacion de entidades
        company.getCreditCards().add(creditCard1);
        company.getCreditCards().add(creditCard2);
        company.getCreditCards().add(creditCard3);

        // guardar las entidades
        session.save(creditCard1);
        session.save(creditCard2);
        session.save(creditCard3);
        session.save(company);

        // hacer efectivos los cambios en la base de datos
        session.getTransaction().commit();



    }
}
