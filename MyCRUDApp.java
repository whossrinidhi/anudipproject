package comp.examples;

import java.sql.*; // importing the header file to handle sql related operations
import java.util.Scanner; //importing the scanner class for taking the user input
public class MyCRUDApp {

    //method to create the records in the database
//    public static void create(){
//
//
//    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException{
        // variable to store the user's choice
        int choice;
        // Step 1: Loading the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Step 2: Establishing a database connection
        Connection con = DriverManager.getConnection("jdbc:mysql://root@localhost:3306/jdbcdemo", "root", "root1234");
        // Step 3: Creating a Statement
        Statement stmt = con.createStatement();
        //scanner class to get the input from the user
        Scanner sc = new Scanner(System.in);

        do{
            // Now we will print out all the operations to the user that can be done.
            System.out.println("Welcome to Library management application. Please enter the choice of the operation you want to perform: ");
            System.out.println("1.Add");
            System.out.println("2.Read");
            System.out.println("3.Update");
            System.out.println("4.Delete");
            System.out.println("5.get the name of the books issued by a particular student");
            System.out.println("6.Exit");
            choice= sc.nextInt(); //getting the input from the user and storing it in the choice variable
            sc.nextLine(); //getting new line
            // use a switch statement to do the necessary operations
            switch (choice){
                case 1:
                    //Collecting the student information
                    int insertchoice;
                    System.out.println("which information do you want to enter?");
                    System.out.println("1.books");
                    System.out.println("2.student");
                    System.out.println("3.issued books");
                    insertchoice= sc.nextInt(); //choice for the table
                    sc.nextLine();
                    switch (insertchoice){
                        case 1: //inserting into books table
                            //entering the books
                            System.out.println("give the information regarding the book");
                            System.out.println("Please enter the id of the book");
                            int id = sc.nextInt(); //taking the id and storing it in the variable id
                            sc.nextLine(); //getting next line
                            System.out.println("What is the name of the book?: "); //prompting the user to enter the first name of the customer
                            String bookname = sc.nextLine(); //getting the name of the book
                            System.out.println("What is the name of the author?: "); //taking the name of the author
                            String authorname = sc.nextLine(); //getting the last name of the customer
                            System.out.println("What is the genre of the book?: ");//taking the genre
                            String genre = sc.nextLine();
                            System.out.println("How many such books are there in the library?: "); //taking the total occurrences of the book in the library
                            int total = sc.nextInt();
                            //creating a prepared statement for execution
                            PreparedStatement insertStatement = con.prepareStatement("insert into books values(?,?,?,?,?)");
                            insertStatement.setInt(1,id);
                            insertStatement.setString(2,bookname);
                            insertStatement.setString(3,authorname);
                            insertStatement.setString(4,genre);
                            insertStatement.setInt(5,total);

                            int recordsaffected = insertStatement.executeUpdate();
                            System.out.println(recordsaffected+"Record added successfully!");
                            break;
                        case 2: //inserting into student table
                            //entering the student
                            System.out.println("Give the id of the student: ");
                            int sid = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Give the name of the student: ");
                            String sname = sc.nextLine();
                            PreparedStatement insertstudent = con.prepareStatement("insert into student values(?,?)");
                            insertstudent.setInt(1,sid);
                            insertstudent.setString(2,sname);
                            int record = insertstudent.executeUpdate();
                            System.out.println(record+"record added successfully");
                            break;
                        case 3: //inserting into issuedbooks table
                            //entering the issue
                            System.out.println("Give the id of the student: ");
                            int ids = sc.nextInt();
                            sc.nextLine();
                            System.out.println("Give the id of the book: ");
                            int idb = sc.nextInt();
                            sc.nextLine();
                            PreparedStatement insertissue = con.prepareStatement("insert into issuedbooks values(?,?)");
                            insertissue.setInt(1,ids);
                            insertissue.setInt(2,idb);
                            int res = insertissue.executeUpdate();
                            System.out.println(res+"record added successfully");
                            break;
                        default:
                            System.out.println("Invalid choice");
                            continue;



                    }

                    break;
                case 2:
                    //Printing the current records of the database
                    System.out.println("what table do you want to see? 1.books 2.student 3.issuedbooks");
                    int readchoice=sc.nextInt();
                    sc.nextLine();
                    switch(readchoice){
                        case 1: //display records of books table
                            ResultSet resultbooks = stmt.executeQuery("select * from books");
                            while(resultbooks.next()){
                                int bid = resultbooks.getInt("bookID");
                                String bname = resultbooks.getString("book_name");
                                String aname = resultbooks.getString("author_name");
                                String gname = resultbooks.getString("genre");
                                int all = resultbooks.getInt("total_no_books");
                                System.out.println("id: "+bid+"  book_name: "+bname+"  author_name: "+aname+"  genre: "+gname+"  Total number of books: "+all);

                            }
                            break;
                        case 2: //display records of student table
                            ResultSet resultstudent = stmt.executeQuery("select * from student");
                            while (resultstudent.next()){
                                int sid = resultstudent.getInt("studentID");
                                String name_s = resultstudent.getString("student_name");
                                System.out.println("id: "+sid+" name: "+name_s);
                            }
                            break;
                        case 3: //display records of issuedbooks table
                            ResultSet resultissue = stmt.executeQuery("select * from issuedbooks");
                            while(resultissue.next()){
                                int ids = resultissue.getInt("studentID");
                                int idb = resultissue.getInt("bookID");
                                System.out.println("student id: "+ids+" book id: "+idb);

                            }
                            break;

                    }

                    
                    break;
                case 3:
                    //Updating the records
                    System.out.println("which table do you want to update? 1.books 2.student 3.issuedbooks");
                    int updatechoice = sc.nextInt();
                    sc.nextLine();
                    switch (updatechoice){
                        case 1://update books table
                            System.out.println("enter the bookid to update");
                            int bookid_toUpdate = sc.nextInt();
                            sc.nextLine();
                            // Display options for what the user can update
                            System.out.println("Choose what to update:");
                            System.out.println("1. Update book name");
                            System.out.println("2. Update author name");
                            System.out.println("3. Update genre");
                            System.out.println("4. Update total number of books");
                            System.out.println("enter the choice");
                            int updatech = sc.nextInt();
                            sc.nextLine();
                            PreparedStatement updateStatement;
                            switch(updatech){
                                case 1:
                                    System.out.println("Give the new name of the book");
                                    String new_book_name = sc.nextLine();
                                    updateStatement = con.prepareStatement("update books set book_name = ? where bookID = ?");
                                    updateStatement.setString(1,new_book_name);
                                    break;
                                case 2:
                                    System.out.println("Give the new name of the author");
                                    String new_author_name = sc.nextLine();
                                    updateStatement = con.prepareStatement("update books set author_name = ? where bookID = ?");
                                    updateStatement.setString(1,new_author_name);
                                    break;
                                case 3:
                                    System.out.println("Give the new genre");
                                    String new_genre = sc.nextLine();
                                    updateStatement = con.prepareStatement("update books set genre = ? where bookID = ?");
                                    updateStatement.setString(1,new_genre);
                                    break;
                                case 4:
                                    System.out.println("Give the new total");
                                    int new_total = sc.nextInt();
                                    sc.nextLine();
                                    updateStatement = con.prepareStatement("update books set total_no_books = ? where bookID = ?");
                                    updateStatement.setInt(1,new_total);
                                    break;
                                default:
                                    System.out.println("invalid");
                                    continue;


                            }
                            updateStatement.setInt(2, bookid_toUpdate);
                            int resultSetBook = updateStatement.executeUpdate();
                            // Check if the update was successful and provide feedback
                            if (resultSetBook > 0)
                            {
                                System.out.println(" table updated successfully.");
                            } else
                            {
                                System.out.println("table not found or update failed.");
                            }
                            break;
                        case 2: //update student table
                            System.out.println("enter student id to update");
                            int stuid = sc.nextInt();
                            sc.nextLine();
                            System.out.println("enter the new name of the student: ");
                            String namestu = sc.nextLine();
                            updateStatement  = con.prepareStatement("update student set student_name = ? where studentID= ? ");
                            updateStatement.setString(1,namestu);
                            updateStatement.setInt(2,stuid);
                            int resultSetStu = updateStatement.executeUpdate();
                            // Check if the update was successful and provide feedback
                            if (resultSetStu > 0)
                            {
                                System.out.println(" table updated successfully.");
                            } else
                            {
                                System.out.println("table not found or update failed.");
                            }
                            break;

                        case 3: //update issuedbooks table
                            System.out.println("enter student id to update");
                            int ids = sc.nextInt();
                            sc.nextLine();
                            System.out.println("enter the id of the book which the student has issued ");
                            int idb = sc.nextInt();
                            sc.nextLine();
                            updateStatement = con.prepareStatement("update issuedbooks set bookID = ? where studentID = ?");
                            updateStatement.setInt(1,idb);
                            updateStatement.setInt(2,ids);
                            int resultSetIssue = updateStatement.executeUpdate();
                            // Check if the update was successful and provide feedback
                            if (resultSetIssue > 0)
                            {
                                System.out.println(" table updated successfully.");
                            } else
                            {
                                System.out.println("table not found or update failed.");
                            }
                            break;
                    }


                    break;
                case 4:
                    //deleting the record
                    System.out.println("from which table do you want to delete? 1.books 2.student 3.issuedbooks: ");
                    int deletechoice = sc.nextInt();
                    switch (deletechoice){
                        case 1: //deleting from books
                            System.out.println("enter the bookid to delete");
                            int bookid_toDelete = sc.nextInt();
                            sc.nextLine();
                            PreparedStatement deleteStatement = con.prepareStatement("delete from books where bookID = ?");
                            deleteStatement.setInt(1,bookid_toDelete);
                            int rows = deleteStatement.executeUpdate();
                            if (rows > 0)
                            {
                                System.out.println("row deleted successfully.");
                            } else
                            {
                                System.out.println("table not found or delete failed.");
                            }
                            break;
                        case 2: //deleting from student
                            System.out.println("enter the studentID to delete: ");
                            int sid = sc.nextInt();
                            sc.nextLine();
                            PreparedStatement deleteStudent = con.prepareStatement("delete from student where studentID = ?");
                            deleteStudent.setInt(1,sid);
                            int rowstudent = deleteStudent.executeUpdate();
                            if (rowstudent > 0)
                            {
                                System.out.println("row deleted successfully.");
                            } else
                            {
                                System.out.println("table not found or delete failed.");
                            }
                            break;
                        case 3: //deleting from issuedbooks
                            System.out.println("enter the studentID to delete: ");
                            int ids = sc.nextInt();
                            sc.nextLine();
                            PreparedStatement deleteIssue = con.prepareStatement("delete from issuedbooks where studentID = ?");
                            deleteIssue.setInt(1,ids);
                            int rowsissue = deleteIssue.executeUpdate();
                            if (rowsissue > 0)
                            {
                                System.out.println("row deleted successfully.");
                            } else
                            {
                                System.out.println("table not found or delete failed.");
                            }
                            break;
                    }

                case 5: //see the books issued by the student with the date of issue
                    System.out.println("enter the id of the student: ");
                    int sid = sc.nextInt();
                    PreparedStatement issuebooks = con.prepareStatement("select books.book_name, issuedbooks.data_of_issue from books join issuedbooks on issuedbooks.bookID=books.bookID join student on student.studentID=issuedbooks.studentID where student.studentID=?;");
                    issuebooks.setInt(1,sid);
                    ResultSet resultissuebooks = issuebooks.executeQuery();
                    while(resultissuebooks.next()){
                        String idb = resultissuebooks.getString("book_name");
                        Date date = resultissuebooks.getDate("data_of_issue");
                        System.out.println("book name:  "+idb+" Date of issue: "+date);

                    }
                    break;
                case 6:
                    System.out.println("exiting the program..........");
                    break;
                default:
                    //when user enters an invalid input
                    System.out.println("invalid input. please try again");


            }
        }while(choice!=6);

        // Close the Scanner used for user input.
        sc.close();
        // Close a Statement object
        stmt.close();
        // Close the database connection.
        con.close();

    }

}
