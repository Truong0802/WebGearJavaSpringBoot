package DoAnJava.Webtest.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import DoAnJava.Webtest.Entity.LOAI_SP;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Repository.LOAI_SP_Repository;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;

@Service
public class HomeService {
    @Autowired
    private SAN_PHAM_Repository sanphamrepository;
    @Autowired
    private LOAI_SP_Repository loai_SP_Repository;

    public List<SAN_PHAM> GetAllProduct() { // Hàm demo lấy tất cả sản phẩm
        return sanphamrepository.findAll();
    }

    public List<SAN_PHAM> GetAllProductFromType(int loai) { // Lấy sản phẩm theo loại
        LOAI_SP type = loai_SP_Repository.findById(loai).get();
        return (List<SAN_PHAM>) sanphamrepository.findAll().stream()
                .filter(x -> x.getMaLoai() == type)
                .toList();
    }
}
