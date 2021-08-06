package main;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import entity.Department;
import entity.Employee;
import entity.Manager;
import service.ServiceDepartment;
import service.ServiceEmployee;

public class EmployeeMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ejercicioEmployees");
		caso1(emf);
		caso2(emf);
		caso3(emf);
		emf.close();
	}

	private static void caso1(EntityManagerFactory emf) {
		System.out.println(
				"\n\n\n\n*** CASO 1: SIN CLEAR --> se ve la lista de empleados vacía (NO se actualiza bien la caché) ***");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Manager manager = new Manager(130);
		Department departamento = new Department("Departamento de prueba", manager);
		ServiceDepartment.insertDepartment(em, departamento);

		String email = String.valueOf(System.currentTimeMillis()); // para que no se repita el email
		Employee empleado = new Employee("IT_PROG", "Iosu", "Sanchez", "630.123.5555", 18000, new Date(), email,
				manager);
		empleado.setDepartment(departamento);
		ServiceEmployee.insertEmployee(em, empleado);

		em.getTransaction().commit();
		System.out.printf("\n*** Employee %d and Department %d commited ***\n", empleado.getId(),
				departamento.getDEPARTMENT_ID());

		int lastDepId = departamento.getDEPARTMENT_ID();
		printDepartmentEmployees(em, lastDepId);

		em.close();
		System.out.println("FIRST EM CLOSED");
		EntityManager em2 = emf.createEntityManager();
		System.out.println("SECOND EM OPENED");

		printDepartmentEmployees(em2, lastDepId);

		em2.close();
	}

	private static void caso2(EntityManagerFactory emf) {
		System.out.println(
				"\n\n\n\n*** CASO 2: CON CLEAR --> se obtienen los datos actuales de la BD (NO se actualiza bien la caché) ***");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Manager manager = new Manager(130);
		Department departamento = new Department("Departamento de prueba", manager);
		ServiceDepartment.insertDepartment(em, departamento);

		String email = String.valueOf(System.currentTimeMillis()); // para que no se repita el email
		Employee empleado = new Employee("IT_PROG", "Iosu", "Sanchez", "630.123.5555", 18000, new Date(), email,
				manager);
		empleado.setDepartment(departamento);
		ServiceEmployee.insertEmployee(em, empleado);

		em.getTransaction().commit();
		System.out.printf("\n*** Employee %d and Department %d commited ***\n", empleado.getId(),
				departamento.getDEPARTMENT_ID());

		em.clear(); // Traemos a la caché los datos actuales de la BD

		int lastDepId = departamento.getDEPARTMENT_ID();
		printDepartmentEmployees(em, lastDepId);

		em.close();
		System.out.println("FIRST EM CLOSED");
		EntityManager em2 = emf.createEntityManager();
		System.out.println("SECOND EM OPENED");

		printDepartmentEmployees(em2, lastDepId);

		em2.close();
	}

	private static void caso3(EntityManagerFactory emf) {
		System.out.println("\n\n\n\n*** CASO 3: SIN CLEAR --> Actualizando correctamente la caché ***");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();

		Manager manager = new Manager(130);
		Department departamento = new Department("Departamento de prueba", manager);
		ServiceDepartment.insertDepartment(em, departamento);

		String email = String.valueOf(System.currentTimeMillis()); // para que no se repita el email
		Employee empleado = new Employee("IT_PROG", "Iosu", "Sanchez", "630.123.5555", 18000, new Date(), email,
				manager);
		empleado.setDepartment(departamento);
		ServiceEmployee.insertEmployee(em, empleado);
		departamento.getEmployees().add(empleado); // actualizar la caché

		em.getTransaction().commit();
		System.out.printf("\n*** Employee %d and Department %d commited ***\n", empleado.getId(),
				departamento.getDEPARTMENT_ID());

		// NO SE HACE CLEAR
		
		int lastDepId = departamento.getDEPARTMENT_ID();
		printDepartmentEmployees(em, lastDepId);

		em.close();
		System.out.println("FIRST EM CLOSED");
		EntityManager em2 = emf.createEntityManager();
		System.out.println("SECOND EM OPENED");

		printDepartmentEmployees(em2, lastDepId);

		em2.close();
	}

	private static void printDepartmentEmployees(EntityManager em, int id) {
		Department d = ServiceDepartment.findDepartment(em, id);
		System.out.printf("\nEMPLEADOS DEL DEPARTAMENTO %d (%s) :\n", d.getDEPARTMENT_ID(), d.getDEPARTMENT_NAME());
		Collection<Employee> coll = d.getEmployees();
		if (coll != null) {
			System.out.println("NOT NULL. Size: " + coll.size());
			Iterator<Employee> it = coll.iterator();
			while (it.hasNext()) {
				System.out.println(it.next());
			}
		} else {
			System.out.println("NULL");
		}
	}

}