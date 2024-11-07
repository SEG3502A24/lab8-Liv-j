package seg3x02.employeeGql.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import seg3x02.employeeGql.entity.Employee
import java.util.Optional

@Repository
interface EmployeesRepository: MongoRepository<Employee, String> {
    fun findByName(name: String): Optional<Employee>
    fun deleteByName(name: String)
}