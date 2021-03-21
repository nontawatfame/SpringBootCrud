package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;



@Controller
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	

	@Autowired
	private EmployeeRepository employeeRepository;


	@GetMapping("/")
	public String viewHomePage(Model model) {
		model.addAttribute("listEmployees", employeeService.getAllEmployees());
		return "index";
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee, RedirectAttributes  attributes, Model model) {
		employeeService.saveEmployee(employee);
		attributes.addFlashAttribute("flashAttribute", true);
		// model.addAttribute("test1", "test1");
        // attributes.addAttribute("attribute", "redirectWithRedirectView");
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		System.out.println(employee.getId());
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable (value = "id") long id) {
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	@GetMapping("/test/{firstName}")
	public String test(@PathVariable (value = "firstName") String firstName , Model model) {
		model.addAttribute("test1", "test1");
		model.addAttribute("test2", "test2");
		model.addAttribute("test3", "test3");
		model.addAttribute("employee", firstName);
		for (Employee em : this.employeeRepository.findByName(firstName)) {
			System.out.println("id = " + em.getId());
			System.out.println("FirstName = " + em.getFirstName());
		}
		model.addAttribute("listEmployee", this.employeeRepository.findByName(firstName));
		return "test";
	}

	
	
}
