package service;

import javax.persistence.EntityManager;

import entity.Department;

public class ServiceDepartment {

	// Persist Department
	public static void insertDepartment(EntityManager em, Department department) {
		em.persist(department);
	}

	// Find Department
	public static Department findDepartment(EntityManager em, int id) {
		return em.find(Department.class, id);
	}

}
