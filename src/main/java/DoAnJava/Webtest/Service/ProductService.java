package DoAnJava.Webtest.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import DoAnJava.Webtest.Entity.LOAI_SP;
import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Repository.LOAI_SP_Repository;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;

@Service
public class ProductService {
    @Autowired
    private SAN_PHAM_Repository san_PHAM_Repository;
    @Autowired
    private LOAI_SP_Repository loai_SP_Repository;

    public Page<SAN_PHAM> GetAll(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return san_PHAM_Repository.findAll(pageRequest);
    }

    public Page<SAN_PHAM> GetAllProductFromType(int loai, int pageNo, int pageSize) { // Lấy sản phẩm theo loại
        LOAI_SP type = loai_SP_Repository.findById(loai).get();
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        List<SAN_PHAM> filteredList = san_PHAM_Repository.findAll().stream()
                .filter(x -> x.getMaLoai() == type).toList();
        int start = Math.min((int) pageRequest.getOffset(), filteredList.size());
        int end = Math.min((start + pageRequest.getPageSize()), filteredList.size());
        Page<SAN_PHAM> page = new PageImpl<SAN_PHAM>(filteredList.subList(start, end), pageRequest,
                filteredList.size());
        return page;
    }

    public Page<SAN_PHAM> GetProductBySearch(String key, int pageNo, int pageSize) { // Lấy sản phẩm theo loại
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        List<SAN_PHAM> filteredList = san_PHAM_Repository.findAll().stream()
                .filter(p -> p.getTenSP().toLowerCase().contains(key.toLowerCase())).toList();
        int start = Math.min((int) pageRequest.getOffset(), filteredList.size());
        int end = Math.min((start + pageRequest.getPageSize()), filteredList.size());
        Page<SAN_PHAM> page = new PageImpl<SAN_PHAM>(filteredList.subList(start, end), pageRequest,
                filteredList.size());
        return page;
    }

    public SAN_PHAM GetinfoProduct(int id) {

        return san_PHAM_Repository.findById(id).stream().findFirst().orElse(null);
    }

    public SAN_PHAM get(int id) {
        return san_PHAM_Repository.findById(id).stream().findFirst().orElse(null);
    }
}
