package com.example.util;

import com.example.model.Company;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CompanyTest {

    @Test
    @DisplayName("Retrieve company ordering employees by name")
    public void retrieveCompanyOrderingEmployeesByName() {

        Session session = HibernateUtil.getSessionFactory().openSession();

        Company company = session.find(Company.class, 1L );

        company.getEmployees().forEach(employee -> System.out.println(employee.getName()));
        // System.out.println(company.getEmployees());

        session.close();


    }
}
