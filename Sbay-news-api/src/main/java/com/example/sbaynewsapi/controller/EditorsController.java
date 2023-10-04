package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.config.JwtUserDetails;
import com.example.sbaynewsapi.dto.EditorsDto;
import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Posts;
import com.example.sbaynewsapi.model.Roles;
import com.example.sbaynewsapi.model.Users;
import com.example.sbaynewsapi.service.EmailService;
import com.example.sbaynewsapi.service.IEditorsService;
import com.example.sbaynewsapi.service.IRolesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin(origins = {"http://localhost:3000"}, allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/editor")
public class EditorsController {
    @Autowired
    private IEditorsService iEditorsService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IRolesService iRolesService;

    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<Page<Editors>> getEditor(@RequestParam(value = "name", defaultValue = "null") String name, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 9);
        try {
            return new ResponseEntity<>(iEditorsService.getAll(name, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xem thông tin cá nhân (Admin)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/detail/{idEditor}")
    public ResponseEntity<Editors> getDetailEditor(@PathVariable("idEditor") Integer idEditor) {
        try {
            return new ResponseEntity<>(iEditorsService.getDetailEditor(idEditor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Trang cá nhân
    // @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
    @GetMapping("/information")
    public ResponseEntity<Editors> getInformation() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
        try {
            return new ResponseEntity<>(iEditorsService.getEditor(principal.getUsername()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Thêm editor (admin)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/createEditor")
    public ResponseEntity<?> createEditor(@RequestBody @Valid EditorsDto editorsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            Editors editors = new Editors();
            String pass = editorsDto.getUsers().getPassword();
            BeanUtils.copyProperties(editorsDto, editors);
            Users users = new Users();
            BeanUtils.copyProperties(editorsDto.getUsers(), users);
            users.setEmail(editorsDto.getEmail());
            users.setRoles(iRolesService.getRole());
            editors.setUsers(users);
            editors.setCreateDate(currentDateTime);
            iEditorsService.createEditor(editors);
            emailService.sendMail(editors.getEmail(), "Đăng kí tài khoản Sbay", "Chào " + editors.getName() + ", quản lý Sbay vừa đăng kí cho bạn một tài khoản để sử dụng trang web Sbay." +
                    "\n" +
                    "Đây là những thông tin đăng nhập của bạn:" + "\n" +
                    "Tài khoản: " + editors.getUsers().getUsername() +
                    "\n" +
                    "Mật khẩu: " + pass + "\n" +
                    "Chúc bạn làm việc thành công" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "---------------------------------------" + "\n" +
                    "Name :Sbay Viet Nam\n" +
                    "Mobile : 0782391943\n" +
                    "Email : sbayintern2023@gmail.com\n" +
                    "Address :\u200B03\u200B \u200BĐinh Thị Hòa\u200B streets, \u200BSơn Trà\u200B District, Da Nang");
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa editor (admin)
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/deleteEditor")
    public ResponseEntity<?> deleteEditor(@RequestBody EditorsDto editorsDto) {
        try {
            return iEditorsService.deleteEditor(editorsDto.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
