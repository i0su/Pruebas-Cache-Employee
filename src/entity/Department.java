package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "DEPARTMENTS")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Gen2", sequenceName = "DEPARTMENTS_SEQ")
	@GeneratedValue(generator = "Gen2")
	private int DEPARTMENT_ID;

	@Transient
	private String DEPARTMENT_NAME;

//	@OneToMany(mappedBy = "department", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
	private Collection<Employee> employees = new ArrayList<>();

	@Embedded
	@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "MANAGER_ID")) })
	// @Column(name="MANAGER_ID") // (Tambien vale)
	private Manager manager;

	public Department() {
		
	}
	
	public Department(String name, Manager manager) {
		this.DEPARTMENT_NAME = name;
		this.manager = manager;
	}

	public int getDEPARTMENT_ID() {
		return DEPARTMENT_ID;
	}

	public void setDEPARTMENT_ID(int id) {
		DEPARTMENT_ID = id;
	}

	@Access(AccessType.PROPERTY)
	@Column(name = "DEPARTMENT_NAME")
	public String getDEPARTMENT_NAME() {
		if (DEPARTMENT_NAME.length() < 10) {
			return "dep".concat(DEPARTMENT_NAME);
		}
		return DEPARTMENT_NAME;
	}

	public void setDEPARTMENT_NAME(String name) {
		DEPARTMENT_NAME = name;
	}

	public Collection<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		String depFormat = "department{id: %d, name: %s}";
		return String.format(depFormat, this.DEPARTMENT_ID, this.DEPARTMENT_NAME);
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
