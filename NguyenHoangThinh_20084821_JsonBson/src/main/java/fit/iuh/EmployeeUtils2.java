package fit.iuh;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Using The Streaming API
public class EmployeeUtils2 {
    //Đọc một file JSON chứa thông tin 1 nhân viên và chuyển thành đối tượng Employee.
    //    {"emp_id":100, "name":"Le Lan", "salary":32.5}
    public static Employee fromJson(File jsonFile){
        Employee emp = null;
        String keyName = "";
        try(JsonParser parser = Json.createParser(new FileReader(jsonFile))){
            while (parser.hasNext()){
                JsonParser.Event event = parser.next();
                switch (event){
                    case START_OBJECT -> emp = new Employee();
                    case KEY_NAME -> keyName = parser.getString();
                    case VALUE_NUMBER -> {
                        if("emp_id".equals(keyName))
                            emp.setEmp_id(parser.getLong());
                        else if("salary".equals(keyName))
                            emp.setSalary(parser.getBigDecimal().doubleValue());
                    }
                    case VALUE_STRING -> emp.setName(parser.getString());
                    case END_OBJECT -> {
                        return emp;
                    }
                    default -> {}
                }
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        return emp;
    }
    //Chuyển chuỗi JSON chứa mảng nhân viên thành List<Employee>.
    //      [{"emp_id":100, "name":"Le Lan", "salary":32.5}, {"emp_id":200, "name":"Le Lanh", "salary":35.5}]
    public static List<Employee> fromJson(String json) {
        List<Employee> employees = new ArrayList<>();
        Employee emp = null;
        String keyName = "";
        // Sử dụng JsonParser để phân tích chuỗi JSON
        try (JsonParser parser = Json.createParser(new StringReader(json))) {
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_ARRAY -> {}
                    case START_OBJECT -> {
                        emp = new Employee();
                    }
                    case KEY_NAME -> {
                        keyName = parser.getString();
                    }
                    case VALUE_NUMBER -> {
                        if ("emp_id".equals(keyName)) {
                            emp.setEmp_id(parser.getLong());
                        } else if ("salary".equals(keyName)) {
                            emp.setSalary(parser.getBigDecimal().doubleValue());
                        }
                    }
                    case VALUE_STRING -> {
                        if ("name".equals(keyName)) {
                            emp.setName(parser.getString());
                        }
                    }
                    case END_OBJECT -> {
                        employees.add(emp);
                    }
                    case END_ARRAY -> {
                    }
                    default -> {}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }
    //Chuyển đối tượng Employee thành chuỗi JSON.
//    Employee(id=100, name=Le Lan, salary=32.5)
//    {"emp_id":100, "name":"Le Lan", "salary":32.5}
    public static String toJson (Employee employee){
        StringWriter sw = null;
        try(JsonGenerator gen = Json.createGenerator(sw = new StringWriter())){
            gen.writeStartObject()
                    .write("emp_id", employee.getEmp_id())
                    .write("name", employee.getName())
                    .write("salary", employee.getSalary())
                    .writeEnd();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return sw.toString();
    }
    //Ghi thông tin Employee vào file JSON.
    public static void toJson (Employee employee, String fileName){
        try(JsonGenerator gen = Json.createGenerator(new FileWriter(fileName))){
            gen.writeStartObject()
                    .write("emp_id", employee.getEmp_id())
                    .write("name", employee.getName())
                    .write("salary", employee.getSalary())
                    .writeEnd();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//Chuyển danh sách nhân viên thành chuỗi JSON dạng mảng.
//    [{"emp_id":100, "name":"Le Lan", "salary":32.5}, {"emp_id":200, "name":"Le Lanh", "salary":35.5}]
public static String toJson(List<Employee> employees) {
    StringWriter sw = new StringWriter();
    try (JsonGenerator gen = Json.createGenerator(sw)) {
        gen.writeStartArray(); // Bắt đầu mảng
        for (Employee emp : employees) {
            gen.writeStartObject()
                    .write("emp_id", emp.getEmp_id())
                    .write("name", emp.getName())
                    .write("salary", emp.getSalary())
                    .writeEnd();
        }
        gen.writeEnd(); // Kết thúc mảng
    }
    return sw.toString();
}
}
