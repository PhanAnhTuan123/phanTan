package fit.iuh;

import jakarta.json.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

//Using The Object Model API
public class EmployeeUtils {
    //Chuyển Employee → JsonObject
//    {"emp_id":100, "name":"Le Lan", "salary":32.5}
    public static JsonObject toJson (Employee employee){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder
                .add("emp_id", employee.getEmp_id())
                .add("name", employee.getName())
                .add("salary", employee.getSalary());
        return builder.build();
    }
//Ghi Employee → file JSON
    public static void toJson (Employee employee, String fileName){
        try(JsonWriter writer = Json.createWriter(new FileWriter(fileName))) {

            JsonObjectBuilder builder = Json.createObjectBuilder();
            builder
                    .add("emp_id", employee.getEmp_id())
                    .add("name", employee.getName())
                    .add("salary", employee.getSalary());
            JsonObject jo = builder.build();

            writer.writeObject(jo);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
//Chuyển List<Employee> → JsonArray
//    [{"emp_id":100, "name":"Le Lan", "salary":32.5}, {"emp_id":200, "name":"Le Lanh", "salary":35.5}]
public static JsonArray toJson(List<Employee> employees) {
    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); // Tạo JsonArrayBuilder
    // Duyệt qua từng Employee và chuyển thành JsonObject
    for (Employee emp : employees) {
        JsonObject empJson = Json.createObjectBuilder()
                .add("emp_id", emp.getEmp_id())
                .add("name", emp.getName())
                .add("salary", emp.getSalary())
                .build();
        arrayBuilder.add(empJson); // Thêm vào mảng JSON
    }
    return arrayBuilder.build(); // Trả về JsonArray
}
//Ghi List<Employee> → file JSON
    public static void toJson(List<Employee> employees, String fileName) {
        try (JsonWriter writer = Json.createWriter(new FileWriter(fileName))) {
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder(); // Tạo JsonArrayBuilder

            // Duyệt từng Employee và chuyển thành JsonObject
            for (Employee emp : employees) {
                JsonObject empJson = Json.createObjectBuilder()
                        .add("emp_id", emp.getEmp_id())
                        .add("name", emp.getName())
                        .add("salary", emp.getSalary())
                        .build();
                arrayBuilder.add(empJson); // Thêm vào mảng JSON
            }

            JsonArray jsonArray = arrayBuilder.build(); // Tạo JsonArray
            writer.writeArray(jsonArray); // Ghi vào file
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//Chuyển chuỗi JSON → Employee
//    {"emp_id":100,"name":"Le Lan","salary":32.5}
    public static Employee fromJson(String json){
        try(JsonReader reader = Json.createReader(new StringReader(json))){
            JsonObject jo = reader.readObject();
            long id = jo.getJsonNumber("emp_id").longValue();
            String name = jo.getString("name");
            double salary = jo.getJsonNumber("salary").doubleValue();
            return new Employee(id,name,salary);
        }catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//Đọc file JSON → List<Employee>
//      [{"emp_id":100, "name":"Le Lan", "salary":32.5}, {"emp_id":200, "name":"Le Lanh", "salary":35.5}]
public static List<Employee> fromJson(File file) {
    List<Employee> employees = new ArrayList<>();
    try (JsonReader reader = Json.createReader(new FileReader(file))) {
        JsonArray jsonArray = reader.readArray();
        for (JsonValue value : jsonArray) {
            JsonObject jo = (JsonObject) value;
            Employee emp = new Employee(
                    jo.getJsonNumber("emp_id").longValue(),
                    jo.getString("name"),
                    jo.getJsonNumber("salary").doubleValue()
            );
            employees.add(emp);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return employees;
}
}
