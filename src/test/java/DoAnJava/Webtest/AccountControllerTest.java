package DoAnJava.Webtest;


import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import DoAnJava.Webtest.Controller.AccountController;
import DoAnJava.Webtest.Entity.KHACH_HANG;
import DoAnJava.Webtest.Entity.TAI_KHOAN;
import DoAnJava.Webtest.Repository.KHACH_HANG_Repository;
import DoAnJava.Webtest.Repository.TAI_KHOAN_Repository;
import DoAnJava.Webtest.Service.AccountService;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Mock
    AccountService accountService;

    @Mock
    KHACH_HANG_Repository khachHangRepository;

    @InjectMocks
    AccountController accountController;

    @Mock
    Model model;

    @BeforeEach
    public void setUp() {
        // setup code if any
    }

    @Test
    public void testLoginfrm() {
        // Arrange

        // Act
        String result = accountController.loginfrm();

        // Assert
        // Kiểm tra xem kết quả trả về có phải là "Account/login.html" không
        // Ở đây có thể sử dụng AssertJ hoặc hamcrest hoặc JUnit assertions
        org.junit.jupiter.api.Assertions.assertEquals("Account/login.html", result);
    }

    @Test
    public void testRegister() {
        // Arrange

        // Act
        String result = accountController.register(model);

        // Assert
        // Kiểm tra xem kết quả trả về có phải là "Account/signup" không
        // Ở đây có thể sử dụng AssertJ hoặc hamcrest hoặc JUnit assertions
        org.junit.jupiter.api.Assertions.assertEquals("Account/signup", result);
    }

    @Test
    public void testHandleRegister() {
        String tk = "truong12";
        String mk = "Truong@123";
        String HoTen = "fullname";
        String email = "tranntruy@gmail.com";
        String phonenumber = "0982221034";
        String address = "address";

        TAI_KHOAN taikhoan = new TAI_KHOAN();
        taikhoan.setUsername(tk);
        taikhoan.setPassword(mk);
        KHACH_HANG khachhang = new KHACH_HANG();
        khachhang.setUsername(taikhoan);
        khachhang.setDiaChi(address);
        khachhang.setEmail(email);
        khachhang.setHoTenKH(HoTen);
        khachhang.setSDT(phonenumber);

        doNothing().when(accountService).addUserWithRole(any(TAI_KHOAN.class), any(KHACH_HANG.class));

        String result = accountController.handelRegister(model, tk, mk, HoTen, email, phonenumber, address);

        org.junit.jupiter.api.Assertions.assertEquals("redirect:/login", result);

        System.out.println(taikhoan.getUsername());
        verify(accountService, times(1)).addUserWithRole(any(TAI_KHOAN.class), any(KHACH_HANG.class));
    }

    @Test
    public void testHandleRegisterUsernameNull() {
        String tk = "";
        String mk = "Truong@123";
        String HoTen = "fullname";
        String email = "tranntruy@gmail.com";
        String phonenumber = "0982221034";
        String address = "address";

        TAI_KHOAN taikhoan = new TAI_KHOAN();
        taikhoan.setUsername(tk);
        taikhoan.setPassword(mk);
        KHACH_HANG khachhang = new KHACH_HANG();
        khachhang.setUsername(taikhoan);
        khachhang.setDiaChi(address);
        khachhang.setEmail(email);
        khachhang.setHoTenKH(HoTen);
        khachhang.setSDT(phonenumber);

        doNothing().when(accountService).addUserWithRole(any(TAI_KHOAN.class), any(KHACH_HANG.class));

        String result = accountController.handelRegister(model, tk, mk, HoTen, email, phonenumber, address);

        org.junit.jupiter.api.Assertions.assertEquals("redirect:/login", result);

        System.out.println(taikhoan.getUsername());
        verify(accountService, times(1)).addUserWithRole(any(TAI_KHOAN.class), any(KHACH_HANG.class));
    }
}
