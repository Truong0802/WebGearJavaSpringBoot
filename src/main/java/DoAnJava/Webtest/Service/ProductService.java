package DoAnJava.Webtest.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Repository.SAN_PHAM_Repository;

@Service
public class ProductService {
    @Autowired
    private SAN_PHAM_Repository san_PHAM_Repository;

    public Page<SAN_PHAM> GetAll(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
        return san_PHAM_Repository.findAll(pageRequest);
    }

    public SAN_PHAM GetinfoProduct(int id) {

        return san_PHAM_Repository.findById(id).stream().findFirst().orElse(null);
    }
}
