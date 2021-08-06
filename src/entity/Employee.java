package entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "Gen", sequenceName = "EMPLOYEES_SEQ", allocationSize = 1)
	@GeneratedValue(generator = "Gen")
	@Column(name = "EMPLOYEE_ID")
	private int id;

	@Column(name = "JOB_ID")
	private String jobId;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(name = "SALARY")
	private int salary;

	@Temporal(TemporalType.DATE)
	@Column(name = "HIRE_DATE")
	private Date hireDate;

	@Basic(fetch = FetchType.LAZY)
	@Column(name = "EMAIL")
	private String email;

	// @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE }, fetch =
	// FetchType.LAZY)
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;

	@Embedded
	@AttributeOverrides(value = { @AttributeOverride(name = "id", column = @Column(name = "MANAGER_ID")) })
	// @Column(name="MANAGER_ID") // (Tambien vale)
	private Manager manager;

	public Employee() {

	}

	public Employee(String jobId, String firstName, String lastName, String phoneNumber, int salary, Date hireDate,
			String email, Manager manager) {
		super();
		this.jobId = jobId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.salary = salary;
		this.hireDate = hireDate;
		this.email = email;
		this.manager = manager;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFullName(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		String empFormat = "employee{id: %s, nombre: %s, apellido: %s, email: %s, número: "
				+ "%s, fechaContratado: %s, salario: %d, " + "departamento: %s}";
		return String.format(empFormat, this.id, this.firstName, this.lastName, this.email, this.phoneNumber,
				this.hireDate.toString(), this.salary, this.department.toString());
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

}
