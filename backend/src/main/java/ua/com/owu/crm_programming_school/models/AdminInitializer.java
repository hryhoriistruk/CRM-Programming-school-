package ua.com.owu.crm_programming_school.models;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import ua.com.owu.crm_programming_school.services.adminService.AdminService;

@Component

public class AdminInitializer implements CommandLineRunner {
    private AdminService adminService;

    public AdminInitializer(@Qualifier("adminServiceImpl1")AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!adminService.adminExists()) {
            adminService.createAdminDefault();
        }
    }
}
