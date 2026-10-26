/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.util.Scanner; // lớp Scanner trong gói java.util cho viện nhập liệu
import java.util.Date; // lớp mô tả cho ngày tháng
import java.text.DateFormat; // lớp mô tả dạng ngày tháng
import java.text.SimpleDateFormat;// định dạng ngày tháng đơn giản dd-MM-yyyy...
// lớp mô tả lỗi khi phân tích (parse) string sang số/Date...
import java.text.ParseException; 
import java.util.Calendar; //Lớp trừu tượng mô tả chung cho lịch  
import java.util.GregorianCalendar; //Lớp cụ thể cho dương lịch
import java.util.List;
    
/**
 *
 * @author hoang
 */
public class ConsoleInputter {
    // fields
    public static Scanner sc = new Scanner(System.in);

    /* Nhập 1 chuỗi theo mẫu quy định (pattern). Vì pattern có nhiều dạng nên
       trong prompt nên có lời giải thích về mẫu nhập. Nếu user nhập sai, một
       thông báo errorMsg được xuất ra. Thí dụ một cách dùng:
       String phone = getStr("Phone no.", "[\\d]{9}|[\\d]{11}", "Phone:9/11 digits!");
    */
    public static String getString(String prompt, String pattern, String errorMsg) {
        String data;
        boolean valid;
        do{
            System.out.print(prompt + ": ");
            data = sc.nextLine().trim();
            valid = data.matches(pattern);// kiểm tra data trùng với mẫu không
            if (!valid) System.out.println(errorMsg);// không trùng mẫu
        } while (!valid); // lặp lại nếu data không trùng mẫu
        return data;
    }
    
    // Nhập 1 chuỗi bất kỳ
    public static String getStr(String prompt){
        System.out.print(prompt + ": "); // xuất lời nhắc nhở
        return sc.nextLine().trim(); 
    }

    /* Nhập số nguyên trong 1 khoảng [min,max]
       Cách dùng: int age = getInt("Age", 18,60);
    */
    public static int getInt(String prompt, int min, int max) {
        int result = 0;
        do{
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Integer.parseInt(sc.nextLine().trim());
            if (result < min || result > max )
                System.out.println("Value range: " + "[" + min + ", " + max + "]" );          
        } while (result < min || result > max );
        return result;
    }

    /* Nhập số thực trong 1 khoảng [min,max]
       Cách dùng: double salary = getDouble("Salary", 1.0,4000.0);
    */
    public static double getDouble(String prompt, double min, double max) {
        double result = 0;
        do{
            System.out.print(prompt + "[" + min + ", " + max + "]: ");
            result = Double.parseDouble(sc.nextLine().trim());
            if (result < min || result > max )
                System.out.println("Value range: " + "[" + min + ", " + max + "]" );            
        } while (result < min || result > max );
        return result;
    }
    
    /* Nhập data dạng boolean. Cách dùng:
       boolean cont = getBoolean ("Do you want to continue");
    */ 
    public static boolean getBoolean(String prompt) {
        // xuất lời nhắc nhở cùng với giải thích cách nhập
        System.out.print(prompt + " (Y/N, T/F, 1/0)?: "); 
        String data = sc.nextLine().trim().toUpperCase(); // lấy vào 1 chuỗi 
        char c = data.charAt(0); // lấy ký tự đầu tiên do user trả lời
        // trả trị true cho 3 trường hợp sau / ngược lại trả trị false
        return c=='Y' || c=='T' || c=='1';
    }
    
    /* Nhập Date theo mẫu dd-MM-yyyy hoặc MM-dd-yyyy, .... Cách dủng
       Date d = getDate("Date of birth:", "dd-MM-yyyy");
       Ở đây hành vi parse(...) của lớp DateFormat được dùng. Hành vi này sẽ 
       tự động điều chỉnh ngày lên xuống để có ngày kết quả phù hợp. 
       Thí dụ 32-12-2024 sẽ chuyển thành 01/01/2025.
       Nếu không muốn dùng cơ chế tự động này, bạn cần tự viết lấy cơ chế 
       chuyển đổi, Cách làm được đề nghị: 
       - Nhập chuỗi
       - Cắt chuỗi thành 3 thành phần (date, month, year)
       - Kiểm tra tính hợp lệ của 3 thành phần này (lưu ý về năm nhuận)
       - Nếu dữ liệu nhập hợp lệ mới nhờ DataFormat chuyển String sang Date.
    */
    public static Date getDate(String prompt, String dateFormat){
        String dateStr;
        Date d;
        // Tạo DateFormat formatter với date format trong tham sồ
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        do{
            System.out.print(prompt + ": "); // xuất lời nhắc
            dateStr = sc.nextLine().trim(); // nhập data
            try{ // phân tích String -> Date. Hành vi parse sẽ tự động điều  
                //chỉnh phù hợp. Thí dụ 32-12-2024 sẽ chuyển thành 01/01/2025 
                d = formatter.parse(dateStr); 
            }
            catch (ParseException e){ // nếu phân tích có lỗi xuất thông báo
                System.out.println("Date format should be " + dateFormat + ".");
                d = null;
            }
        } while (d==null);
        return d; 
    }
    
    /* Đổi Date sang chuỗi dd-MM-yyyy/ MM-dd-yyyy,... Cách dùng:
       System.out.println(dateStr(aDate, "dd-MM-yyyy"));
     */
    public static String dateStr(Date date, String dateFormat){
        if (date==null) return null;
        // Tạo DateFormat object làm việc với định đạng trong tham số thứ hai
        DateFormat formatter = new SimpleDateFormat(dateFormat);
        // rồi trả kết quả sau khi đã chuyển dạng
        return formatter.format(date);
    }
    
    /* Chuyển chuỗi String sang Date theo định dạng dateFormat. 
    Trả về null nếu sai định dạng hoặc chuỗi không hợp lệ. */
    public static Date parseDate(String dateStr, String dateFormat) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            sdf.setLenient(false); // Không cho phép ngày vô lý (như 32/12)
            return sdf.parse(dateStr.trim());
        } catch (ParseException e) {
            return null;
        }
    }
  
    /* Lấy 1 thành phần trong Date. Lớp Calender giúp truy xuất các thành phần
       Cách dùng: int year = getPart(aDate, Calendar.YEAR);
       Chú ý: Thành phần tháng cho kết quả : Tháng 1 --> 0
    */
    public static int getPart(Date d, int calendarPart){
        GregorianCalendar cal = new GregorianCalendar();// tạo calandar
        cal.setTime(d); // cho calendar mang ngày d
        return cal.get(calendarPart); // lấy ra thành phần thời gian này
    }
    // Lấy tuổi với ngày sinh dã biết
    public int getAge(Date birthDate){
        int currentYear = getPart(new Date(), Calendar.YEAR);
        int birthYear = getPart(birthDate, Calendar.YEAR);
        return currentYear-birthYear;
    } 
    /* Thay vì xây dựng lớp cho Menu, hàm menu được xây dựng. Tham số của 
       các hàm menu này có thể là một mảng tĩnh, mảng động hoặc một danh sách
       Cú pháp tham số là mảng động (tham số với số phần tử thay đổi tùy ý) 
       DataType... Hàm có số đối số thay đổi). Trình biên dịch cư xử với 
       tham số có số lượng thay đổi như mảng 1 chiều. */
    
    /*Menu trả về số int mà người dùng chọn. 
    * Cách dùng: int choice = intMenu("Add", "Search", "Remove");
    */
    public static int intMenu (Object... options){
       // int choice;
        int n= options.length ; // số mục trong menu
        for (int i=0; i< n; i++) // xuất các options
            System.out.println((i+1) + "-" + options[i]);
        return getInt("Choose ", 1, n); // User bị buộc nhập số phù hợp 1..n
    }
    public static int intMenu (List options){
       // int choice;
        int n= options.size() ; // số mục trong menu
        for (int i=0; i< n; i++) // xuất các options
            System.out.println((i+1) + "-" + options.get(i));
        return getInt("Choose ", 1, n); // User bị buộc nhập số phù hợp 1..n
    }
    
    /*Menu trả về object mà người dùng chọn 
     Cách dùng: String objChoice = (String)objMenu("Add", "Search", "Remove");
    */
    public static Object objMenu (Object... options){
        int choice = intMenu(options);
        return options[choice-1];
    }
    public static Object objMenu (List options){
        int choice = intMenu(options);
        return options.get(choice-1);
    }     
    
    // sinh key tự động dựa trên ngày tháng theo mẫu yyyyMMddhhmmss
    public static String dateKeyGen(){
        Date now = new Date(); // lấy ngày hiện tại
        // chuyển thành dạng chuỗi theo mẫu
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss");
        return f.format(now);
    }
}
