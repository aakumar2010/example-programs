package org.anil.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.anil.bean.Employee;

public class EmployeeService {

	static HashMap<Integer, Employee> employeeIdMap = getEmployeeIdMap();

	public EmployeeService() {
		super();

		if (employeeIdMap == null) {
			employeeIdMap = new HashMap<Integer, Employee>();
			// Creating some object of Employees while initializing
			Employee anilEmployee = new Employee(1, "Anil", "anil@gmail.com", 614943939);
			Employee chandraEmployee = new Employee(2, "Anil", "anil@gmail.com", 614943939);
			Employee venuEmployee = new Employee(3, "Anil", "anil@gmail.com", 614943939);
			Employee dhanaEmployee = new Employee(4, "Anil", "anil@gmail.com", 614943939);

			employeeIdMap.put(1, anilEmployee);
			employeeIdMap.put(2, chandraEmployee);
			employeeIdMap.put(3, venuEmployee);
			employeeIdMap.put(4, dhanaEmployee);
		}
	}

	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<Employee>(employeeIdMap.values());
		return employees;
	}

	public Employee getEmployee(int id) {
		Employee employee = employeeIdMap.get(id);
		return employee;
	}

	public Employee addEmployee(Employee employee) {
		employee.setId(employeeIdMap.size() + 1);
		employeeIdMap.put(employee.getId(), employee);
		return employee;
	}

	public Employee updateEmployee(Employee employee) {
		if (employee.getId() <= 0)
			return null;
		employeeIdMap.put(employee.getId(), employee);
		return employee;

	}

	public void deleteEmployee(int id) {
		employeeIdMap.remove(id);
	}

	public static HashMap<Integer, Employee> getEmployeeIdMap() {
		return employeeIdMap;
	}

}
