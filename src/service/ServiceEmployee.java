package service;

import javax.persistence.EntityManager;

import entity.Employee;

public class ServiceEmployee {

	// Persist Employee
	public static void insertEmployee(EntityManager em, Employee employee) {
		em.persist(employee);
	}

	// Find Employee
	public static Employee findEmployee(EntityManager em, int id) {
		return em.find(Employee.class, id);
	}

}
