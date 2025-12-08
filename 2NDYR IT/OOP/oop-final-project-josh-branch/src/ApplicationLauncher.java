/**
 * ApplicationLauncher: Handles initialization and startup of the application
 * Separates setup logic from Main entry point
 */
public class ApplicationLauncher {
    
    public static void start() {
        // Create service implementations
        IDataPersistence dataService = new DataPersistenceImpl();
        IStudentOperations studentOps = new StudentOperationsImpl();
        IAdminOperations adminOps = new AdminOperationsImpl();
        IAuthenticationService authService = new AuthenticationServiceImpl();
        
        // Load application data
        AppData appData = dataService.loadApplicationData();
        
        // Create menu handler with injected dependencies
        MenuHandler menuHandler = new MenuHandler(studentOps, adminOps, authService, dataService);
        
        // Start application
        menuHandler.runMainMenu(appData.getStudents(), appData.getGrievances());
    }
}
