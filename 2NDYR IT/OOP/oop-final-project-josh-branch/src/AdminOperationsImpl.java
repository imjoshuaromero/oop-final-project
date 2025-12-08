import java.util.ArrayList;

/**
 * Implementation of admin operations
 * Handles viewing, updating, and deleting concerns
 */
public class AdminOperationsImpl implements IAdminOperations {
    
    @Override
    public void viewAllConcerns(ArrayList<Grievance> grievances) {
        if (grievances.isEmpty()) {
            UIHelper.showError("No concerns submitted yet.");
            return;
        }
        
        int pageSize = 5;
        int totalPages = (int) Math.ceil((double) grievances.size() / pageSize);
        
        for (int page = 0; page < totalPages; page++) {
            Utility.clearScreen();
            Utility.printTitle("ALL CONCERNS");
            Utility.printCentered("Page " + (page + 1) + " of " + totalPages);
            Utility.printCentered("");
            
            int start = page * pageSize;
            int end = Math.min(start + pageSize, grievances.size());
            
            for (int i = start; i < end; i++) {
                grievances.get(i).display();
                System.out.println();
            }
            
            if (page < totalPages - 1) {
                Utility.waitForEnter("Press Enter for next page...");
            } else {
                Utility.waitForEnter("Press Enter to continue...");
            }
        }
    }
    
    @Override
    public void updateConcern(ArrayList<Grievance> grievances) {
        Utility.printTitle("UPDATE CONCERN");
        
        if (grievances.isEmpty()) {
            UIHelper.showError("No concerns available to update.");
            return;
        }
        
        int grievanceId = Utility.promptCenteredInt("Enter Grievance ID to update: ");
        
        Grievance targetGrievance = null;
        for (Grievance grievance : grievances) {
            if (grievance.getId() == grievanceId) {
                targetGrievance = grievance;
                break;
            }
        }
        
        if (targetGrievance == null) {
            UIHelper.showError("Grievance ID not found!");
            return;
        }
        
        Utility.printCentered("╔═══════════════════════════════════╗");
        Utility.printCentered("║       SELECT STATUS               ║");
        Utility.printCentered("╠═══════════════════════════════════╣");
        Utility.printCentered("║ 1. Under Review                   ║");
        Utility.printCentered("║ 2. In Progress                    ║");
        Utility.printCentered("║ 3. Resolved                       ║");
        Utility.printCentered("╚═══════════════════════════════════╝");
        System.out.println();
        
        int statusChoice = Utility.promptCenteredInt("Select status (1-3): ");
        String newStatus;
        
        switch (statusChoice) {
            case 1: newStatus = "Under Review"; break;
            case 2: newStatus = "In Progress"; break;
            case 3: newStatus = "Resolved"; break;
            default:
                UIHelper.showInvalidChoice(1, 3);
                return;
        }
        
        String feedback = Utility.promptCenteredString("Enter feedback from officer: ");
        
        targetGrievance.setStatus(newStatus);
        targetGrievance.setFeedback(feedback);
        
        DataManager.saveGrievances(grievances);
        
        UIHelper.showSuccess("Concern updated successfully!");
    }
    
    @Override
    public void deleteConcern(ArrayList<Grievance> grievances) {
        Utility.printTitle("DELETE CONCERN");
        
        if (grievances.isEmpty()) {
            UIHelper.showError("No concerns available to delete.");
            return;
        }
        
        int grievanceId = Utility.promptCenteredInt("Enter Grievance ID to delete: ");
        
        Grievance targetGrievance = null;
        int targetIndex = -1;
        
        for (int i = 0; i < grievances.size(); i++) {
            if (grievances.get(i).getId() == grievanceId) {
                targetGrievance = grievances.get(i);
                targetIndex = i;
                break;
            }
        }
        
        if (targetGrievance == null) {
            UIHelper.showError("Grievance ID not found!");
            return;
        }
        
        String confirmation = Utility.promptCenteredString("Are you sure you want to delete Grievance ID " + grievanceId + "? (yes/no): ");
        
        if (confirmation.equalsIgnoreCase("yes")) {
            grievances.remove(targetIndex);
            DataManager.saveGrievances(grievances);
            UIHelper.showSuccess("Concern deleted successfully!");
        } else {
            UIHelper.showError("Delete operation cancelled.");
        }
    }
}
