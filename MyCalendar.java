// Peter Hovenier
// CS 141
// Katie Davis
// Assignment 2: Calendar
// This program prints out a calendar of the user's choosing, it is interactive
// and can print the current date, or the next or previous month
// I spent approximately 20 hours on this assignment
import java.util.*; 
import java.io.*;
public class MyCalendar {
   public static final int SIZE = 10;
   public static void main (String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      String[][] eventsArray = createArray();
      giveIntro();
      String input = console.next();
      Scanner fRead = new Scanner(new File("calendarEvents.txt"));
      while (fRead.hasNext()) {
         String sDate = fRead.next();
         int sMonth = monthFromDate(sDate);
         int sDay = dayFromDate(sDate);
         String sName = fRead.next();
         eventsArray[sMonth - 1][sDay - 1] = sName;
      }
      while (!input.equals("q")) {   
          if (input.equals("ev")) {
             System.out.println("Please enter an event: mm/dd event_name");
             String eventDate = console.next();
             int eventMonth = monthFromDate(eventDate);
             int eventDay = dayFromDate(eventDate);
             String eventName = console.next();
             eventsArray[eventMonth - 1][eventDay - 1] = eventName;
          }
          
          if (input.equals("fp")) {
             System.out.println("Please enter a month to print: ");
             int mon = console.nextInt();
             int da = 1;
             int dayOfYear = dayOfYear(mon);
             int dayOfWeek = dayOfWeek(dayOfYear);
             System.out.println("Please enter an output file name: ");
             String outputFileName;
             outputFileName = console.next();
             File outFile = new File(outputFileName);
             PrintStream output = new PrintStream(outFile);
             PrintStream consoleOut = System.out;
             System.setOut(output);
             drawUpTriangle();
             drawDownTriangle();
             drawMonth(mon, dayOfWeek(dayOfYear), da, eventsArray);
             System.setOut(consoleOut);
          }
          
          if (input.equals("e")) {
            System.out.println("Please enter a date");
            String date = console.next();
            int month = monthFromDate(date);
            int day = dayFromDate(date);
            int dayOfYear = dayOfYear(month);
            int dayOfWeek = dayOfWeek(dayOfYear);
            drawUpTriangle();
            drawDownTriangle();
            drawMonth(month, dayOfWeek(dayOfYear), day, eventsArray);
            displayDate(month, day);
            giveIntro();
            input = console.next();
            while (input.equals("n")) {
               month += 1;
               if (month > 12) {
                  month = 1;
               }
               drawMonth(month, dayOfWeek(dayOfYear(month)), day, eventsArray);
               giveIntro();
               input = console.next();
            }
            while (input.equals("p")) {
               month -= 1;
               if (month < 1) {
                  month = 12;
               }
               drawMonth(month, dayOfWeek(dayOfYear(month)), day, eventsArray);
               giveIntro();
               input = console.next();
            }
         } if (input.equals("t")) {
            Calendar calendar = Calendar.getInstance();
            System.out.println("The current date is: ");
            int currentMonth = calendar.get(Calendar.MONTH) + 1;
            int currentDay = calendar.get(Calendar.DATE);
            drawUpTriangle();
            drawDownTriangle();
            int dayOfYear = dayOfYear(currentMonth);
            int dayOfWeek = dayOfWeek(dayOfYear);
            drawMonth(currentMonth, dayOfWeek, currentDay, eventsArray);
            displayDate(currentMonth, currentDay);
            giveIntro();
            input = console.next();
            while (input.equals("n")) {
               currentMonth += 1;
               if (currentMonth > 12) {
                  currentMonth = 1;
               }
               drawMonth(currentMonth, dayOfWeek(dayOfYear(currentMonth)), currentDay, eventsArray);
               giveIntro();
               input = console.next();
            }
            while (input.equals("p")) {
               currentMonth -= 1;
               if (currentMonth < 1) {
                  currentMonth = 12;
               }
               drawMonth(currentMonth, dayOfWeek(dayOfYear(currentMonth)), currentDay, eventsArray);
               giveIntro();
               input = console.next();
            }
         }
         giveIntro();
         input = console.next();
      }       
   }
   
  // returns the month of the date from the string date.
  public static int monthFromDate(String date) {
      String month = date.substring(0, 2);
      int monthInt = Integer.parseInt(month);
      return monthInt;
  }
  
  // returns the day of the date from the string date.
  public static int dayFromDate(String date) {
      String day = date.substring(3, 5);
      int dayInt = Integer.parseInt(day);
      return dayInt;   
  } 
   
   // draws the full month of the calendar.
   public static void drawMonth(int month, int dayOfWeek, int selectedDay, String[][] eventsArray) {
      for (int i = 1; i <= SIZE * 3.5; i++) {
         System.out.print(" ");
      }
      System.out.print(month);
      System.out.println("");
      System.out.print("Sun");
      printSpace();
      System.out.print("Mon");
      printSpace();
      System.out.print("Tue");
      printSpace();
      System.out.print("Wed");
      printSpace();
      System.out.print("Thu");
      printSpace();
      System.out.print("Fri");
      printSpace();
      System.out.print("Sat");
      System.out.println();
      for (int row = 1; row <= 5; row++) {
         drawLine();
         drawRow(row, dayOfWeek, daysInMonth(month), selectedDay, month, eventsArray);
      }
      drawLine();
      System.out.println();
   }
   
   // draws the lines of the calendar.
   public static void drawLine() {
      for (int i = 1; i <= SIZE * 7 + 1; i++) {
         System.out.print("=");
      }    
   }
   
   // pads the numbers inside of the cells of the calendar. 
   public static String padded(String s, int width) {
      for (int i = s.length(); i < width; i++) {
         s = s + " ";
      }
      return s;
   }
   // draws the rows of the calendar
   public static void drawRow(int row, int dayOfWeek, int daysInMonth, int selectedDay, int month, String[][] eventsArray) {
      System.out.println();
      for (int i = 1; i <= 7; i++) {
         System.out.print("|");
         int currentDay = i - dayOfWeek + (row - 1) * 7;
         if ((row == 1 && i <= dayOfWeek) || currentDay > daysInMonth) {
            for (int j = 0; j <= SIZE - 2; j++) {
               System.out.print(" ");
            }
         } else if (currentDay == selectedDay) {
            System.out.print(padded(currentDay + ("*"), SIZE - 1));
         } else {
         System.out.print(padded(currentDay + "", SIZE - 1));
         }
         
      } 
      System.out.print("|");
      for (int j = 1; j <= SIZE / 2 - 1; j++) {
         System.out.println();
         
         for (int i = 1; i <= 7; i++) {
             System.out.print("|");
             int currentDay = i - dayOfWeek + (row - 1) * 7;
             if (currentDay > 0 && j == 1 && currentDay <= daysInMonth && eventsArray[month - 1][currentDay - 1] != null) {
                System.out.print(padded(eventsArray[month - 1][currentDay - 1], SIZE - 1));
             } else { 
                for(int g = 1; g <= SIZE - 1; g++) {
                       System.out.print(" ");
                    }
              
            
             }
            
         }
        System.out.print("|");
     }
     
     System.out.println();
   }
   
   // displays the month and date broken up at the bottom of the calendar. 
   public static void displayDate(int month, int day) {
      System.out.println();
      System.out.println("month: " + month);
      System.out.println("day: " + day);
   }
   
   // draws the top half of the diamond art at the top of the calendar
   public static void drawUpTriangle() {
         
      for (int line = 1; line <= 10; line++) {
         
            
         for (int i = 1; i <= -1 * line + 29; i++) {
            System.out.print(" ");
         }
            
         for (int i = 1; i <= 1 * line; i++) {
            System.out.print("/");
         }
            
         for (int i = 1; i <= 1 * line; i++) {
            System.out.print("\\");
         }
      System.out.println();
      }
         
   }      
   // Draws the bottom half of the diamond art at the top of the calendar
   public static void drawDownTriangle() {
   
      for (int line = 1; line <= 10; line ++) {

         
         for (int i = 1; i <= line - 1 + 19; i++) {
            System.out.print(" ");
         }
         
         for (int i = 1; i <= -1 * line + 11; i++) {
            System.out.print("\\");
         }
         
         for (int i = 1; i <= -1 * line + 11; i++) {
            System.out.print("/");
         }
      System.out.println();
      }
   }  
   // Calculates the day of the week that the month starts on
   public static int dayOfWeek(int absDay) {
      return (absDay + 6) % 7;
   }
   
   // calculates the absolute day of the year that the month starts on
   public static int dayOfYear(int month) {
      int absDay = 0;
      for (int i = 1; i < month; i++) {
         if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) {
            absDay += 31;
         } else if (i == 2) {
            absDay += 28;
         } else if (i == 4 || i == 6 || i == 9 || i == 11) {
            absDay += 30;
         }
      
      }   
   return absDay;
   }
   // returns how many days are in each month
   public static int daysInMonth(int month) {
      int days = 0;
      if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
         days = 31;
      } else if (month == 2) {
         days = 28;
      } else if (month == 4 || month == 6 || month == 9 || month == 11) {
         days = 30;
      }
      return days;
   }
   
   // prints space between the days at the top of the calendar
   public static void printSpace() {
      for (int i = 1; i <= SIZE -3; i++) {
         System.out.print(" ");
      }
   }
   // The prompt for the interactive menu
   public static void giveIntro() {
         System.out.println("Please enter a command:");
         System.out.println("press n to display the next month");
         System.out.println("press p to display the previous month");
         System.out.println("press e to enter a date");
         System.out.println("press t to get today's date");
         System.out.println("press q to quit");
         System.out.println("enter fp to print the month to an output file");
         System.out.println("enter ev to create an event");

   }
   // prints out an error message when invalid input is put in
   public static void invalidInput(String input) {
      Scanner console = new Scanner(System.in);
      if (!input.equals("q") && !input.equals("e") && !input.equals("t") && !input.equals("n") && !input.equals("p")) {
         System.out.println("Invalid input. Please enter correct input");
         input = console.next();
      }
      
   } 
     //Creates the array where the events are stored
     public static String[][] createArray() {
      String[][] events = new String[12][];
      for (int i = 0; i < events.length; i++) {
         events[i] = new String[daysInMonth(i + 1)];
      }
      return events;
   }
    

}
