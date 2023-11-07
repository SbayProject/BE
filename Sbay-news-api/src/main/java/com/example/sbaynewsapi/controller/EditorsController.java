package com.example.sbaynewsapi.controller;

import com.example.sbaynewsapi.config.JwtUserDetails;
import com.example.sbaynewsapi.dto.EditorsDto;
import com.example.sbaynewsapi.model.Editors;
import com.example.sbaynewsapi.model.Users;
import com.example.sbaynewsapi.service.EmailService;
import com.example.sbaynewsapi.service.IEditorsService;
import com.example.sbaynewsapi.service.IRolesService;
import com.example.sbaynewsapi.service.IUsersService;
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
    @Autowired
    private IUsersService iUsersService;

     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<Page<Editors>> getEditor(@RequestParam(value = "name", defaultValue = "null") String name, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 3);
        try {
            return new ResponseEntity<>(iEditorsService.getAll(name, pageable), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Xem thông tin cá nhân (Admin)
     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/detail/{idEditor}")
    public ResponseEntity<Editors> getDetailEditor(@PathVariable("idEditor") Integer idEditor) {
        try {
            return new ResponseEntity<>(iEditorsService.getDetailEditor(idEditor), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Trang cá nhân
     @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_EDITOR')")
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
     @PreAuthorize("hasRole('ROLE_ADMIN')")
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
            String emailBody = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; }" +
                    "h1 { color: #337ab7; }" +
                    "img { max-width: 200px; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<h1>Đăng kí tài khoản Sbay</h1>" +
                    "<p>Chào " + editors.getName() + ", quản lý Sbay vừa đăng kí cho bạn một tài khoản để sử dụng trang web Sbay.</p>" +
                    "<p>Đây là những thông tin đăng nhập của bạn:</p>" +
                    "<ul>" +
                    "<li>Tài khoản: " + editors.getUsers().getUsername() + "</li>" +
                    "<li>Mật khẩu: " + pass + "</li>" +
                    "</ul>" +
                    "<p>Chúc bạn làm việc thành công</p>" +
                    "<br>" +
                    "<img src='https://sbay.com.vn/upload/news/mau-logo-chinh-thuc-co-sologanjpg.jpg?h=261&v=qRREr6pn-dp5ACj2VJ80O2MU7DOiYq1vHAJqjTraJk0'>" +
                    "<br>" +
                    "<hr>" +
                    "<p>Name: Sbay Viet Nam</p>" +
                    "<p>Mobile: 0782391943</p>" +
                    "<p>Email: sbayintern2023@gmail.com</p>" +
                    "<p>Address: 03 Đinh Thị Hòa streets, Sơn Trà District, Da Nang</p>" +
                    "</body>" +
                    "</html>";

            emailService.sendMail(editors.getEmail(), "Đăng kí tài khoản Sbay", emailBody);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa editor (admin)
     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/deleteEditor")
    public ResponseEntity<?> deleteEditor(@RequestBody EditorsDto editorsDto) {
        try {
            return iEditorsService.deleteEditor(editorsDto.getId());
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
     @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PatchMapping("/updateEditor")
    public ResponseEntity<?> updateEditor(@RequestBody @Valid EditorsDto editorsDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JwtUserDetails principal = (JwtUserDetails) authentication.getPrincipal();
            Users users = iUsersService.findByUsername(principal.getUsername());
            if (users.getRoles().getRoleName().equals("ROLE_ADMIN")) {
                return iEditorsService.updateEditor(editorsDto);
            } else {
                Editors editors =iEditorsService.getDetailEditor(editorsDto.getId());
                if (users.getId() == editors.getUsers().getId()) {
                    return iEditorsService.updateEditor(editorsDto);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
