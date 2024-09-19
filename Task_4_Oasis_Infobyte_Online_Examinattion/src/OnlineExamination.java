import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class User {
    private String username;
    private String password;
    private String profile;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.profile = "Default Profile";
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateProfile(String profile) {
        this.profile = profile;
    }

    public String getProfile() {
        return profile;
    }
}

class ExaminationSystem {
    private Map<String, User> users;
    private User currentUser;
    private boolean examInProgress;
    private int score;


    private String[][] questions = {
            {"What is 2 + 2?", "1. 3", "2. 4", "3. 5", "4. 6", "2"},
            {"What is the capital of France?", "1. Berlin", "2. London", "3. Paris", "4. Rome", "3"},
            {"Which is the largest planet in our solar system?", "1. Earth", "2. Mars", "3. Jupiter", "4. Venus", "3"},
            {"Who wrote 'Hamlet'?", "1. Charles Dickens", "2. J.K. Rowling", "3. William Shakespeare", "4. Mark Twain", "3"},
            {"What is the square root of 64?", "1. 6", "2. 7", "3. 8", "4. 9", "3"},
            {"Which continent is the Sahara Desert located?", "1. Africa", "2. Asia", "3. Australia", "4. South America", "1"},
            {"What is the chemical symbol for water?", "1. H2", "2. O2", "3. CO2", "4. H2O", "4"},
            {"Which year did World War II end?", "1. 1945", "2. 1918", "3. 1939", "4. 1965", "1"},
            {"What is the boiling point of water?", "1. 90°C", "2. 100°C", "3. 120°C", "4. 110°C", "2"},
            {"Who discovered penicillin?", "1. Marie Curie", "2. Alexander Fleming", "3. Albert Einstein", "4. Isaac Newton", "2"}
    };

    public ExaminationSystem() {
        users = new HashMap<>();
        users.put("user1", new User("user1", "password123"));
    }

    public boolean login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void register(String username, String password) {
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("Registration successful. You can now log in.");
        }
    }

    public void logout() {
        currentUser = null;
        System.out.println("Logged out successfully.");
    }

    public void updateProfile(String profile) {
        if (currentUser != null) {
            currentUser.updateProfile(profile);
            System.out.println("Profile updated.");
        } else {
            System.out.println("Please login first.");
        }
    }

    public void updatePassword(String newPassword) {
        if (currentUser != null) {
            currentUser.updatePassword(newPassword);
            System.out.println("Password updated.");
        } else {
            System.out.println("Please login first.");
        }
    }

    public void startExam() {
        if (currentUser == null) {
            System.out.println("Please login first.");
            return;
        }

        examInProgress = true;
        score = 0;
        System.out.println("Exam started. You have 90 seconds to complete.");

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (examInProgress) {
                    System.out.println("\nTime is up! Exam auto-submitted.");
                    examInProgress = false;
                    showScore();
                }
            }
        }, 90000);

        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < questions.length; i++) {
            if (!examInProgress) break;

            System.out.println("Question " + (i + 1) + ": " + questions[i][0]);
            for (int j = 1; j <= 4; j++) {
                System.out.println(questions[i][j]);
            }
            System.out.print("Enter your answer (1-4): ");
            int answer = scanner.nextInt();

            if (examInProgress) {
                if (Integer.toString(answer).equals(questions[i][5])) {
                    System.out.println("Correct answer!");
                    score++;
                } else {
                    System.out.println("Wrong answer.");
                }
            }
        }

        if (examInProgress) {
            examInProgress = false;
            showScore();
        }
    }

    private void showScore() {
        System.out.println("\nExam finished.");
        System.out.println("Your score is: " + score + " out of " + questions.length);
        System.out.println("Your Total Score is : "+score);
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}

public class OnlineExamination {
    public static void main(String[] args) {
        ExaminationSystem system = new ExaminationSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOnline Examination System");

            if (!system.isLoggedIn()) {
                System.out.println("1. Login");
                System.out.println("2. Register");
            } else {
                System.out.println("3. Update Profile");
                System.out.println("4. Update Password");
                System.out.println("5. Start Exam");
                System.out.println("6. Logout");
            }
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (!system.isLoggedIn()) {
                if (choice == 1) {
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (system.login(username, password)) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Login failed. Incorrect username or password.");
                    }
                } else if (choice == 2) {
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine();
                    system.register(newUsername, newPassword);
                } else if (choice == 7) {
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } else {
                switch (choice) {
                    case 3:
                        System.out.print("Enter new profile information: ");
                        String profile = scanner.nextLine();
                        system.updateProfile(profile);
                        break;
                    case 4:
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        system.updatePassword(newPassword);
                        break;
                    case 5:
                        system.startExam();
                        break;
                    case 6:
                        system.logout();
                        break;
                    case 7:
                        System.out.println("Exiting system. Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }
}
