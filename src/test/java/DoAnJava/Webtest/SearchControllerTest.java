package DoAnJava.Webtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.lenient;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import DoAnJava.Webtest.Entity.SAN_PHAM;
import DoAnJava.Webtest.Service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @Test
    public void testSearchWithKeyword() throws Exception {
        List<SAN_PHAM> productList = new ArrayList<>();
        productList.add(new SAN_PHAM());
        PageImpl<SAN_PHAM> page = new PageImpl<>(productList);
        lenient().when(productService.GetProductBySearch(anyString(), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/").param("key", "Lo"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("key"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listproduct"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalPages"));
    }

    @Test
    public void testSearchWithEmptyKeyword() throws Exception {
        List<SAN_PHAM> productList = new ArrayList<>();
        PageImpl<SAN_PHAM> page = new PageImpl<>(productList);
        lenient().when(productService.GetProductBySearch(anyString(), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/").param("key", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("key"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listproduct"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalPages"));
    }

    @Test
    public void testSearchWithSpecificKeyword() throws Exception {
        List<SAN_PHAM> productList = new ArrayList<>();
        productList.add(new SAN_PHAM());
        PageImpl<SAN_PHAM> page = new PageImpl<>(productList);
        lenient().when(productService.GetProductBySearch(anyString(), anyInt(), anyInt())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/search/").param("key", "Cúc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("key"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("listproduct"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalPages"));
    }
}
