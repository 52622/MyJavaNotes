package org.format.demo.controller;

import org.format.demo.model.Employee;
import org.format.demo.service.IDeptService;
import org.format.demo.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private IDeptService deptService;

    @RequestMapping
    public ModelAndView index() {
        ModelAndView view = new ModelAndView("employee/list");
        List<Employee> employees = employeeService.list();
        view.addObject("list", employees);
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/delete/{employeeId}")
    @ResponseBody
    public String delete(@PathVariable Integer employeeId) {
        employeeService.delete(employeeId);
        return "success";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    public ModelAndView add(ModelAndView view) {
        view.setViewName("employee/form");
        view.addObject("depts", deptService.listAll());
        return view;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/detail/{employeeId}")
    public ModelAndView detail(@PathVariable Integer employeeId, ModelAndView view) {
        view.setViewName("employee/form");
        view.addObject("employee", employeeService.getById(employeeId));
        view.addObject("depts", deptService.listAll());
        return view;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public String add(Employee employee) {
        if(employee.getDept().getId() == null) {
            employee.setDept(null);
        }
        employeeService.saveOrUpdate(employee);
        return "redirect:/employee/";
    }

}
