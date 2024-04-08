package com.project.cinemamanagement.Controller;

import com.project.cinemamanagement.Entity.Role;
import com.project.cinemamanagement.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping
    private ResponseEntity<?> getAllRole(){
        try{
            return new ResponseEntity<>(roleService.getAllRole(),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @GetMapping("/{roleId}")
    private ResponseEntity<?> getRoleById(@PathVariable Long roleId) {
        try{
            return new ResponseEntity<>(roleService.getRoleById(roleId),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @PostMapping
    private ResponseEntity<?> addRole(@RequestBody Role role){
        try{
            return new ResponseEntity<>(roleService.addRole(role),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @DeleteMapping("/{roleId}")
    private ResponseEntity<?> deleteRole(@PathVariable Long roleId){
        try{
            return new ResponseEntity<>(roleService.deleteRole(roleId),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }

    @PutMapping("/{roleId}")
    private ResponseEntity<?> updateRole(@PathVariable Long roleId,@RequestBody Role role){
        try{
            return new ResponseEntity<>(roleService.updateRole(roleId,role),null,200);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),null,404);
        }
    }
}
