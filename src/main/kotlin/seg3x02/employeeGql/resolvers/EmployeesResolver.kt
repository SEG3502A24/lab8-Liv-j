package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.*

@Controller
class EmployeesResolver (private val employeesRepository: EmployeesRepository) {
    @QueryMapping
    fun employees(): List<Employee> {
        return employeesRepository.findAll()
    }

    @MutationMapping
    fun newEmployee(@Argument("createEmployeeInput") input: CreateEmployeeInput) : Employee {
        if (input.name != null &&
                input.dateOfBirth != null &&
                input.city != null && 
                input.salary != null && 
                input.gender != null && input.email != null) {
            val employee = Employee(input.name, input.dateOfBirth, input.city, input.salary, input.gender, input.email)
            employeesRepository.save(employee)
            return employee
        } else {
            throw Exception("Invalid input")
        }
    }

    @MutationMapping
    fun deleteEmployee(@Argument("name") name: String) : Boolean {
        employeesRepository.deleteByName(name)
        return true
    }

    @MutationMapping
    fun updateEmployee(@Argument("name") name: String, @Argument("createEmployeeInput") input: CreateEmployeeInput) : Employee {
        val employee = employeesRepository.findByName(name)
        employee.ifPresent {
            if (input.name != null) {
                it.name = input.name
            }
            if (input.dateOfBirth != null) {
                it.dateOfBirth = input.dateOfBirth
            }
            if (input.city != null) {
                it.city = input.city
            }
            if (input.salary != null) {
                it.salary = input.salary
            }
            if (input.gender != null) {
                it.gender = input.gender
            }
            if (input.email != null) {
                it.email = input.email
            }
            employeesRepository.save(it)
        }
        return employee.get()
    }
}