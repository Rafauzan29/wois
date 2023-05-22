package propensi.a04.wois.service;

import propensi.a04.wois.model.AdminModel;

public interface AdminService {

    AdminModel getAdminByUuid (String id);
    AdminModel getAdminByEmail (String email);
}